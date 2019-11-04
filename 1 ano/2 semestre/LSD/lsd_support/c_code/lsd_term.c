//
// LSD.TOS, April 2018 (DO NOT REMOVE THIS LINE)
//
// Terminal emulator for RS232 communications with the FPGA (based on pterm.cpp and CommSerial.cpp).
//
// To compile this program run the following command on a terminal
//   cc -Wall -O2 -march=native lsd_term.c -o lsd_term
//

#include <time.h>
#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>
#include <termios.h>
#include <sys/file.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <sys/types.h>


//
// serial connection stuff
//

#define default_device_name  "/dev/ttyUSB0"

typedef struct
{
  //
  // configuration
  //
  char *device_name;           // the device name
  int baud_rate;               // the baud rate (see code below for a list of possible values)
  int data_bits;               // number of data bits (5, 6, 7, or 8)
  int stop_bits;               // number of stop bits (1 or 2)
  char parity;                 // the parity ('N', 'E', or 'O')
  //
  // internal stuff (would be "private" in C++)
  //
  int fd;                      // file descriptor
  struct termios current_tio;  // current terminal settings
  struct termios original_tio; // original terminal settings
}
serial_connection;

static int open_serial_connection(serial_connection *sc)
{
  speed_t baud;
  //
  // open device
  //
  sc->fd = open(sc->device_name,O_RDWR | O_NDELAY | O_NOCTTY);
  if(sc->fd < 0)
  {
    fprintf(stderr,"unable to open device %s\n",sc->device_name);
    return -1;
  }
  if(flock(sc->fd,LOCK_EX | LOCK_NB) < 0)
  {
    fprintf(stderr,"%s is already being used\n",sc->device_name);
e:  close(sc->fd);
    sc->fd = -1;
    return -1;
  }
  if(tcflush(sc->fd,TCIOFLUSH) < 0)
  {
    fprintf(stderr,"unable to clear stale data of device %s\n",sc->device_name);
    goto e;
  }
  if(tcgetattr(sc->fd,&sc->original_tio) < 0)
  {
    fprintf(stderr,"unable to get the original configuration of device %s\n",sc->device_name);
    goto e;
  }
  sc->current_tio = sc->original_tio;
  //
  // change device control configuration (man tcgetattr)
  //
  if     (sc->baud_rate <     900) { sc->baud_rate =     600; baud = B600;     }
  else if(sc->baud_rate <    1500) { sc->baud_rate =    1200; baud = B1200;    }
  else if(sc->baud_rate <    2100) { sc->baud_rate =    1800; baud = B1800;    }
  else if(sc->baud_rate <    3600) { sc->baud_rate =    2400; baud = B2400;    }
  else if(sc->baud_rate <    7200) { sc->baud_rate =    4800; baud = B4800;    }
  else if(sc->baud_rate <   14400) { sc->baud_rate =    9600; baud = B9600;    }
  else if(sc->baud_rate <   28800) { sc->baud_rate =   19200; baud = B19200;   }
  else if(sc->baud_rate <   48000) { sc->baud_rate =   38400; baud = B38400;   }
  else if(sc->baud_rate <   86400) { sc->baud_rate =   57600; baud = B57600;   }
  else if(sc->baud_rate <  172800) { sc->baud_rate =  115200; baud = B115200;  }
  else if(sc->baud_rate <  345600) { sc->baud_rate =  230400; baud = B230400;  }
  else if(sc->baud_rate <  691200) { sc->baud_rate =  460800; baud = B460800;  }
  else if(sc->baud_rate < 1036800) { sc->baud_rate =  921600; baud = B921600;  }
  else                             { sc->baud_rate = 1152000; baud = B1152000; }
  if(cfsetospeed(&sc->current_tio,baud) < 0 || cfsetispeed(&sc->current_tio,baud) < 0)
  {
    fprintf(stderr,"unable to change the baud rate of device %s\n",sc->device_name);
    goto e;
  }
  if     (sc->data_bits < 6) { sc->data_bits = 5; sc->current_tio.c_cflag = (sc->current_tio.c_cflag & ~CSIZE) | CS5; }
  else if(sc->data_bits < 7) { sc->data_bits = 6; sc->current_tio.c_cflag = (sc->current_tio.c_cflag & ~CSIZE) | CS6; }
  else if(sc->data_bits < 8) { sc->data_bits = 7; sc->current_tio.c_cflag = (sc->current_tio.c_cflag & ~CSIZE) | CS7; }
  else                       { sc->data_bits = 8; sc->current_tio.c_cflag = (sc->current_tio.c_cflag & ~CSIZE) | CS8; }
  if(sc->stop_bits < 2)
  {
    sc->stop_bits = 1;
    sc->current_tio.c_cflag &= ~CSTOPB;
  }
  else
  {
    sc->stop_bits = 2;
    sc->current_tio.c_cflag |= CSTOPB;
  }
  sc->current_tio.c_cflag &= ~(PARENB | PARODD);
  if(sc->parity == 'E')
    sc->current_tio.c_cflag |= PARENB;
  else if(sc->parity == 'O')
    sc->current_tio.c_cflag |= PARENB | PARODD;
  else
    sc->parity = 'N';
  sc->current_tio.c_cflag &= ~CRTSCTS; // disable RTS/CTS hardware flow control
  sc->current_tio.c_cflag |= CLOCAL | CREAD; // ignore modem control lines, enable receiver
  //
  // change device input configuration
  //
  sc->current_tio.c_iflag = IGNBRK | PARMRK; // characters with parity or framing errors will the prefixed by "\377\000"
  if(sc->parity != 'N')
    sc->current_tio.c_iflag |= INPCK;
  //
  // change device output configuration
  //
  sc->current_tio.c_oflag = 0;
  //
  // change device local configuration
  //
  sc->current_tio.c_lflag = 0;
  //
  // change the terminal special characters
  //
  sc->current_tio.c_cc[VMIN] = 1; // minimum number of characters for a noncanonical read
  sc->current_tio.c_cc[VTIME] = 1; // timeout (unit is 0.1s) for a noncanonical read
  //
  // apply new configuration
  //
  if(tcsetattr(sc->fd,TCSAFLUSH,&sc->current_tio) < 0)
  {
    fprintf(stderr,"unable to configure device %s\n",sc->device_name);
    goto e;
  }
  return 0;
}

#if 0
static int control_rts_line(serial_connection *sc,int desired_value)
{ // man tty_ioctl
  int bits_to_change = TIOCM_RTS;

  if(ioctl(sc->fd,(desired_value != 0) ? TIOCMSET : TIOCMBIC,&bits_to_change) < 0)
  {
    fprintf(stderr,"unable to change the RTS bit of device %s\n",sc->device_name);
    return -1;
  }
  return 0;
}
#endif

static void close_serial_connection(serial_connection *sc)
{
  if(tcsetattr(sc->fd,TCSAFLUSH,&sc->original_tio) < 0)
    fprintf(stderr,"unable to restore configuration of device %s\n",sc->device_name);
  if(flock(sc->fd,LOCK_UN) < 0)
    fprintf(stderr,"unable to unlock device %s\n",sc->device_name);
  if(close(sc->fd) < 0)
    fprintf(stderr,"unable to close device %s\n",sc->device_name);
  sc->fd = -1;
}

static int serial_connection_write(serial_connection *sc,char byte_to_send)
{
  return (write(sc->fd,(void *)&byte_to_send,(size_t)1) != (ssize_t)1) ? -1 : 0;
}

static int serial_connection_read(serial_connection *sc)
{
  char byte_received;

  return (read(sc->fd,(void *)&byte_received,(size_t)1) == (ssize_t)1) ? (int)byte_received & 0xFF : -1;
}


//
// the main terminal
//


static volatile int must_exit;

static void exit_request(int dummy)
{
  signal(SIGINT,exit_request);
  signal(SIGTERM,exit_request);
  must_exit = 1;
}

static void set_terminal_title(char *title)
{
  fprintf(stderr,"\e]0;%s\007",title); // set window title
}

static void banner(FILE *fp)
{
  fprintf(fp,"\n");
  fprintf(fp,"Terminal emulator (LSD.TOS, April 2018)\n");
  fprintf(fp,"DETI, Universidade de Aveiro\n");
  fprintf(fp,"\n");
}

static void help(void)
{
  banner(stderr);
  fprintf(stderr,"Usage: lsd_term [-t] [-d device_name] [com_spec]\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"com_spec is either\n");
  fprintf(stderr,"    baud_rate\n");
  fprintf(stderr,"or\n");
  fprintf(stderr,"    baud_rate,parity,data_bits,stop_bits\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"baud_rate is one of 600, 1200, 1800, 2400, 4800, 9600, 19200, 38400, 57600,\n");
  fprintf(stderr,"                    115200, 230400, 460800, 921600, 1152000\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"parity is one of N (none), E (even), O (odd)\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"data_bits is one of 5, 6, 7, 8\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"stop_bits is one of 1, 2\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"the default com_spec is 115200,N,8,1\n");
  fprintf(stderr,"the default device_name is " default_device_name "\n");
  fprintf(stderr,"\n");
  fprintf(stderr,"the -t option turns on test mode (one character displayed per line)\n");
  exit(1);
}

int main(int argc,char **argv)
{
  struct termios terminal_tio;
  struct timeval time_out;
  char c,*s,*e,title[64];
  serial_connection sc;
  fd_set read_mask;
  time_t t,tr,tw;
  int i,fd,test;
  long l;

  sc.device_name = default_device_name;
  sc.baud_rate = 115200;
  sc.data_bits = 8;
  sc.stop_bits = 1;
  sc.parity = 'N';
  test = 0;
  //
  // process command line arguments
  //
  i = 1;
  if(i < argc && argv[i][0] == '-' && argv[i][1] == 't')
  { // turn on test mode
    test = 1;
    i++;
  }
  if(i + 1 < argc && argv[i][0] == '-' && argv[i][1] == 'd')
  { // change device name
    sc.device_name = argv[i + 1];
    i += 2;
  }
  if(i < argc)
  { // parse com_spec
    s = argv[i++];
    l = strtol(s,&e,10);
    if(e == s || l < 600l || l > 115200l)
      help();
    sc.baud_rate = (int)l;
    if(e[0] != '\0')
    {
      if(e[0] != ',' || (e[1] != 'N' && e[1] != 'E' && e[1] != 'O') || e[2] != ',')
        help();
      sc.parity = e[1];
      s = e + 3;
      l = strtol(s,&e,10);
      if(e == s || l < 5l || l > 8l || e[0] != ',')
        help();
      sc.data_bits = (int)l;
      s = e + 1;
      l = strtol(s,&e,10);
      if(e == s || l < 1l || l > 2l || e[0] != '\0')
        help();
      sc.stop_bits = (int)l;
    }
  }
  //
  // make sure we are running at a terminal
  //
  if(isatty(fileno(stdin)) == 0)
  {
    fprintf(stderr,"ERROR: standard input is not a tty device\n");
    exit(1);
  }
  if(isatty(fileno(stdout)) == 0)
  {
    fprintf(stderr,"ERROR: standard output is not a tty device\n");
    exit(1);
  }
  //
  // open connection
  //
  if(open_serial_connection(&sc) < 0)
    exit(1);
  //
  // print configuration
  //
  printf("\e[32m"); // man console_codes
  banner(stdout);
  printf("device_name ... %s\n",sc.device_name);
  printf("baud rate ..... %d\n",sc.baud_rate);
  printf("parity ........ %s\n",(sc.parity == 'N') ? "none" : ((sc.parity == 'E') ? "even" : "odd"));
  printf("data bits ..... %d\n",sc.data_bits);
  printf("stop bits ..... %d\n",sc.stop_bits);
  printf("\n");
  printf("--- data send by the FPGA starts in the next line ---\n");
  printf("\e[0m");
  fflush(stdout);
  if(snprintf(title,sizeof(title),"lsd_term %d,%c,%d,%d on %s",sc.baud_rate,sc.parity,sc.data_bits,sc.stop_bits,sc.device_name) >= sizeof(title))
    sprintf(title,"lsd_term %d,%c,%d,%d",sc.baud_rate,sc.parity,sc.data_bits,sc.stop_bits);
  set_terminal_title(title);
  //
  // disable terminal echo
  //
  fd = fileno(stdin);
  if(tcgetattr(fd,&terminal_tio) < 0)
    fprintf(stderr,"WARNING: tcgetattr() failed on the terminal\n");
  else
  {
    terminal_tio.c_lflag &= ~(ICANON | ECHO);
    if(tcsetattr(fd,TCSANOW,&terminal_tio) < 0)
      fprintf(stderr,"WARNING: tcsetattr() failed on the terminal\n");
  }
  //
  // main loop
  //
  signal(SIGINT,exit_request);
  signal(SIGTERM,exit_request);
  must_exit = 0;
  tr = tw = (time_t)0;
  for(;must_exit == 0;)
  {
    FD_ZERO(&read_mask);
    FD_SET(fd,&read_mask);
    FD_SET(sc.fd,&read_mask);
    time_out.tv_sec = 1;  // wake-up the program every second (currenty a wake up event without input/output activity does nothing)
    time_out.tv_usec = 0;
    if(select(1 + (fd > sc.fd ? fd : sc.fd),&read_mask,NULL,NULL,&time_out) > 0)
    {
      if(FD_ISSET(sc.fd,&read_mask))
      { // FPGA -> terminal
        i = serial_connection_read(&sc);
        if(i >= 0)
        {
          if(test == 0)
            printf("%c",(char)i);
          else
            printf("received code 0x%02X (%c)\n",i,(i >= 32 && i < 127) ? (char)i : ' ');
        }
        else
        {
          t = time(NULL);
          if(t != tr)
          {
            tr = t;
            printf("\e[31m<read error>\e[0m"); // only one read error message per second!
          }
        }
        fflush(stdout);
      }
      if(FD_ISSET(fd,&read_mask) && read(fd,&c,(size_t)1) == (ssize_t)1)
      { // terminal -> FPGA
        if(serial_connection_write(&sc,c) < 0)
        {
          t = time(NULL);
          if(t != tw)
          {
            tw = t;
            printf("\e[31m<write error>\e[0m"); // only one write error message per second!
          }
          fflush(stdout);
        }
        else if(test != 0)
          printf("sent code 0x%02X (%c)\n",(int)c & 0xFF,(c >= (char)32 && c < (char)127) ? c : ' ');
      }
    }
  }
  //
  // clean up
  //
  close_serial_connection(&sc);
  if(tcgetattr(fd,&terminal_tio) < 0)
    fprintf(stderr,"WARNING: tcgetattr() failed on the terminal\n");
  else
  {
    terminal_tio.c_lflag |= ICANON | ECHO;
    if(tcsetattr(fd,TCSANOW,&terminal_tio) < 0)
      fprintf(stderr,"WARNING: tcsetattr() failed on the terminal\n");
  }
  printf("\nGoodbye!\n");
  set_terminal_title("");
  return 0;
}
