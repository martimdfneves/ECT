import socket
import signal
import sys
import time

def signal_handler(sig, frame):
    print('\nDone!')
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)
print('Press Ctrl+C to exit...')

##

ip_addr = "127.0.0.1"
tcp_port = 5005

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

sock.connect((ip_addr, tcp_port))

while True:
    try:
        time.sleep(10)
        req = "request"
        print("Requesting CPU and Memory stats...") 
        message=req.encode()
        if len(message)>=0:
            sock.send(message)
            response = sock.recv(4096).decode()
            print('Server response: {}'.format(response))
    except (socket.timeout, socket.error):
        print('Server error. Done!')
        sys.exit(0)

