N=1e5;
p=0.5;
k=2;
n=2;
lancamentos = rand (n,N) > p;
sucessos = sum(lancamentos) >= k;

k=1;
n=2;
lancamentos = rand (n,N) > p;
sucessos1 = sum(lancamentos) >= k;

prob=sum(sucessos)/sum(sucessos1)