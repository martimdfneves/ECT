N=1e5;
p=0.5;
k=2;
n=5;
lancamentos = rand (n,N) > p;
sucessos = sum(lancamentos) >= k;

k=1;
sucessos1 = sum(lancamentos) >= k;
prob=sucessos/sucessos1