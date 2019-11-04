%% (a)

% Espaço de amostragem S = {cada uma das 100 notas}
% Probabilidade de cada acontecimento elementar = 1/número de notas = 1/100

%% (b) 
 
% O espaço de amostragem da variável aleatória X, S_X, é o conjunto {5, 50, 100}
% que corresponde aos 3 diferentes valores das 100 notas existentes.

% A probabilidade de cada acontecimento elementar é P(V) = número de notas com
% esse valor V/ número total de notas.
% Pelo que P(5)   = 90 / 100 = 0.9; 
%          P(50)  = 9  / 100 = 0.09; 
%          P(100) = 1  / 100 = 0.01;


%% (c) 
%                | 0.9   , xi = 5
% P (X = xi)  =  | 0.09  , xi = 50
%                | 0.01  , xi = 100
%                | 0     , outros valores de xi

stem([5, 50, 100], [0.9, 0.09, 0.01])
ylabel('P(X=xi)')
xlabel('xi')
axis([0,100,0,1])