% (a) 

N = 1e4;                  % número de experiências
n = 5;                    % número de experiências de Bernoulli
prob = 0.3;                  
experiencia = rand(n, N);  % each column has 5 lines, each one with a faulty (< 0.3) or good parts (> 0.3)
numPecas  = sum(experiencia < prob);

probSim = histc(numPecas, 0:n) / N;

subplot(1,2,1)
stem(0:n, probSim, 'r')
axis([-0.5 5.5 0 0.45])
ylabel('Probabilidade')
xlabel('Número de peças defeituosas em 5 peças')
title('Probabilidade simulada da distribuição da variável aleatória X');

% Probabilidade Teórica 
probTeo = 0:5;
probTeo = factorial(n)./(factorial(probTeo).*factorial(n-probTeo)).*prob.^(probTeo).*(1-prob).^(n-probTeo);

subplot(1,2,2);
stem(0:n, probTeo, 'b')
axis([-0.5 5.5 0 0.45])
ylabel('Probabilidade')
xlabel('Número de peças defeituosas em 5 peças')
title('Probabilidade teórica da distribuição da variável aleatória X');
probSim
probTeo

%% (b)

%Teórica
% P(X <= 2) = Sum((5 k) * 0.3^k * (0.7)^5-k), = 0.8369 para k = 0 -> 2
% n = 5; k = 0, 1, 2; p = 0.3
probT = 0;
for k = 0 : 2 
    probT = probT + factorial(5)/(factorial(5-k)*factorial(k))*0.3^k*(1-0.3)^(5-k);
end

% Por simulção
successos  = sum(experiencia < 0.3) <= 2;
probS      = sum(successos) / N;
probT
probS