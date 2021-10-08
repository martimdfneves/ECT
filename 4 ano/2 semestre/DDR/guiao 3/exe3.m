%3a
C=10;
f=10^7;
b=0;
P=10000;
N = 10; %number of simulations
lambda=[1500,1600,1700,1800,1900,2000];
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);
%{
for i=1:length(lambda)
    for j= 1:N
        [PL(j),APD(j),MPD(j),TT(j)]= simulator2(lambda(i),C,f,P,b);
    end
    alfa= 0.1; %90% confidence interval%
    mediapl(i) = mean(PL);
    termpl(i) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    mediaapd(i) = mean(APD);
    termapd(i) = norminv(1-alfa/2)*sqrt(var(APD)/N);
    mediampd(i) = mean(MPD);
    termmpd(i) = norminv(1-alfa/2)*sqrt(var(MPD)/N);
    mediatt(i) = mean(TT);
    termtt(i) = norminv(1-alfa/2)*sqrt(var(TT)/N);
end

figure(1)
bar(lambda,mediaapd)
hold on
erro=errorbar(lambda,mediaapd,termapd,termapd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('lambda values')
ylabel('miliseconds')
title('Average Packet Delay (milliseconds)')

figure(2)
bar(lambda,mediampd)
hold on
errb=errorbar(lambda,mediampd,termmpd,termmpd);
errb.Color=[0 0 0];
errb.LineStyle = 'none';
hold off
grid on
xlabel('lambda values')
ylabel('miliseconds')
title('Maximum Packet Delay (milliseconds)')

figure(3)
bar(lambda,mediatt)
hold on
erro=errorbar(lambda,mediatt,termtt,termtt);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('lambda values')
ylabel('Mbps')
ylim([0 11])
title('Transmited Throughput (Mbps)')
%}

%3b
N=40;
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);
%{
for i=1:length(lambda)
    for j= 1:N
        [PL(j),APD(j),MPD(j),TT(j)]= simulator2(lambda(i),C,f,P,b);
    end
    alfa= 0.1; %90% confidence interval%
    mediaplb(i) = mean(PL);
    termplb(i) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    mediaapdb(i) = mean(APD);
    termapdb(i) = norminv(1-alfa/2)*sqrt(var(APD)/N);
    mediampdb(i) = mean(MPD);
    termmpdb(i) = norminv(1-alfa/2)*sqrt(var(MPD)/N);
    mediattb(i) = mean(TT);
    termttb(i) = norminv(1-alfa/2)*sqrt(var(TT)/N);
end

figure(4)
subplot(1,2,1)
bar(lambda,mediaapd)
hold on
erro=errorbar(lambda,mediaapd,termapd,termapd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 150])
xlabel('lambda values')
ylabel('miliseconds')
title('10 corridas')
subplot(1,2,2)
bar(lambda,mediaapdb)
hold on
erro=errorbar(lambda,mediaapdb,termapdb,termapdb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 150])
xlabel('lambda values')
ylabel('miliseconds')
title('40 corridas')
sgtitle('Average Packet Delay (milliseconds)')

figure(5)
subplot(1,2,1)
bar(lambda,mediampd)
hold on
erro=errorbar(lambda,mediampd,termmpd,termmpd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 300])
xlabel('lambda values')
ylabel('milliseconds')
title('10 corridas')
subplot(1,2,2)
bar(lambda,mediampdb)
hold on
erro=errorbar(lambda,mediampdb,termmpdb,termmpdb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 300])
xlabel('lambda values')
ylabel('milliseconds')
title('40 corridas')
sgtitle('Maximum Packet Delay (milliseconds)')

figure(6)
subplot(1,2,1)
bar(lambda,mediatt)
hold on
erro=errorbar(lambda,mediatt,termtt,termtt);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('lambda values')
ylabel('Mbps')
ylim([0 11])
title('10 corridas')
subplot(1,2,2)
bar(lambda,mediattb)
hold on
erro=errorbar(lambda,mediattb,termttb,termttb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 11])
xlabel('lambda values')
ylabel('Mbps')
title('40 corridas')
sgtitle('Transmited Throughput (Mbps)')
%}

%3c
%{
%M/M/1
aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.16+0.25+0.2))/aux3;
B=(0.16*64+0.25*110+0.2*1518+aux4*sum(aux1))*8;
miu=C*1000000/B;
for j=1:length(lambda)
    packet_delay(j) = 1/(miu-lambda(j))*1000;
    throughput(j) = (lambda(j)*B)/1000000;
end

%M/G/1
aux1=[64:1518];
aux3=length(aux1)-3;
aux4=(1-(0.16+0.25+0.2))/aux3;
APD=zeros(1,length(lambda));
TT=zeros(1,length(lambda));
for i=1:length(aux1)
    Pi(i)=(1-b)^(8*aux1(i));
    if aux1(i)==64
        pn(i)=0.16;
    elseif aux1(i)==110
        pn(i)=0.25;
    elseif aux1(i)==1518
        pn(i)=0.2;
    else
        pn(i)=aux4;
    end
end
for j=1:length(lambda)
    ES(j)=0;
    ES2(j)=0;
    TT(j)=0;
    WQ(j)=0;
    Wi(j)=0;
    APD(j)=0;
    for i=1:length(aux1)
        ES(j)=ES(j)+pn(i)*(8*aux1(i)/(C*1e6));
        ES2(j)=ES2(j)+pn(i)*((8*aux1(i)/(C*1e6))^2);
        TT(j)=TT(j)+(pn(i)*Pi(i)*lambda(j)*(8*aux1(i)));
    end
    WQ(j)=lambda(j)*ES2(j)/(2*(1-lambda(j)*ES(j)));
    num(j)=0;
    den(j)=0;
    for i=1:length(aux1)
        Wi(j)=WQ(j)+(8*aux1(i)/(C*1e6));
        num(j)=num(j)+(pn(i)*Pi(i)*Wi(j));
        den(j)=den(j)+(pn(i)*Pi(i));
    end
    APD(j)=num(j)/den(j);
end

figure(24)
bar(lambda,[mediaapdb;packet_delay;APD.*1000])
grid on
title('Average Packet Delay (milliseconds)')
legend('valor experimental','M/M/1','M/G/1')
xlabel('lambda values')
ylabel('milliseconds')

figure(25)
bar(lambda,[mediattb;throughput;TT/1000000])
grid on
title('Transmited Throughput (Mbps)')
legend('valor experimental','M/M/1','M/G/1')
xlabel('lambda values')
ylabel('Mbps')
%}

%3d
lambdad=1800;
fd=[2500,5000,7500,10000,12500,15000,17500,20000];
N=40;
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);
%{
for i=1:length(fd)
    for j= 1:N
        [PL(j),APD(j),MPD(j),TT(j)]= simulator2(lambdad,C,fd(i),P,b);
    end
    alfa= 0.1; %90% confidence interval%
    mediapld(i) = mean(PL);
    termpld(i) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    mediaapdd(i) = mean(APD);
    termapdd(i) = norminv(1-alfa/2)*sqrt(var(APD)/N);
    mediampdd(i) = mean(MPD);
    termmpdd(i) = norminv(1-alfa/2)*sqrt(var(MPD)/N);
    mediattd(i) = mean(TT);
    termttd(i) = norminv(1-alfa/2)*sqrt(var(TT)/N);
end

figure(7)
bar(fd,mediapld)
hold on
erro=errorbar(fd,mediapld,termpld,termpld);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('queue size')
ylabel('%')
title('Packet Loss (%)')

figure(8)
bar(fd,mediaapdd)
hold on
erro=errorbar(fd,mediaapdd,termapdd,termapdd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('queue size')
ylabel('milliseconds')
title('Average Packet Delay (milliseconds)')

figure(9)
bar(fd,mediampdd)
hold on
errb=errorbar(fd,mediampdd,termmpdd,termmpdd);
errb.Color=[0 0 0];
errb.LineStyle = 'none';
hold off
grid on
xlabel('queue size')
ylabel('milliseconds')
title('Maximum Packet Delay (milliseconds)')

figure(10)
bar(fd,mediattd)
hold on
erro=errorbar(fd,mediattd,termttd,termttd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
xlabel('queue size')
ylabel('Mbps')
title('Transmited Throughput (Mbps)')
%}

%3e
%{
aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.16+0.25+0.2))/aux3;
B=(0.16*64+0.25*110+0.2*1518+aux4*sum(aux1))*8;
miu=C*1000000/B;
for i=1:length(fd)
    m(i)=round(fd(i)*8/B)+1;
    num(i)=0;
    den(i)=0;
    for j=0:m(i)
        num(i)=num(i)+(j*(lambdad/miu)^j);
        den(i)=den(i)+(lambdad/miu)^j;
    end
    packet_loss2(i)=((lambdad/miu)^m(i))/den(i);
    L(i)=num(i)/den(i);
    packet_delay2(i)=L(i)/(lambdad*(1-packet_loss2(i)));
    throughput2(i) = ((lambdad*B)/1000000)*(1-packet_loss2(i));
end

figure(19)
bar(fd,[mediapld;packet_loss2*100])
grid on
title('Packet Loss (%)')
legend('valor experimental','M/M/1/m')
xlabel('queue size')
ylabel('%')

figure(20)
bar(fd,[mediaapdd;packet_delay2*1000])
grid on
title('Average Packet Delay (milliseconds)')
legend('valor experimental','M/M/1/m')
xlabel('queue size')
ylabel('milliseconds')

figure(21)
bar(fd,[mediattd;throughput2])
grid on
title('Transmited Throughput (Mbps)')
legend('valor experimental','M/M/1/m')
xlabel('queue size')
ylabel('Mbps')
%}

%3f
bf=10^-5;
N=40;
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);
%{
for i=1:length(fd)
    for j= 1:N
        [PL(j),APD(j),MPD(j),TT(j)]= simulator2(lambdad,C,fd(i),P,bf);
    end
    alfa= 0.1; %90% confidence interval%
    mediaplf(i) = mean(PL);
    termplf(i) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    mediaapdf(i) = mean(APD);
    termapdf(i) = norminv(1-alfa/2)*sqrt(var(APD)/N);
    mediampdf(i) = mean(MPD);
    termmpdf(i) = norminv(1-alfa/2)*sqrt(var(MPD)/N);
    mediattf(i) = mean(TT);
    termttf(i) = norminv(1-alfa/2)*sqrt(var(TT)/N);
end

figure(11)
subplot(1,2,1)
bar(fd,mediapld)
hold on
erro=errorbar(fd,mediapld,termpld,termpld);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 15])
title('b=0')
xlabel('queue size')
ylabel('%')
subplot(1,2,2)
bar(fd,mediaplf)
hold on
erro=errorbar(fd,mediaplf,termplf,termplf);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 15])
title('b=10e-5')
xlabel('queue size')
ylabel('%')
sgtitle('Packet Loss (%)')

figure(12)
subplot(1,2,1)
bar(fd,mediaapdd)
hold on
erro=errorbar(fd,mediaapdd,termapdd,termapdd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 7])
title('b=0')
xlabel('queue size')
ylabel('milliseconds')
subplot(1,2,2)
bar(fd,mediaapdf)
hold on
erro=errorbar(fd,mediaapdf,termapdf,termapdf);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 7])
title('b=10e-5')
xlabel('queue size')
ylabel('milliseconds')
sgtitle('Average Packet Delay (milliseconds)')

figure(13)
subplot(1,2,1)
bar(fd,mediampdd)
hold on
erro=errorbar(fd,mediampdd,termmpdd,termmpdd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 20])
title('b=0')
xlabel('queue size')
ylabel('milliseconds')
subplot(1,2,2)
bar(fd,mediampdf)
hold on
erro=errorbar(fd,mediampdf,termmpdf,termmpdf);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 20])
title('b=10e-5')
xlabel('queue size')
ylabel('milliseconds')
sgtitle('Maximum Packet Delay (milliseconds)')

figure(14)
subplot(1,2,1)
bar(fd,mediattd)
hold on
erro=errorbar(fd,mediattd,termttd,termttd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
title('b=0')
xlabel('queue size')
ylabel('Mbps')
ylim([0 10])
subplot(1,2,2)
bar(fd,mediattf)
hold on
erro=errorbar(fd,mediattf,termttf,termttf);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 10])
title('b=10e-5')
xlabel('queue size')
ylabel('Mbps')
sgtitle('Transmited Throughput (Mbps)')
%}

%3g
N=40;
PL=zeros(1,N);
APD=zeros(1,N);
MPD=zeros(1,N);
TT=zeros(1,N);

for i=1:length(lambda)
    for j= 1:N
        [PL(j),APD(j),MPD(j),TT(j)]= simulator2(lambda(i),C,f,P,bf);
    end
    alfa= 0.1; %90% confidence interval%
    mediaplg(i) = mean(PL);
    termplg(i) = norminv(1-alfa/2)*sqrt(var(PL)/N);
    mediaapdg(i) = mean(APD);
    termapdg(i) = norminv(1-alfa/2)*sqrt(var(APD)/N);
    mediampdg(i) = mean(MPD);
    termmpdg(i) = norminv(1-alfa/2)*sqrt(var(MPD)/N);
    mediattg(i) = mean(TT);
    termttg(i) = norminv(1-alfa/2)*sqrt(var(TT)/N);
end
%{
figure(15)
subplot(1,2,1)
bar(lambda,mediaplb)
hold on
erro=errorbar(lambda,mediaplb,termplb,termplb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 7])
title('b=0')
xlabel('lambda values')
ylabel('%')
subplot(1,2,2)
bar(lambda,mediaplg)
hold on
erro=errorbar(lambda,mediaplg,termplg,termplg);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 7])
title('b=10e-5')
xlabel('lambda values')
ylabel('%')
sgtitle('Packet Loss (%)')

figure(16)
subplot(1,2,1)
bar(lambda,mediaapdb)
hold on
erro=errorbar(lambda,mediaapdb,termapdb,termapdb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 140])
title('b=0')
xlabel('lambda values')
ylabel('milliseconds')
subplot(1,2,2)
bar(lambda,mediaapdg)
hold on
erro=errorbar(lambda,mediaapdg,termapdg,termapdg);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 140])
title('b=10e-5')
xlabel('lambda values')
ylabel('milliseconds')
sgtitle('Average Packet Delay (milliseconds)')

figure(17)
subplot(1,2,1)
bar(lambda,mediampdb)
hold on
erro=errorbar(lambda,mediampdb,termmpdb,termmpdb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 300])
title('b=0')
xlabel('lambda values')
ylabel('milliseconds')
subplot(1,2,2)
bar(lambda,mediampdg)
hold on
erro=errorbar(lambda,mediampdg,termmpdg,termmpdg);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 300])
title('b=10e-5')
xlabel('lambda values')
ylabel('milliseconds')
sgtitle('Maximum Packet Delay (milliseconds)')

figure(18)
subplot(1,2,1)
bar(lambda,mediattb)
hold on
erro=errorbar(lambda,mediattb,termttb,termttb);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
title('b=0')
xlabel('lambda values')
ylabel('Mbps')
ylim([0 15])
subplot(1,2,2)
bar(lambda,mediattg)
hold on
erro=errorbar(lambda,mediattg,termttg,termttg);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
ylim([0 15])
title('b=10e-5')
xlabel('lambda values')
ylabel('Mbps')
sgtitle('Transmited Throughput (Mbps)')
%}

%3h
aux1=[64:1518];
aux3=length(aux1)-3;
aux4=(1-(0.16+0.25+0.2))/aux3;
APD=zeros(1,length(lambda));
TT=zeros(1,length(lambda));

for i=1:length(aux1)
    Pi(i)=(1-bf)^(8*aux1(i));
    if aux1(i)==64
        pn(i)=0.16;
    elseif aux1(i)==110
        pn(i)=0.25;
    elseif aux1(i)==1518
        pn(i)=0.2;
    else
        pn(i)=aux4;
    end
end
for j=1:length(lambda)
    ES(j)=0;
    ES2(j)=0;
    TT(j)=0;
    WQ(j)=0;
    Wi(j)=0;
    APD(j)=0;
    TT(j)=0;
    for i=1:length(aux1)
        ES(j)=ES(j)+pn(i)*(8*aux1(i)/(C*1e6));
        ES2(j)=ES2(j)+pn(i)*((8*aux1(i)/(C*1e6))^2);
        TT(j)=TT(j)+(pn(i)*Pi(i)*lambda(j)*(8*aux1(i)));
    end
    WQ(j)=lambda(j)*ES2(j)/(2*(1-lambda(j)*ES(j)));
    num(j)=0;
    den(j)=0;
    for i=1:length(aux1)
        Wi(j)=WQ(j)+(8*aux1(i)/(C*1e6));
        num(j)=num(j)+(pn(i)*Pi(i)*Wi(j));
        den(j)=den(j)+(pn(i)*Pi(i));
    end
    APD(j)=num(j)/den(j);
end

figure(22)
bar(lambda,[mediaapdg;APD.*1000])
grid on
title('Average Packet Delay (milliseconds)')
legend('valor experimental','M/G/1')
xlabel('lambda values')
ylabel('milliseconds')

figure(23)
bar(lambda,[mediattg;TT./1000000])
grid on
title('Transmited Throughput (Mbps)')
legend('valor experimental','M/G/1')
xlabel('lambda values')
ylabel('milliseconds')
%}