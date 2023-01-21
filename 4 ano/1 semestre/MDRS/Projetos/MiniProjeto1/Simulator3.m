function [PLdata , APDdata, MPDdata , TT , PLvoip, APDvoip, MPDvoip ] = Simulator3(lambda,C,f,P,n)
% INPUT PARAMETERS:
%  lambda - packet rate (packets/sec)
%  C      - link bandwidth (Mbps)
%  f      - queue size (Bytes)
%  P      - number of packets (stopping criterium)
%  n      - additional VoIP packet flows (sec)
% OUTPUT PARAMETERS:
%  PLdata   - packet loss of data packets (%)
%  APDdata  - average delay of data packets (milliseconds)
%  MPDdata  - maximum delay of data packets (milliseconds)
%  PLvoip   - packet loss of voip packets (%)
%  APDvoip  - average delay of voip packets (milliseconds)
%  MPDvoip  - maximum delay of voip packets (milliseconds)
%  TT       - transmitted throughput (Mbps)
% EVENTS:
ARRIVAL = 0;       % Arrival of a packet            
DEPARTURE = 1;     % Departure of a packet
DATA = 2;
VOIP = 3;
% STATE VARIABLES:
STATE = 0;              % 0 - connection free; 1 - connection bysy
QUEUEOCCUPATION = 0;    % Occupation of the queue (in Bytes)
QUEUE = [];             % Size and arriving time instant of each packet in the queue
% STATISTICAL COUNTERS:
TOTALPACKETS = 0;            % No. of packets arrived to the system
TOTALVOIPPACKETS = 0;        % No. of voip packets arrived to the systeM
LOSTPACKETS = 0;             % No. of packets dropped due to buffer overflow
LOSTVOIPPACKETS = 0;         % No. of voip packets dropped due to buffer overflow
TRANSMITTEDPACKETS = 0;      % No. of transmitted packets
TRANSMITTEDVOIPPACKETS = 0;  % No. of voip transmitted packets
TRANSMITTEDBYTES = 0;        % Sum of the Bytes of transmitted packets
DELAYS = 0;                  % Sum of the delays of transmitted packets
VOIPDELAYS = 0;              % Sum of the delays of transmitted voip packets
MAXDELAY = 0;                % Maximum delay among all transmitted packets
MAXVOIPDELAY = 0;            % Maximum delay among all transmitted voip packets
% INITIALIZING THE SIMULATION CLOCK:
Clock = 0;
% INITIALIZING THE LIST OF EVENTS WITH THE FIRST ARRIVAL:
tmp = Clock + exprnd(1/lambda);
EventList = [ARRIVAL, tmp, GeneratePacketSize(), tmp, DATA];
for i=1:n
    tmp = Clock + 0.02*rand();
    EventList = [EventList; ARRIVAL, tmp, GeneratePacketSizeVoip(), tmp, VOIP];
end 
% SIMILATION LOOP:
while (TRANSMITTEDPACKETS + TRANSMITTEDVOIPPACKETS) < P   % Stopping criterium
    EventList = sortrows(EventList,2);    % Order EventList by time
    Event = EventList(1,1);               % Get first event  
    Clock = EventList(1,2);              
    PacketSize = EventList(1,3);         
    ArrivalInstant = EventList(1,4);     
    PacketType = EventList(1,5);
    EventList(1,:)= [];                  % Eliminate first event
    switch Event
        case ARRIVAL        % If first event is an ARRIVAL
            if PacketType == DATA
                TOTALPACKETS = TOTALPACKETS+1;
                tmp = Clock + exprnd(1/lambda);
                EventList = [EventList; ARRIVAL, tmp, GeneratePacketSize(), tmp, PacketType];
                if STATE == 0
                    STATE = 1;
                    EventList = [EventList; DEPARTURE, Clock + 8*PacketSize/(C*10^6), PacketSize, Clock, PacketType];
                else
                    if QUEUEOCCUPATION + PacketSize <= f
                        QUEUE = [QUEUE;PacketSize , Clock, PacketType];
                        QUEUEOCCUPATION = QUEUEOCCUPATION + PacketSize;
                    else
                        LOSTPACKETS = LOSTPACKETS + 1;
                    end
                end
            else
                TOTALVOIPPACKETS = TOTALVOIPPACKETS+1;
                tmp = Clock + 0.008*rand() + 0.016;
                EventList = [EventList; ARRIVAL, tmp, GeneratePacketSizeVoip(), tmp, PacketType];
                if STATE == 0
                    STATE = 1;
                    EventList = [EventList; DEPARTURE, Clock + 8*PacketSize/(C*10^6), PacketSize, Clock, PacketType];
                else
                    if QUEUEOCCUPATION + PacketSize <= f
                        QUEUE = [QUEUE;PacketSize , Clock, PacketType];
                        QUEUEOCCUPATION = QUEUEOCCUPATION + PacketSize;
                    else
                        LOSTVOIPPACKETS = LOSTVOIPPACKETS + 1;
                    end
                end
            end
        case DEPARTURE               % If first event is a DEPARTURE      
            if PacketType == DATA
                TRANSMITTEDBYTES = TRANSMITTEDBYTES + PacketSize;
                DELAYS = DELAYS + (Clock - ArrivalInstant);
                if Clock - ArrivalInstant > MAXDELAY
                    MAXDELAY = Clock - ArrivalInstant;
                end
                TRANSMITTEDPACKETS = TRANSMITTEDPACKETS + 1;
                if QUEUEOCCUPATION > 0
                    EventList = [EventList; DEPARTURE, Clock + 8*QUEUE(1,1)/(C*10^6), QUEUE(1,1), QUEUE(1,2), QUEUE(1,3)];
                    QUEUEOCCUPATION = QUEUEOCCUPATION - QUEUE(1,1);
                    QUEUE(1,:) = [];
                else
                    STATE = 0;
                end
            end
            if PacketType == VOIP
                TRANSMITTEDBYTES = TRANSMITTEDBYTES + PacketSize;
                VOIPDELAYS = VOIPDELAYS + (Clock - ArrivalInstant);
                if Clock - ArrivalInstant > MAXVOIPDELAY
                    MAXVOIPDELAY = Clock - ArrivalInstant;
                end
                TRANSMITTEDVOIPPACKETS = TRANSMITTEDVOIPPACKETS + 1;
                if QUEUEOCCUPATION > 0
                    EventList = [EventList; DEPARTURE, Clock + 8*QUEUE(1,1)/(C*10^6), QUEUE(1,1), QUEUE(1,2), QUEUE(1,3)];
                    QUEUEOCCUPATION = QUEUEOCCUPATION - QUEUE(1,1);
                    QUEUE(1,:) = [];
                else
                    STATE= 0;
                end
            end
    end
end
% PERFORMANCE PARAMETERS DETERMINATION:
PLdata = 100*LOSTPACKETS/TOTALPACKETS;              % in %
APDdata = 1000*DELAYS/TRANSMITTEDPACKETS;           % in milliseconds
MPDdata = 1000*MAXDELAY;                            % in milliseconds
PLvoip = 100*LOSTVOIPPACKETS/TOTALVOIPPACKETS;      % packet loss VOIP
APDvoip = 1000*VOIPDELAYS/TRANSMITTEDVOIPPACKETS;   % average packet delay VOIP
MPDvoip = 1000*MAXVOIPDELAY;                        % maximum packet delay VOIP
TT = 10^(-6)*TRANSMITTEDBYTES*8/Clock;              % in Mbps
end

function out = GeneratePacketSize()
    aux = rand();
    aux2 = [65:109 111:1517];
    if aux <= 0.19
        out= 64;
    elseif aux <= 0.19 + 0.23
        out = 110;
    elseif aux <= 0.19 + 0.23 + 0.17
        out = 1518;
    else
        out = aux2(randi(length(aux2)));
    end
end

function out = GeneratePacketSizeVoip()
    out = randi([110 130]);
end