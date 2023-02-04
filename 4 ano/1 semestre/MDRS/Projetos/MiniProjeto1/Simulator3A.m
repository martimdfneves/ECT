function [PLdata , APDdata, MPDdata , TT , PLvoip, APDvoip, MPDvoip ] = Simulator3A(lambda,C,f,P,n,b)
% INPUT PARAMETERS:
%  lambda - packet rate (packets/sec)
%  C      - link bandwidth (Mbps)
%  f      - queue size (Bytes)
%  P      - number of data packets (stopping criterium)
%  n      - number of adidional VoIP packets
%  b      - bit error rate
% OUTPUT PARAMETERS:
%  PLdata  - packet loss of data packets(%)
%  APDdata - average packet delay of data packets(milliseconds)
%  MPDdata - maximum packet delay of data packets(milliseconds)
%  TT   - transmitted throughput (Mbps)
%  PLvoip	- packet loss of voip packets(%)
%  APDvoip - average packet delay of voip packets(milliseconds)
%  MPDvoip - maximum packet delay of voip packets(milliseconds)

%Events:
ARRIVAL= 0;       % Arrival of a packet            
DEPARTURE= 1;     % Departure of a packet

%State variables:
STATE = 0;          % 0 - connection free; 1 - connection bysy
QUEUEOCCUPATION= 0; % Occupation of the queue (in Bytes)
QUEUE= [];          % Size and arriving time instant of each packet in the queue

%Statistical Counters:
TOTALPACKETS= 0;       % No. of data packets arrived to the system
TOTALPACKETSV= 0;      % No. of voip packets arrived to the system
LOSTPACKETS= 0;        % No. of data packets dropped due to buffer overflow
LOSTPACKETSV = 0;      % No. of voip packets dropped due to buffer overflow
TRANSMITTEDPACKETS= 0; % No. of transmitted data packets
TRANSMITTEDPACKETSV=0; % No. of transmitted voip packets
TRANSMITTEDBYTES= 0;   % Sum of the Bytes of transmitted data packets
TRANSMITTEDBYTESV=0;   % Sum of the Bytes of transmitted voip packets
DELAYS= 0;             % Sum of the delays of data transmitted packets
DELAYSV = 0;           % Sum of the delays of voip transmitted packets
MAXDELAY= 0;           % Maximum delay among all data transmitted packets
MAXDELAYV = 0;         % Maximum delay among all voip transmitted packets

% Initializing the simulation clock:
Clock= 0;

% Initializing the List of Events with the first ARRIVAL:
tmp= Clock + exprnd(1/lambda);
EventList = [ARRIVAL, tmp, GeneratePacketSize(), tmp, 0];
% Generating VoIP packets
for i=1:n
    tmp = unifrnd(0, 0.02);
    EventList = [EventList; ARRIVAL, tmp, randi([110, 130]), tmp, 1];
end

%Similation loop:
while (TRANSMITTEDPACKETS+TRANSMITTEDPACKETSV)<P               % Stopping criterium
    EventList= sortrows(EventList,2);    % Order EventList by time
    Event= EventList(1,1);               % Get first event and 
    Clock= EventList(1,2);               %   and
    PacketSize= EventList(1,3);          %   associated
    ArrivalInstant= EventList(1,4);      %   parameters
    type = EventList(1,5);               %
    EventList(1,:)= [];                  % Eliminate first event
    switch Event
        case ARRIVAL                     % If first event is an ARRIVAL
            if type == 0  % Data packet
                TOTALPACKETS= TOTALPACKETS+1;
                tmp= Clock + exprnd(1/lambda);
                EventList = [EventList; ARRIVAL, tmp, GeneratePacketSize(), tmp, 0];
                if STATE==0
                    STATE= 1;
                    EventList = [EventList; DEPARTURE, Clock + 8*PacketSize/(C*10^6), PacketSize, Clock, 0];
                else
                    if QUEUEOCCUPATION + PacketSize <= f
                        QUEUE= [QUEUE;PacketSize , Clock, 0];
                        QUEUEOCCUPATION= QUEUEOCCUPATION + PacketSize;
                    else
                        LOSTPACKETS= LOSTPACKETS + 1;
                    end
                end
            elseif type == 1 % VoIP packet
                TOTALPACKETSV= TOTALPACKETSV+1;
                tmp= Clock + unifrnd(0.016,0.024);
                EventList = [EventList; ARRIVAL, tmp, randi([110,130]), tmp, 1];
                if STATE==0
                    STATE= 1;
                    EventList = [EventList; DEPARTURE, Clock + 8*PacketSize/(C*10^6), PacketSize, Clock, 1];
                else
                    if QUEUEOCCUPATION + PacketSize <= f
                        QUEUE= [QUEUE;PacketSize , Clock, 1];
                        QUEUEOCCUPATION= QUEUEOCCUPATION + PacketSize;
                    else
                        LOSTPACKETSV= LOSTPACKETSV + 1;
                    end
                end
            end
        

        case DEPARTURE                     % If first event is a DEPARTURE
            if type == 0 % Data Packet
                if rand() < (1-b)^(PacketSize*8) 
                    TRANSMITTEDBYTES= TRANSMITTEDBYTES + PacketSize;
                    DELAYS= DELAYS + (Clock - ArrivalInstant);
                    if Clock - ArrivalInstant > MAXDELAY
                        MAXDELAY= Clock - ArrivalInstant;
                    end
                    TRANSMITTEDPACKETS= TRANSMITTEDPACKETS + 1;
                else
                    LOSTPACKETS = LOSTPACKETS + 1; 
                end
                if QUEUEOCCUPATION > 0
                    EventList = [EventList; DEPARTURE, Clock + 8*QUEUE(1,1)/(C*10^6), QUEUE(1,1), QUEUE(1,2), QUEUE(1,3)];
                    QUEUEOCCUPATION= QUEUEOCCUPATION - QUEUE(1,1);
                    QUEUE(1,:)= [];
                else
                    STATE= 0;
                end
            elseif type == 1  % VoIP Packet
                if rand() < (1-b)^(PacketSize*8)
                    TRANSMITTEDBYTESV= TRANSMITTEDBYTESV + PacketSize;
                    DELAYSV= DELAYSV + (Clock - ArrivalInstant);
                    if Clock - ArrivalInstant > MAXDELAYV
                        MAXDELAYV= Clock - ArrivalInstant;
                    end
                    TRANSMITTEDPACKETSV= TRANSMITTEDPACKETSV + 1;
                else
                    LOSTPACKETSV = LOSTPACKETSV + 1;
                end
                if QUEUEOCCUPATION > 0
                    EventList = [EventList; DEPARTURE, Clock + 8*QUEUE(1,1)/(C*10^6), QUEUE(1,1), QUEUE(1,2), QUEUE(1,3)];
                    QUEUEOCCUPATION= QUEUEOCCUPATION - QUEUE(1,1);
                    QUEUE(1,:)= [];
                else
                    STATE= 0;
                end
            end
    end
end

%Performance parameters determination:
PLdata= 100*LOSTPACKETS/TOTALPACKETS;       % in %
APDdata= 1000*DELAYS/TRANSMITTEDPACKETS;    % in milliseconds
MPDdata = 1000*MAXDELAY;                     % in milliseconds
TT = 10^(-6)*(TRANSMITTEDBYTES+TRANSMITTEDBYTESV)*8/Clock;    % in Mbps
PLvoip = 100*LOSTPACKETSV/TOTALPACKETSV;    % in %
APDvoip = 1000*DELAYSV/TRANSMITTEDPACKETSV; % in milliseconds
MPDvoip = 1000*MAXDELAYV;                    % in milliseconds 
end

function out= GeneratePacketSize()
    aux= rand();
    aux2= [65:109 111:1517];
    if aux <= 0.19
        out= 64;
    elseif aux <= 0.19 + 0.23
        out= 110;
    elseif aux <= 0.19 + 0.23 + 0.17
        out= 1518;
    else
        out = aux2(randi(length(aux2)));
    end
end