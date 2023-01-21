%1a)

lambda = [1500, 1600, 1700, 1800, 1900];
C=10;
f=1000000;
P=100000;
b=10^-6;
N=20;
alfa=0.1;
PL = zeros(1,N);
APD = zeros(1,N);
MPD = zeros(1,N);
TT= zeros(1,N);

for j=1:length(lambda)
    for i=1:N
        [PL(i), APD(i), MPD(i), TT(i)] = Simulator2(lambda(j),C,f,P,b);   
    end

    avgPL(j) = sum(PL)/N;
    termPL(j) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    
    avgAPD(j) = sum(APD)/N;
    termAPD(j) = norminv(1-alfa/2)*sqrt(var(APD)/N);
end

figure(1)
bar(lambda,avgAPD)
hold on
erro=errorbar(lambda,avgAPD,termAPD,termAPD);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('λ values')
ylabel('miliseconds')
title('Average Data Packet Delay (milliseconds) ')

figure(2)
bar(lambda,avgPL)
hold on
erro=errorbar(lambda,avgPL,termPL,termPL);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('λ values')
ylabel('%')
title('Average Data Packet Loss (%) ')


%1b)

lambda = [1500, 1600, 1700, 1800, 1900];
C=10;
f=1000000;
P=100000;
b=10^-4;     % Changed from 10^-6 to 10^-4
N=20;
alfa=0.1;
PL = zeros(1,N);
APD = zeros(1,N);
MPD = zeros(1,N);
TT= zeros(1,N);

for j=1:length(lambda)
    for i=1:N
        [PL(i), APD(i), MPD(i), TT(i)] = Simulator2(lambda(j),C,f,P,b);   
    end

    avgPL(j) = sum(PL)/N;
    termPL(j) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    
    avgAPD(j) = sum(APD)/N;
    termAPD(j) = norminv(1-alfa/2)*sqrt(var(APD)/N);
end

figure(3)
bar(lambda,avgAPD)
hold on
erro=errorbar(lambda,avgAPD,termAPD,termAPD);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('λ values')
ylabel('miliseconds')
title('Average Data Packet Delay (milliseconds) ')

figure(4)
bar(lambda,avgPL)
hold on
erro=errorbar(lambda,avgPL,termPL,termPL);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('λ values')
ylabel('%')
title('Average Data Packet Loss (%) ')

%1c)

aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.19+0.23+0.17))/aux3;
ber1=10^-6;
ber2=10^-4;
pl1=zeros(1,aux3+3);
pl2=zeros(1,aux3+3);
for i = 1:length(pl1)
    if i == 1
        pl1(i) = 0.19*(100*(1-(nchoosek((i+64-1)*8,0)*ber1^(0)*(1-ber1)^((i+64-1)*8-0))));
        pl2(i) = 0.19*(100*(1-(nchoosek((i+64-1)*8,0)*ber2^(0)*(1-ber2)^((i+64-1)*8-0))));
    elseif i == 110-64+1
        pl1(i) = 0.23*(100*(1-(nchoosek((i+64-1)*8,0)*ber1^(0)*(1-ber1)^((i+64-1)*8-0))));
        pl2(i) = 0.23*(100*(1-(nchoosek((i+64-1)*8,0)*ber2^(0)*(1-ber2)^((i+64-1)*8-0))));
    elseif i == 1518-64+1
        pl1(i) = 0.17*(100*(1-(nchoosek((i+64-1)*8,0)*ber1^(0)*(1-ber1)^((i+64-1)*8-0))));
        pl2(i) = 0.17*(100*(1-(nchoosek((i+64-1)*8,0)*ber2^(0)*(1-ber2)^((i+64-1)*8-0))));
    else
        pl1(i) = aux4*(100*(1-(nchoosek((i+64-1)*8,0)*ber1^(0)*(1-ber1)^((i+64-1)*8-0))));
        pl2(i) = aux4*(100*(1-(nchoosek((i+64-1)*8,0)*ber2^(0)*(1-ber2)^((i+64-1)*8-0))));
    end
end

apl1 = sum(pl1);
apl2 = sum(pl2);

fprintf("Theoretical average packet loss with BER = 10^-6 -> %.3f %%\n", apl1);
fprintf("Theoretical average packet loss with BER = 10^-4 -> %.3f %%\n", apl2);
