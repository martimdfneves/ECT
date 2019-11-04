N=1e5;
p=0.5;
k=1;
n=2;
lancamentos = rand (n,N) > p;
n1=sum(lancamentos(1,:)==1 & lancamentos(2,:)==1);
n2=sum(lancamentos(1,:)==1);
prob=n1/n2

