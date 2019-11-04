function [prob] = simprob(p,n,k,N)
  prob=0;
  lancamentos = rand(n,N) > p;
  sucessos= sum(lancamentos)==k;
  prob= sum(sucessos)/N
endfunction
