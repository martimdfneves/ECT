%3a)

lambda = 1500;
c=10;
f=10000;
P=100000;
N=20;
n=[10,20,30,40];
alfa=0.1;
PLdata = zeros(1,N);
APDdata = zeros(1,N);
PLvoip = zeros(1,N);
APDvoip = zeros(1,N);
%{
for i=1:length(n)
    for j=1:N
        [PLdata(j), APDdata(j), MPDdata(j), TT(j), PLvoip(j), APDvoip(j), MPDvoip(j)] = Simulator3(lambda,c,f,P,n(i));   
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
title('Average VoIP Packet Loss (%)');
%}

%3b)
%{
for i=1:length(n)
    for j=1:N
        [PLdata(j), APDdata(j), MPDdata(j), TT(j), PLvoip(j), APDvoip(j), MPDvoip(j)] = Simulator4(lambda,c,f,P,n(i));   
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
%}

%3c)

for i=1:length(n)
    for j=1:N
        [PLdata(j), APDdata(j), MPDdata(j), TT(j), PLvoip(j), APDvoip(j), MPDvoip(j)] = Simulator4c(lambda,c,f,P,n(i));   
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

figure(9)
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

figure(10)
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

figure(11)
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

figure(12)
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

%3d)
%{
for i=1:length(n)
    for j=1:N
        [PLdata(j), APDdata(j), MPDdata(j), TT(j), PLvoip(j), APDvoip(j), MPDvoip(j)] = Simulator4d(lambda,c,f,P,n(i));   
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

figure(13)
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

figure(14)
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

figure(15)
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

figure(16)
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
%}