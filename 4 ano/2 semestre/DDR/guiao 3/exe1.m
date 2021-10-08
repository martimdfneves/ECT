%1a
P=10000;
lambda=1800;
C=10;
f=1000000;
%{
for j=1:10
    [PL(j),APD(j),MPD(j),TT(j)] = simulator1(lambda,C,f,P);
end
alfa= 0.1; %90% confidence interval%
mediapl = mean(PL);
termpl = norminv(1-alfa/2)*sqrt(var(PL)/10);
mediaapd = mean(APD);
termapd = norminv(1-alfa/2)*sqrt(var(APD)/10);
mediampd = mean(MPD);
termmpd = norminv(1-alfa/2)*sqrt(var(MPD)/10);
mediatt = mean(TT);
termtt = norminv(1-alfa/2)*sqrt(var(TT)/10);
fprintf('Packet Loss (%%) = %.4e +- %.4e\n',mediapl,termpl)
fprintf('Av. Packet delay (ms) = %.4e +- %.4e\n',mediaapd,termapd)
fprintf('Max. Packet Delay (ms) = %.4e +- %.4e\n',mediampd,termmpd)
fprintf('Throughput (Mbps) = %.4e +- %.4e\n',mediatt,termtt)
%}

%1b
fb=10000;
%{
for j=1:10
    [PL(j),APD(j),MPD(j),TT(j)] = simulator1(lambda,C,fb,P);
end
alfa= 0.1; %90% confidence interval%
mediapl = mean(PL);
termpl = norminv(1-alfa/2)*sqrt(var(PL)/10);
mediaapd = mean(APD);
termapd = norminv(1-alfa/2)*sqrt(var(APD)/10);
mediampd = mean(MPD);
termmpd = norminv(1-alfa/2)*sqrt(var(MPD)/10);
mediatt = mean(TT);
termtt = norminv(1-alfa/2)*sqrt(var(TT)/10);
fprintf('Packet Loss (%%) = %.4e +- %.4e\n',mediapl,termpl)
fprintf('Av. Packet delay (ms) = %.4e +- %.4e\n',mediaapd,termapd)
fprintf('Max. Packet Delay (ms) = %.4e +- %.4e\n',mediampd,termmpd)
fprintf('Throughput (Mbps) = %.4e +- %.4e\n',mediatt,termtt)
%}

%1c         M/M/1
%{
packet_loss=0; %fila infinita logo nunca se perdem
aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.16+0.25+0.2))/aux3;
B=(0.16*64+0.25*110+0.2*1518+aux4*sum(aux1))*8;
miu=C*1000000/B;
packet_delay=1/(miu-lambda)*1000;
throughput = (lambda*B)/1000000;
fprintf('Packet Loss (%%) = %.4e\n',packet_loss)
fprintf('Av. Packet delay (ms) = %.4e\n',packet_delay)
fprintf('Throughput (Mbps) = %.4e\n',throughput)
%}

%1d         M/G/1
%{
packet_loss1=0; %fila infinita logo nunca se perdem
aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.16+0.25+0.2))/aux3;
S64=64*8/(C*1000000);
S110=110*8/(C*1000000);
S1518=1518*8/(C*1000000);
S1=aux1*8/(C*1000000);
S2=S1.^2;
ES=0.16*S64+0.25*S110+0.2*S1518+aux4*sum(S1);
ES2=0.16*((S64)^2)+0.25*((S110)^2)+0.2*((S1518)^2)+aux4*sum(S2);
WQ=(lambda*ES2)/(2*(1-lambda*ES));
packet_delay1=WQ+ES;
throughput1 = (lambda*B)/1000000;
fprintf('Packet Loss (%%) = %.4e\n',packet_loss1)
fprintf('Av. Packet delay (ms) = %.4e\n',packet_delay1*1000)
fprintf('Throughput (Mbps) = %.4e\n',throughput1)
%}

%1e         M/M/1/m
aux1=[65:109 111:1517];
aux3=length(aux1);
aux4=(1-(0.16+0.25+0.2))/aux3;
B=(0.16*64+0.25*110+0.2*1518+aux4*sum(aux1))*8;
miu=C*1000000/B;
m=round(fb*8/B)+1;
num=0;
den=0;
for j=0:m
    num=num+(j*(lambda/miu)^j);
    den=den+(lambda/miu)^j;
end
packet_loss2=((lambda/miu)^m)/den;
L=num/den;
packet_delay2=L/(lambda*(1-packet_loss2));
throughput2 = ((lambda*B)/1000000)*(1-packet_loss2);
fprintf('Packet Loss (%%) = %.4e\n',packet_loss2*100)
fprintf('Av. Packet delay (ms) = %.4e\n',packet_delay2*1000)
fprintf('Throughput (Mbps) = %.4e\n',throughput2)