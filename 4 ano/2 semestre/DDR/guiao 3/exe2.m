%2a
P=10000;
lambda=1800;
C=10;
f=1000000;
b=10^-6;
%{
for j=1:10
    [PL(j),APD(j),MPD(j),TT(j)] = simulator2(lambda,C,f,P,b);
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

%2b
b1=10^-5;
%{
for j=1:10
    [PL(j),APD(j),MPD(j),TT(j)] = simulator2(lambda,C,f,P,b1);
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

%2c
%{
aux1=[64:1518];
aux3=length(aux1)-3;
aux4=(1-(0.16+0.25+0.2))/aux3;
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
PL=0;
ES=0;
ES2=0;
TT=0;
for i=1:length(aux1)
    PL=PL+(pn(i)*(1-Pi(i)));
    ES=ES+pn(i)*(8*aux1(i)/(C*1e6));
    ES2=ES2+pn(i)*((8*aux1(i)/(C*1e6))^2);
    TT=TT+(pn(i)*Pi(i)*lambda*(8*aux1(i)));
end
WQ=lambda*ES2/(2*(1-lambda*ES));
num=0;
den=0;
for i=1:length(aux1)
    Wi=WQ+(8*aux1(i)/(C*1e6));
    num=num+(pn(i)*Pi(i)*Wi);
    den=den+(pn(i)*Pi(i));
end
APD=num/den;
fprintf('Packet Loss (%%) = %.4e\n',PL*100)
fprintf('Av. Packet delay (ms) = %.4e\n',APD*1000)
fprintf('Throughput (Mbps) = %.4e\n',TT/1000000)
%}

%2d
aux1=[64:1518];
aux3=length(aux1)-3;
aux4=(1-(0.16+0.25+0.2))/aux3;
for i=1:length(aux1)
    Pi(i)=(1-b1)^(8*aux1(i));
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
PL=0;
ES=0;
ES2=0;
TT=0;
for i=1:length(aux1)
    PL=PL+(pn(i)*(1-Pi(i)));
    ES=ES+pn(i)*(8*aux1(i)/(C*1e6));
    ES2=ES2+pn(i)*((8*aux1(i)/(C*1e6))^2);
    TT=TT+(pn(i)*Pi(i)*lambda*(8*aux1(i)));
end
WQ=lambda*ES2/(2*(1-lambda*ES));
num=0;
den=0;
for i=1:length(aux1)
    Wi=WQ+(8*aux1(i)/(C*1e6));
    num=num+(pn(i)*Pi(i)*Wi);
    den=den+(pn(i)*Pi(i));
end
APD=num/den;
fprintf('Packet Loss (%%) = %.4e\n',PL*100)
fprintf('Av. Packet delay (ms) = %.4e\n',APD*1000)
fprintf('Throughput (Mbps) = %.4e\n',TT/1000000)