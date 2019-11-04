%% (a) 

% X ~ Poisson(15)
% P(X = 0) = (15^0)*e^(-15)/0! = e^(-15)

probNenhumaMensagem = exp(-15)

%% (b) 

% X ~ Poisson(15)
% P(X > 10) = 1 - P (X <= 10) = 1 - SUM[(15^i)*e^(-15)/i!] para i = 0:10

i = 0:10;
p = (15.^i).*exp(-15)./factorial(i);
probMin10Mensagens = 1 - sum(p)