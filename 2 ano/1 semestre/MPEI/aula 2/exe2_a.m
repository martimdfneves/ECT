N=1e7;
p=0.5;
k=10;
n=10;
lancamentos = rand (n,N) > p;
sucessos = sum(lancamentos) >= k;
prob=sum(sucessos)/N