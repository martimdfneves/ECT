% Probabilidades teóricas
% (a) X < 3 
% P(0 <= X < 3) = 3 - 0 / 10 - 0
pTeoricaA = 3/10

% (b) X > 7
% P(7 <= X < 10) = 10 - 7 / 10 - 0
pTeoricaB = (10-7)/10     % = 3/10;

% (c) 1 < X < 6
% P(1 < X < 6) = 6 - 1 / 10 - 0
pTeoricaC = (6-1)/10     % = 1/2;

% Probabilidades simuladas
N = 1e4;
X = 0 + rand(1, N) * (10-0);     

% (a) X < 3 
successosA = sum(X < 3);
pSimA = successosA / N

% (b) X > 7
successosB = sum(X > 7);
pSimB = successosB / N

% (c) 1 < X < 6
successosC = sum(X > 1 & X < 6);
pSimC = successosC / N