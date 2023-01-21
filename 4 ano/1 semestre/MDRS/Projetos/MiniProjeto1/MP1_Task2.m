%2a)

lambda = 1500;
c=10;
f=1000000;
b=10^-5;
P=100000;
N=20;
n=[10,20,30,40];
alfa=0.1;
PLdata = zeros(1,N);
APDdata = zeros(1,N);
PLvoip = zeros(1,N);
APDvoip = zeros(1,N);
for i=1:length(n)

    for j=1:N
        [PLdata(j), APDdata(j), MPDdata(j), TT(j), PLvoip(j), APDvoip(j), MPDvoip(j)] = Simulator3A(lambda,c,f,P,n(i),b);   
    end

    avgPLdata(i) = mean(PLdata);
    termPLdata(i) = norminv(1-alfa/2)*sqrt(var(PLdata)/N);
    
    avgAPDdata(i) = mean(APDdata);
    termAPDdata(i) = norminv(1-alfa/2)*sqrt(var(APDdata)/N);
    
    avgPLvoip(i) = mean(PLvoip);
    termPLvoip(i) = norminv(1-alfa/2)*sqrt(var(PLvoip)/N);
    
    avgAPDvoip(i) = mean(APDvoip);
    termAPDvoip(i) = norminv(1-alfa/2)*sqrt(var(APDvoip)/N);
end

figure(1)
bar(n,avgAPDdata)
hold on
erro=errorbar(n,avgAPDdata,termAPDdata,termAPDdata);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('miliseconds')
title('Average Data Packet Delay (milliseconds)')

figure(2)
bar(n,avgPLdata)
hold on
erro=errorbar(n,avgPLdata,termPLdata,termPLdata);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('%')
title('Average Data Packet Loss (%)')

figure(3)
bar(n,avgAPDvoip)
hold on
erro=errorbar(n,avgAPDvoip,termAPDvoip,termAPDvoip);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('miliseconds')
title('Average VoIP Packet Delay (milliseconds)')

figure(4)
bar(n,avgPLvoip)
hold on
erro=errorbar(n,avgPLvoip,termPLvoip,termPLvoip);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('%')
title('Average VoIP Packet Loss (%)')


%2b)
lambda = 1500;
c=10;
fb=10000;
b=10^-5;
P=100000;
N=20;
n=[10,20,30,40];
alfa=0.1;
PLdata = zeros(1,N);
APDdata = zeros(1,N);
PLvoip = zeros(1,N);
APDvoip = zeros(1,N);

for i=1:length(n)
    for j=1:N
        [PLdata(j), APDdata(j), MPDdata(j), TT(j), PLvoip(j), APDvoip(j), MPDvoip(j)] = Simulator3A(lambda,c,fb,P,n(i),b);   
    end

    avgPLdata(i) = mean(PLdata);
    termPLdata(i) = norminv(1-alfa/2)*sqrt(var(PLdata)/N);
    
    avgAPDdata(i) = mean(APDdata);
    termAPDdata(i) = norminv(1-alfa/2)*sqrt(var(APDdata)/N);
    
    avgPLvoip(i) = mean(PLvoip);
    termPLvoip(i) = norminv(1-alfa/2)*sqrt(var(PLvoip)/N);
    
    avgAPDvoip(i) = mean(APDvoip);
    termAPDvoip(i) = norminv(1-alfa/2)*sqrt(var(APDvoip)/N);
end

figure(5)
bar(n,avgAPDdata)
hold on
erro=errorbar(n,avgAPDdata,termAPDdata,termAPDdata);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('miliseconds')
title('Average Data Packet Delay (milliseconds)')

figure(6)
bar(n,avgPLdata)
hold on
erro=errorbar(n,avgPLdata,termPLdata,termPLdata);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('%')
title('Average Data Packet Loss (%)')

figure(7)
bar(n,avgAPDvoip)
hold on
erro=errorbar(n,avgAPDvoip,termAPDvoip,termAPDvoip);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('miliseconds')
title('Average VoIP Packet Delay (milliseconds)')

figure(8)
bar(n,avgPLvoip)
hold on
erro=errorbar(n,avgPLvoip,termPLvoip,termPLvoip);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('n values')
ylabel('%')
title('Average VoIP Packet Loss (%)')


%2c
aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.19+0.23+0.17))/aux3;
ber=10^-5;
pld=zeros(1,aux3+3);
for i = 1:length(pld)
    if i == 1
        pld(i) = 0.19*(100*(1-(nchoosek((i+64-1)*8,0)*ber^(0)*(1-ber)^((i+64-1)*8-0))));
    elseif i == 110-64+1
        pld(i) = 0.23*(100*(1-(nchoosek((i+64-1)*8,0)*ber^(0)*(1-ber)^((i+64-1)*8-0))));
    elseif i == 1518-64+1
        pld(i) = 0.17*(100*(1-(nchoosek((i+64-1)*8,0)*ber^(0)*(1-ber)^((i+64-1)*8-0))));
    else
        pld(i) = aux4*(100*(1-(nchoosek((i+64-1)*8,0)*ber^(0)*(1-ber)^((i+64-1)*8-0))));
    end
end

aux2=[110:130];
aux5=length(aux2);
aux6=1/aux5;
plv=zeros(1,aux5);
for j = 1:length(plv)
    plv(j) = aux6*(100*(1-(nchoosek((j+110-1)*8,0)*ber^(0)*(1-ber)^((j+110-1)*8-0))));
end

apld = sum(pld);
aplv = sum(plv);

fprintf("Theoretical average packet loss of data packets with BER = 10^-5 -> %.3f %%\n", apld);
fprintf("Theoretical average packet loss of VoIP packets with BER = 10^-5 -> %.3f %%\n", aplv);