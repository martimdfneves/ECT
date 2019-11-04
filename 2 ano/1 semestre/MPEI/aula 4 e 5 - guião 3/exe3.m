%% (a)

N = 1e5; % Número de experiências
p = 0.5; % Probabilidade
n = 4;   % Número de lançamentos

experiencia = rand(n, N) > p;
numCoroas   = sum(experiencia);

prob=[sum(numCoroas==0)/N,sum(numCoroas==1)/N,sum(numCoroas==2)/N,sum(numCoroas==3)/N,sum(numCoroas==4)/N];
stem([0,1,2,3,4],prob)
axis([0,4.5,0,0.4]);
xlabel('X');
ylabel('P(x)');

%% (b)

np=[0:4];
VEsperado=sum(np.*prob)
variancia=sum([np.^2.*prob])-VEsperado^2
desvio=sqrt(variancia)

%% (c) 

% A varíavel X é uma variável aleatória discreta Binomial.
%P(X = k) = nchoosek(n, k)*(p)^(k)*(1-p)^(n-k)
%onde n são o número de experiências de Bernoulli independentes;
%k o número de sucessos dessas experiências;
%p a probabilidade de sucesso.

%% (d)

% P(X = x_i) = nchoosek(4, x_i)*((1/2)^4), se x_i pertence a {0, 1, 2, 3, 4}
% ( n = 4; p = 1/2; k pertence a {0, 1, 2, 3, 4} )

%               -> 0.0625  se x_i = 0
%               -> 0.2500  se x_i = 1
% p_X( x_i ) =  -> 0.3750  se x_i = 2
%               -> 0.2500  se x_i = 3
%               -> 0.0625  se x_i = 4
%               -> 0 para outros valores de x_i

%% (e)

%%% i

% P(X >= 2) = 1 - P(X < 2) = 1 - P(X = 1) - P(X = 0)
probMinDuasCoroas = 1;
for i=0:1
    probMinDuasCoroas = probMinDuasCoroas - nchoosek(4, i)*((1/2)^4);
end
probMinDuasCoroas

%%% ii

% P(X <= 1) = P(X = 0) + P(X = 1)
probUmaMenosCoroas = 0;
for i=0:1
    probUmaMenosCoroas = probUmaMenosCoroas + nchoosek(4, i)*((1/2)^4);
end
probUmaMenosCoroas

%%% iii

% P(1 <= X <= 3) = P(X = 1) + P(X = 2) + P(X = 3)
probUmaTresCoroas = 0;
for i=1:3
    probUmaTresCoroas = probUmaTresCoroas + nchoosek(4, i)*((1/2)^4);
end
probUmaTresCoroas