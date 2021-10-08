%1a
R=500;
lambda=20;
C=100;
M=4;
fname='movies.txt';
N = 10; %number of simulations
results=zeros(1,N);
for it= 1:N
    [b(it) o(it)]= simulator1(lambda,C,M,R,fname);
end
alfa= 0.1; %90% confidence interval%
mediab = mean(b);
termb = norminv(1-alfa/2)*sqrt(var(b)/N);
mediao = mean(o);
termo = norminv(1-alfa/2)*sqrt(var(o)/N);
%fprintf('blocking probability = %.2e +- %.2e\n',mediab,termb)
%fprintf('average occupation (Mbps) = %.2e +- %.2e\n',mediao,termo)

%1b
R=500;
C=100;
M=4;
fname='movies.txt';
N = 10; %number of simulations
lambda1=[10,15,20,25,30,35,40];
for i=1:length(lambda1)
    for it= 1:N
        [b(it) o(it)]= simulator1(lambda1(i),C,M,R,fname);
    end
    alfa= 0.1; %90% confidence interval%
    mediab1(i) = mean(b);
    termb1(i) = norminv(1-alfa/2)*sqrt(var(b)/N);
    mediao1(i) = mean(o);
    termo1(i) = norminv(1-alfa/2)*sqrt(var(o)/N);
end
%figure(1)
%bar(lambda1,mediab1)
%hold on
%errb=errorbar(lambda1,mediab1,termb1,termb1);
%errb.Color=[0 0 0];
%errb.LineStyle = 'none';
%hold off
%grid on
%title('Blocking probability (%)')
%xlabel('lambda(requests/hour)')

%figure(2)
%bar(lambda1,mediao1)
%hold on
%erro=errorbar(lambda1,mediao1,termo1,termo1);
%erro.Color=[0 0 0];
%erro.LineStyle = 'none';
%hold off
%grid on
%title('Average server occupation (Mbps)')
%xlabel('lambda(requests/hour)')

%1c
R1=5000;
C=100;
M=4;
fname='movies.txt';
N = 10; %number of simulations
lambda1=[10,15,20,25,30,35,40];
for i=1:length(lambda1)
    for it= 1:N
        [b(it) o(it)]= simulator1(lambda1(i),C,M,R1,fname);
    end
    alfa= 0.1; %90% confidence interval%
    mediabc(i) = mean(b);
    termbc(i) = norminv(1-alfa/2)*sqrt(var(b)/N);
    mediaoc(i) = mean(o);
    termoc(i) = norminv(1-alfa/2)*sqrt(var(o)/N);
end
%figure(3)
%bar(lambda1,mediabc)
%hold on
%errb=errorbar(lambda1,mediabc,termbc,termbc);
%errb.Color=[0 0 0];
%errb.LineStyle = 'none';
%hold off
%grid on
%title('Blocking probability (%)')
%xlabel('lambda(requests/hour)')

%figure(4)
%bar(lambda1,mediaoc)
%hold on
%erro=errorbar(lambda1,mediaoc,termoc,termoc);
%erro.Color=[0 0 0];
%erro.LineStyle = 'none';
%hold off
%grid on
%title('Average server occupation (Mbps)')
%xlabel('lambda(requests/hour)')

%1d
R1=5000;
C1=1000;
M=4;
fname='movies.txt';
N = 10; %number of simulations
lambda2=[100,150,200,250,300,350,400];
for i=1:length(lambda1)
    for it= 1:N
        [b(it) o(it)]= simulator1(lambda2(i),C1,M,R1,fname);
    end
    alfa= 0.1; %90% confidence interval%
    mediabd(i) = mean(b);
    termbd(i) = norminv(1-alfa/2)*sqrt(var(b)/N);
    mediaod(i) = mean(o);
    termod(i) = norminv(1-alfa/2)*sqrt(var(o)/N);
end
%figure(5)
%bar(lambda2,mediabd)
%hold on
%errb=errorbar(lambda2,mediabd,termbd,termbd);
%errb.Color=[0 0 0];
%errb.LineStyle = 'none';
%hold off
%grid on
%title('Blocking probability (%)')
%xlabel('lambda(requests/hour)')

%figure(6)
%bar(lambda2,mediaod)
%hold on
%erro=errorbar(lambda2,mediaod,termod,termod);
%erro.Color=[0 0 0];
%erro.LineStyle = 'none';
%hold off
%grid on
%title('Average server occupation (Mbps)')
%xlabel('lambda(requests/hour)')

%1e
lambda1=[10,15,20,25,30,35,40];
miu=1/86.3;
ro=lambda1/60/miu;
M=25;
occ1=zeros(1,7);
for j=1:length(ro)
    %blocking probbility
    a1= 1;
    p1= 1;
    for m= M:-1:1
        a1= a1*m/ro(j);
        p1= p1 + a1;
    end
    p2(j)= (1/p1)*100;
    %average system occupation
    a2= M;
    numerator= a2;
    for i= M-1:-1:1
        a2= a2*i/ro(j);
        numerator= numerator + a2;
    end
    a2= 1;
    denominator= a2;
    for i= M:-1:1
        a2= a2*i/ro(j);
        denominator= denominator + a2;
    end
    occ1(j)= numerator/denominator
end
figure(7)
bar(lambda1,[p2;mediabc])
figure(8)
bar(lambda1,[occ1;mediaoc])
