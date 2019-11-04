% Valores de x
x = [1:4];

% Probabilidade de cada x, usando f(x)
fx = (x+5)./30;

if (min(fx) >= 0 && max(fx) <= 1 && sum(fx) == 1)
    fprintf('A fun��o f(x) = (x + 5) / 30 pode representar a vari�vel aleat�ria com os valores 1, 2, 3 2 4. \n')
else 
    fprintf('A fun��o f(x) = (x + 5) / 30 n�o pode representar a vari�vel aleat�ria com os valores 1, 2, 3 e 4. \n')
end