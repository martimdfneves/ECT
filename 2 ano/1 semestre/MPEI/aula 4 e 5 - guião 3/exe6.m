% n = 8000 experi�ncias de Bernoulli
% p = 1 pe�a em 1000 = 1/1000

n = 8000;                 
p = 1/1000;
k = 7;
probBinomTeo = prod(n-k+1:n)/factorial(k) * p^k * (1-p)^(n-k);
 
% n = 8000
% p = 1 pe�a em 1000 = 1/1000

N = 1e3;                  % N�mero de experi�ncias
n = 8000;                 % number of Bernoulli experiences
p = 1/1000;
k = 7;
experiencia = rand(n, N);  

ChipsDefeituosos = sum(experiencia < p); 

numSuccessos = ChipsDefeituosos == k;
probBinomSimu = sum(numSuccessos) / N;
  
lambda = n*p; % lambda = np na distribui��o de Poisson

probPoisson = (lambda^k/factorial(k)) * exp(-lambda);

probBinomTeo
probBinomSimu
probPoisson