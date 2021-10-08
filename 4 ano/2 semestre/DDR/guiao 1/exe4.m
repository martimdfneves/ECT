%4a
p1=1/(1+(8/600)+(8/600)*(5/200)+(8/600)*(5/200)*(2/50)+(8/600)*(5/200)*(2/50)*(1/5));                            
p2=(8/600)/(1+(8/600)+(8/600)*(5/200)+(8/600)*(5/200)*(2/50)+(8/600)*(5/200)*(2/50)*(1/5));                       
p3=(8/600)*(5/200)/(1+(8/600)+(8/600)*(5/200)+(8/600)*(5/200)*(2/50)+(8/600)*(5/200)*(2/50)*(1/5));              
p4=(8/600)*(5/200)*(2/50)/(1+(8/600)+(8/600)*(5/200)+(8/600)*(5/200)*(2/50)+(8/600)*(5/200)*(2/50)*(1/5));       
p5=(8/600)*(5/200)*(2/50)*(1/5)/(1+(8/600)+(8/600)*(5/200)+(8/600)*(5/200)*(2/50)+(8/600)*(5/200)*(2/50)*(1/5)); 
pi=p4+p5;
pn=p1+p2+p3;
fprintf('A probabilidade de estar no estado normal é %.5e\n',pn)
fprintf('A probabilidade de estar no estado de interferência é %.5e\n',pi)

%4b
beri=((p4*10^-3)+(p5*10^-2))/(pi);
bern=((p1*10^-6)+(p2*10^-5)+(p3*10^-4))/(pn);
fprintf('O ber médio no estado normal é %.5e\n',bern)
fprintf('O ber médio no estado de interferência é %.5e\n',beri)

%4c
n1=linspace(64*8, 200*8, 100);                                        %gerar vários valores para o tamanho dos pacotes
p1c=1-(1*(10^-6)^0*(1-(10^-6)).^n1);                                  %probabilidade de ser recebido com erros no estado 1
p2c=1-(1*(10^-5)^0*(1-(10^-5)).^n1);                                  %probabilidade de ser recebido com erros no estado 2
p3c=1-(1*(10^-4)^0*(1-(10^-4)).^n1);                                  %probabilidade de ser recebido com erros no estado 3
p4c=1-(1*(10^-3)^0*(1-(10^-3)).^n1);                                  %probabilidade de ser recebido com erros no estado 4
p5c=1-(1*(10^-2)^0*(1-(10^-2)).^n1);                                  %probabilidade de ser recebido com erros no estado 5
pErr1=(p1c.*p1)./((p1c.*p1)+(p2c.*p2)+(p3c.*p3)+(p4c.*p4)+(p5c.*p5)); %Regra de Bayes: calcular probabiliadde de estar no estado normal sabendo que foi recebido com erros (estado 1) 
pErr2=(p2c.*p2)./((p1c.*p1)+(p2c.*p2)+(p3c.*p3)+(p4c.*p4)+(p5c.*p5)); %Regra de Bayes: calcular probabiliadde de estar no estado normal sabendo que foi recebido com erros (estado 2)
pErr3=(p3c.*p3)./((p1c.*p1)+(p2c.*p2)+(p3c.*p3)+(p4c.*p4)+(p5c.*p5)); %Regra de Bayes: calcular probabiliadde de estar no estado normal sabendo que foi recebido com erros (estado 3)
prob1=pErr1+pErr2+pErr3;                                              %Soma das 3 probabilidades calculadas anteriormente pois os estados 1, 2 e 3 formam o estado normal
figure(1)
plot(n1/8,prob1,'k.')
title('P of normal state knowing it had errors')
xlabel('Packet size (Bytes)')
ylabel('Probabilty')
grid on

%4d
p1d=(1*(10^-6)^0*(1-(10^-6)).^n1);
p2d=(1*(10^-5)^0*(1-(10^-5)).^n1);
p3d=(1*(10^-4)^0*(1-(10^-4)).^n1);
p4d=(1*(10^-3)^0*(1-(10^-3)).^n1);
p5d=(1*(10^-2)^0*(1-(10^-2)).^n1);
pErr4=(p4d.*p4)./((p1d.*p1)+(p2d.*p2)+(p3d.*p3)+(p4d.*p4)+(p5d.*p5));
pErr5=(p5d.*p5)./((p1d.*p1)+(p2d.*p2)+(p3d.*p3)+(p4d.*p4)+(p5d.*p5));
prob2=pErr4+pErr5;
figure(2)
plot(n1/8,prob2,'r--')
title('P of interference state knowing it had no errors')
xlabel('Packet size (Bytes)')
ylabel('Probabilty')
grid on