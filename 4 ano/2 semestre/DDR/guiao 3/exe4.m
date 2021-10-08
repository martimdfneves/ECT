%4a
C=10;
f=10^6;
b=0;
P=100000;
N = 10; %number of simulations
lambda=1800;
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);
for j= 1:N
    [PL(j),APD(j),MPD(j),TT(j)]= simulator3(lambda,C,f,P,b);
end
alfa= 0.1; %90% confidence interval%
mediapl = mean(PL);
termpl = norminv(1-alfa/2)*sqrt(var(PL)./N);
mediaapd = mean(APD);
termapd = norminv(1-alfa/2)*sqrt(var(APD)./N);
mediampd = mean(MPD);
termmpd = norminv(1-alfa/2)*sqrt(var(MPD)./N);
mediatt = mean(TT);
termtt = norminv(1-alfa/2)*sqrt(var(TT)./N);

fprintf('Packet Loss (%%) = %.4e +- %.4e\n',mediapl,termpl)
fprintf('Av. Packet delay (ms) = %.4e +- %.4e\n',mediaapd,termapd)
fprintf('Max. Packet Delay (ms) = %.4e +- %.4e\n',mediampd,termmpd)
fprintf('Throughput (Mbps) = %.4e +- %.4e\n',mediatt,termtt)

%4b
C=10;
fb=10^4;
bb=10^-5;
P=100000;
N=10;
lambda=1800;
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);

for j= 1:N
    [PL(j),APD(j),MPD(j),TT(j)]= simulator3(lambda,C,fb,P,bb);
end
alfa= 0.1; %90% confidence interval%
mediaplb = mean(PL);
termplb = norminv(1-alfa/2)*sqrt(var(PL)./N);
mediaapdb = mean(APD);
termapdb = norminv(1-alfa/2)*sqrt(var(APD)./N);
mediampdb = mean(MPD);
termmpdb = norminv(1-alfa/2)*sqrt(var(MPD)./N);
mediattb = mean(TT);
termttb = norminv(1-alfa/2)*sqrt(var(TT)./N);

fprintf('Packet Loss (%%) = %.4e +- %.4e\n',mediaplb,termplb)
fprintf('Av. Packet delay (ms) = %.4e +- %.4e\n',mediaapdb,termapdb)
fprintf('Max. Packet Delay (ms) = %.4e +- %.4e\n',mediampdb,termmpdb)
fprintf('Throughput (Mbps) = %.4e +- %.4e\n',mediattb,termttb)
%}

%4c
fc=10^7;
lambdac=[1500,1600,1700,1800,1900,2000];
PL3=zeros(1,N);
APD3=zeros(1,N);
MPD3=zeros(1,N);
TT3=zeros(1,N);
PL2=zeros(1,N);
APD2=zeros(1,N);
MPD2=zeros(1,N);
TT2=zeros(1,N);
%{
for i=1:length(lambdac)
    for j= 1:N
        [PL2(j),APD2(j),MPD2(j),TT2(j)]= simulator2(lambdac(i),C,fc,P,b);
        [PL3(j),APD3(j),MPD3(j),TT3(j)]= simulator3(lambdac(i),C,fc,P,b);
    end
    alfa= 0.1; %90% confidence interval%
    mediaplc2(i) = mean(PL2);
    termplc2(i) = norminv(1-alfa/2)*sqrt(var(PL2)/N);
    mediaapdc2(i) = mean(APD2);
    termapdc2(i) = norminv(1-alfa/2)*sqrt(var(APD2)/N);
    mediampdc2(i) = mean(MPD2);
    termmpdc2(i) = norminv(1-alfa/2)*sqrt(var(MPD2)/N);
    mediattc2(i) = mean(TT2);
    termttc2(i) = norminv(1-alfa/2)*sqrt(var(TT2)/N);
    
    mediaplc3(i) = mean(PL3);
    termplc3(i) = norminv(1-alfa/2)*sqrt(var(PL3)/N);
    mediaapdc3(i) = mean(APD3);
    termapdc3(i) = norminv(1-alfa/2)*sqrt(var(APD3)/N);
    mediampdc3(i) = mean(MPD3);
    termmpdc3(i) = norminv(1-alfa/2)*sqrt(var(MPD3)/N);
    mediattc3(i) = mean(TT3);
    termttc3(i) = norminv(1-alfa/2)*sqrt(var(TT3)/N);
end

figure(1)
bar(lambdac,[mediaplc2;mediaplc3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('%')
title('Packet Loss (%)')

figure(2)
bar(lambdac,[mediaapdc2;mediaapdc3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('Milliseconds')
title('Average Packet Delay (milliseconds)')

figure(3)
bar(lambdac,[mediampdc2;mediampdc3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('Milliseconds')
title('Maximum Packet Delay (milliseconds)')

figure(4)
bar(lambdac,[mediattc2;mediattc3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('Mbps')
title('Transmited Throughput (Mbps)')
%}

%4d
fd=[2500,5000,7500,10000,12500,15000,17500,20000];
PL3=zeros(1,N);
APD3=zeros(1,N);
MPD3=zeros(1,N);
TT3=zeros(1,N);
PL2=zeros(1,N);
APD2=zeros(1,N);
MPD2=zeros(1,N);
TT2=zeros(1,N);
%{
for i=1:length(fd)
    for j= 1:N
        [PL2(j),APD2(j),MPD2(j),TT2(j)]= simulator2(lambda,C,fd(i),P,b);
        [PL3(j),APD3(j),MPD3(j),TT3(j)]= simulator3(lambda,C,fd(i),P,b);
    end
    alfa= 0.1; %90% confidence interval%
    mediapld2(i) = mean(PL2);
    termpld2(i) = norminv(1-alfa/2)*sqrt(var(PL2)/N);
    mediaapdd2(i) = mean(APD2);
    termapdd2(i) = norminv(1-alfa/2)*sqrt(var(APD2)/N);
    mediampdd2(i) = mean(MPD2);
    termmpdd2(i) = norminv(1-alfa/2)*sqrt(var(MPD2)/N);
    mediattd2(i) = mean(TT2);
    termttd2(i) = norminv(1-alfa/2)*sqrt(var(TT2)/N);
    
    mediapld3(i) = mean(PL3);
    termpld3(i) = norminv(1-alfa/2)*sqrt(var(PL3)/N);
    mediaapdd3(i) = mean(APD3);
    termapdd3(i) = norminv(1-alfa/2)*sqrt(var(APD3)/N);
    mediampdd3(i) = mean(MPD3);
    termmpdd3(i) = norminv(1-alfa/2)*sqrt(var(MPD3)/N);
    mediattd3(i) = mean(TT3);
    termttd3(i) = norminv(1-alfa/2)*sqrt(var(TT3)/N);
end

figure(5)
bar(fd,[mediapld2;mediapld3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('%')
title('Packet Loss (%)')

figure(6)
bar(fd,[mediaapdd2;mediaapdd3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('Milliseconds')
title('Average Packet Delay (milliseconds)')

figure(7)
bar(fd,[mediampdd2;mediampdd3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('Milliseconds')
title('Maximum Packet Delay (milliseconds)')

figure(8)
bar(fd,[mediattd2;mediattd3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('Mbps')
title('Transmited Throughput (Mbps)')
%}

%4e
PL3=zeros(1,N);
APD3=zeros(1,N);
MPD3=zeros(1,N);
TT3=zeros(1,N);
PL2=zeros(1,N);
APD2=zeros(1,N);
MPD2=zeros(1,N);
TT2=zeros(1,N);

for i=1:length(lambdac)
    for j= 1:N
        [PL2(j),APD2(j),MPD2(j),TT2(j)]= simulator2(lambdac(i),C,fc,P,bb);
        [PL3(j),APD3(j),MPD3(j),TT3(j)]= simulator3(lambdac(i),C,fc,P,bb);
    end
    alfa= 0.1; %90% confidence interval%
    mediaple2(i) = mean(PL2);
    termple2(i) = norminv(1-alfa/2)*sqrt(var(PL2)/N);
    mediaapde2(i) = mean(APD2);
    termapde2(i) = norminv(1-alfa/2)*sqrt(var(APD2)/N);
    mediampde2(i) = mean(MPD2);
    termmpde2(i) = norminv(1-alfa/2)*sqrt(var(MPD2)/N);
    mediatte2(i) = mean(TT2);
    termtte2(i) = norminv(1-alfa/2)*sqrt(var(TT2)/N);
    
    mediaple3(i) = mean(PL3);
    termple3(i) = norminv(1-alfa/2)*sqrt(var(PL3)/N);
    mediaapde3(i) = mean(APD3);
    termapde3(i) = norminv(1-alfa/2)*sqrt(var(APD3)/N);
    mediampde3(i) = mean(MPD3);
    termmpde3(i) = norminv(1-alfa/2)*sqrt(var(MPD3)/N);
    mediatte3(i) = mean(TT3);
    termtte3(i) = norminv(1-alfa/2)*sqrt(var(TT3)/N);
end

figure(9)
bar(lambdac,[mediaple2;mediaple3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('%')
title('Packet Loss (%)')

figure(10)
bar(lambdac,[mediaapde2;mediaapde3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('Milliseconds')
title('Average Packet Delay (milliseconds)')

figure(11)
bar(lambdac,[mediampde2;mediampde3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('Milliseconds')
title('Maximum Packet Delay (milliseconds)')

figure(12)
bar(lambdac,[mediatte2;mediatte3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('lambda values')
ylabel('Mbps')
title('Transmited Throughput (Mbps)')
%}

%4f
PL3=zeros(1,N);
APD3=zeros(1,N);
MPD3=zeros(1,N);
TT3=zeros(1,N);
PL2=zeros(1,N);
APD2=zeros(1,N);
MPD2=zeros(1,N);
TT2=zeros(1,N);
%{
for i=1:length(fd)
    for j= 1:N
        [PL2(j),APD2(j),MPD2(j),TT2(j)]= simulator2(lambda,C,fd(i),P,bb);
        [PL3(j),APD3(j),MPD3(j),TT3(j)]= simulator3(lambda,C,fd(i),P,bb);
    end
    alfa= 0.1; %90% confidence interval%
    mediaplf2(i) = mean(PL2);
    termplf2(i) = norminv(1-alfa/2)*sqrt(var(PL2)/N);
    mediaapdf2(i) = mean(APD2);
    termapdf2(i) = norminv(1-alfa/2)*sqrt(var(APD2)/N);
    mediampdf2(i) = mean(MPD2);
    termmpdf2(i) = norminv(1-alfa/2)*sqrt(var(MPD2)/N);
    mediattf2(i) = mean(TT2);
    termttf2(i) = norminv(1-alfa/2)*sqrt(var(TT2)/N);
    
    mediaplf3(i) = mean(PL3);
    termplf3(i) = norminv(1-alfa/2)*sqrt(var(PL3)/N);
    mediaapdf3(i) = mean(APD3);
    termapdf3(i) = norminv(1-alfa/2)*sqrt(var(APD3)/N);
    mediampdf3(i) = mean(MPD3);
    termmpdf3(i) = norminv(1-alfa/2)*sqrt(var(MPD3)/N);
    mediattf3(i) = mean(TT3);
    termttf3(i) = norminv(1-alfa/2)*sqrt(var(TT3)/N);
end

figure(13)
bar(fd,[mediaplf2;mediaplf3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('%')
title('Packet Loss (%)')

figure(14)
bar(fd,[mediaapdf2;mediaapdf3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('Milliseconds')
title('Average Packet Delay (milliseconds)')

figure(15)
bar(fd,[mediampdf2;mediampdf3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('Milliseconds')
title('Maximum Packet Delay (milliseconds)')

figure(16)
bar(fd,[mediattf2;mediattf3])
grid on
legend('Simulador 2','Simulador 3')
xlabel('queue size')
ylabel('Mbps')
title('Transmited Throughput (Mbps)')
%}