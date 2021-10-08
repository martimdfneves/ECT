R = 100000;
S = 1000;
p = 30;
lambda = ((5000*10)+(2500*25))/24;
n = 77;
W = 0;
fname='movies.txt';
for j=1:10
    [bHD(j) b4K(j)] = simulator2(lambda,p,n,S,W,R,fname);
end
alfa= 0.1; %90% confidence interval%
media4k = mean(b4K);
term4k = norminv(1-alfa/2)*sqrt(var(b4K)/10);
mediahd = mean(bHD);
termhd = norminv(1-alfa/2)*sqrt(var(bHD)/10);

fprintf('n = %d W = %d\n',n,W)
fprintf('blocking probability 4K = %.4e\n',media4k+term4k)
fprintf('blocking probability HD = %.10e\n',mediahd+termhd)