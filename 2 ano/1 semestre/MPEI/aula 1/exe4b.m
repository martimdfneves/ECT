for n=0:20
  f(n+1)=n;
  q(n+1)=simprob(0.5,20,n,1e5);
endfor
stem(f,q,"--")