N=1e7;
p=0.5;
k=11;
n=11;
lancamentos = rand (n,N) > p;
sucessos = sum(lancamentos) == k;
sucessos1=sum(lancamentos(1:10,:))==10;
prob=sucessos/sucessos1