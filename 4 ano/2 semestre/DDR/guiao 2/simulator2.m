function [bHD b4K]= simulator2(lambda,p,n,S,W,R,fname)
    %lambda = request arrival rate (in requests per hour)
    %p=       percentage of requests for 4K movies (%)
    %n=       number of servers
    %S=       interface capacity of each server (Mbps)
    %W=       resource reservation for 4K movies (Mbps)
    %R=       stop simulation on ARRIVAL no. R
    %fname=   filename with the duration of each movie
    
    invlambda=60/lambda;     %average time between requests (in minutes)
    invmiu= load(fname);     %duration (in minutes) of each movie
    Nmovies= length(invmiu); % number of movies
    
    %Events definition:
    ARRIVAL= 0;           %movie request
    DEPARTURE_HD= 1;      %termination of a HD movie transmission
    DEPARTURE_4K= 2;      %termination of a 4K movie transmission
    %State variables initialization:
    STATE= zeros(1,n);
    STATE_HD=0;
    %Statistical counters initialization:
    REQUESTS_HD=0;
    REQUESTS_4K=0;
    BLOCKED_HD=0;
    BLOCKED_4K=0;
    %Simulation Clock and initial List of Events:
    Clock= 0;
    EventList= [ARRIVAL exprnd(invlambda) 0];
    C = n*S;
    while (REQUESTS_HD + REQUESTS_4K) < R
        event= EventList(1,1);
        Clock= EventList(1,2);
        old=EventList(1,3);
        EventList(1,:)= [];
        if event == ARRIVAL
            EventList= [EventList; ARRIVAL Clock+exprnd(invlambda) 0];
            [val,pos]=min(STATE);
            if (rand * 100) < p
                REQUESTS_4K= REQUESTS_4K + 1;
                if val + 25 <= S
                    STATE(pos) = STATE(pos) + 25;
                    EventList= [EventList; DEPARTURE_4K Clock+invmiu(randi(Nmovies)) pos];
                else
                    BLOCKED_4K = BLOCKED_4K + 1;
                end
            else
                REQUESTS_HD= REQUESTS_HD + 1;
                if STATE_HD + 5 <= (C-W) && val + 5 <= S
                    STATE_HD = STATE_HD + 5;
                    STATE(pos) = STATE(pos) + 5;
                    EventList= [EventList; DEPARTURE_HD Clock+invmiu(randi(Nmovies)) pos];
                else
                    BLOCKED_HD = BLOCKED_HD + 1;
                end
            end    
        elseif event == DEPARTURE_4K
            STATE(old) = STATE(old) - 25;
        else
            STATE(old) = STATE(old) - 5;
            STATE_HD = STATE_HD - 5;
        end
        EventList= sortrows(EventList,2);
    end
    bHD= 100*BLOCKED_HD/REQUESTS_HD;    % blocking probability in %
    b4K= 100*BLOCKED_4K/REQUESTS_4K;
end