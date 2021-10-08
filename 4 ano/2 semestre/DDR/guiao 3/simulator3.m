function [PL , APD , MPD , TT] = simulator3(lambda,C,f,P,b)
% INPUT PARAMETERS:
%  lambda - packet rate (packets/sec)
%  C      - link bandwidth (Mbps)
%  f      - queue size (Bytes)
%  P      - number of packets (stopping criterium)
% OUTPUT PARAMETERS:
%  PL   - packet loss (%)
%  APD  - average packet delay (milliseconds)
%  MPD  - maximum packet delay (milliseconds)
%  TT   - transmitted throughput (Mbps)

% Initializing flow state:
time = 1/10;
rate = [0.5 1 1.5]*lambda;
la = [ 5 10 ];
mu = [ 10 5 ];
div_mat = la./mu;
div_mat_mult = [    div_mat( 1 )
                    div_mat( 1 )*div_mat( 2 )];

p0 = 1/(1+sum(div_mat_mult));
p1 = p0*div_mat_mult( 1 );
p2 = p0*div_mat_mult( 2 );
p = rand;
if p<p0
    FLOWSTATE = 1;
elseif p<(p0+p1)
    FLOWSTATE = 2;
else
    FLOWSTATE = 3;
end

%Events:
ARRIVAL= 0;       % Arrival of a packet            
DEPARTURE= 1;     % Departure of a packet
TRANSITION = 2;   % Transition between states

%State variables:
STATE = 0;          % 0 - connection free; 1 - connection bysy
QUEUEOCCUPATION= 0; % Occupation of the queue (in Bytes)
QUEUE= [];          % Size and arriving time instant of each packet in the queue

%Statistical Counters:
TOTALPACKETS= 0;       % No. of packets arrived to the system
LOSTPACKETS= 0;        % No. of packets dropped due to buffer overflow
TRANSMITTEDPACKETS= 0; % No. of transmitted packets
TRANSMITTEDBYTES= 0;   % Sum of the Bytes of transmitted packets
DELAYS= 0;             % Sum of the delays of transmitted packets
MAXDELAY= 0;           % Maximum delay among all transmitted packets

%Auxiliary variables:
% Initializing the simulation clock:
Clock= 0;

% Initializing the List of Events with the first ARRIVAL:
EventList = [ARRIVAL, Clock + exprnd(1/(rate(FLOWSTATE))), GeneratePacketSize(), 0];
EventList = [EventList; TRANSITION, Clock + exprnd(time), 0, 0];


%Similation loop:
while TRANSMITTEDPACKETS<P               % Stopping criterium
    EventList= sortrows(EventList,2);    % Order EventList by time
    Event= EventList(1,1);               % Get first event and 
    Clock= EventList(1,2);               %   and
    PacketSize= EventList(1,3);          %   associated
    ArrivalInstant= EventList(1,4);      %   parameters.
    EventList(1,:)= [];                  % Eliminate first event
    switch Event
        case ARRIVAL                     % If first event is an ARRIVAL
            TOTALPACKETS= TOTALPACKETS+1;
            EventList = [EventList; ARRIVAL, Clock + exprnd(1/(rate(FLOWSTATE))), GeneratePacketSize(), 0];
            if STATE==0
                STATE= 1;
                EventList = [EventList; DEPARTURE, Clock + 8*PacketSize/(C*10^6), PacketSize, Clock];
            else
                if QUEUEOCCUPATION + PacketSize <= f
                    QUEUE= [QUEUE;PacketSize , Clock];
                    QUEUEOCCUPATION= QUEUEOCCUPATION + PacketSize;
                else
                    LOSTPACKETS= LOSTPACKETS + 1;
                end
            end
        case DEPARTURE                     % If first event is a DEPARTURE
            if rand() < (1-b)^(8*PacketSize)
                TRANSMITTEDPACKETS= TRANSMITTEDPACKETS + 1;
                TRANSMITTEDBYTES= TRANSMITTEDBYTES + PacketSize;
                DELAYS= DELAYS + (Clock - ArrivalInstant);
                if Clock - ArrivalInstant > MAXDELAY
                    MAXDELAY= Clock - ArrivalInstant;
                end
            else
               LOSTPACKETS = LOSTPACKETS + 1; 
            end
            
            if QUEUEOCCUPATION > 0
                EventList = [EventList; DEPARTURE, Clock + 8*QUEUE(1,1)/(C*10^6), QUEUE(1,1), QUEUE(1,2)];
                QUEUEOCCUPATION= QUEUEOCCUPATION - QUEUE(1,1);
                QUEUE(1,:)= [];
            else
                STATE= 0;
            end
        case TRANSITION
            EventList = [EventList; TRANSITION, Clock + exprnd(time), 0, 0];
            if FLOWSTATE ~= 2
               FLOWSTATE = 2;
            else
                if rand<0.5
                   FLOWSTATE = 3;
                else
                   FLOWSTATE = 1;
                end
            end
    end
end

%Performance parameters determination:
PL= 100*LOSTPACKETS/TOTALPACKETS;      % in %
APD= 1000*DELAYS/TRANSMITTEDPACKETS;   % in milliseconds
MPD= 1000*MAXDELAY;                    % in milliseconds
TT= 10^(-6)*TRANSMITTEDBYTES*8/Clock;  % in Mbps

end

function out= GeneratePacketSize()
    aux= rand();
    aux2= [65:109 111:1517];
    if aux <= 0.16
        out= 64;
    elseif aux <= 0.16 + 0.25
        out= 110;
    elseif aux <= 0.16 + 0.25 + 0.2
        out= 1518;
    else
        out = aux2(randi(length(aux2)));
    end
end