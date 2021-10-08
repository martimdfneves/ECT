%2a
R=10000;
n=10;
S=100;
W=0;
p=20;
lambda=[100,120,140,160,180,200];
fname='movies.txt';
%{
for i=1:length(lambda)
    for j=1:10
        [bHD(j) b4K(j)] = simulator2(lambda(i),p,n,S,W,R,fname);
    end
    alfa= 0.1; %90% confidence interval%
    media4k(i) = mean(b4K);
    term4k(i) = norminv(1-alfa/2)*sqrt(var(b4K)/10);
    mediahd(i) = mean(bHD);
    termhd(i) = norminv(1-alfa/2)*sqrt(var(bHD)/10);
end
figure(1)
bar(lambda,media4k)
hold on
errb=errorbar(lambda,media4k,term4k,term4k);
errb.Color=[0 0 0];
errb.LineStyle = 'none';
hold off
grid on
title('Blocking probability 4K(%)')
xlabel('lambda(requests/hour)')
ylim([0 100])

figure(2)
bar(lambda,mediahd)
hold on
erro=errorbar(lambda,mediahd,termhd,termhd);
erro.Color=[0 0 0];
erro.LineStyle = 'none';
hold off
grid on
title('Blocking probability HD(%)')
xlabel('lambda(requests/hour)')
ylim([0 100])
%}

%2b
n2=4;
S2=250;
n3=1;
S3=1000;
%{
for i=1:length(lambda)
    for j=1:10
        [bHD2(j) b4K2(j)] = simulator2(lambda(i),p,n2,S2,W,R,fname);
    end
    alfa= 0.1; %90% confidence interval%
    media4k2(i) = mean(b4K2);
    term4k2(i) = norminv(1-alfa/2)*sqrt(var(b4K2)/10);
    mediahd2(i) = mean(bHD2);
    termhd2(i) = norminv(1-alfa/2)*sqrt(var(bHD2)/10);
end
for i=1:length(lambda)
    for j=1:10
        [bHD3(j) b4K3(j)] = simulator2(lambda(i),p,n3,S3,W,R,fname);
    end
    alfa= 0.1; %90% confidence interval%
    media4k3(i) = mean(b4K3);
    term4k3(i) = norminv(1-alfa/2)*sqrt(var(b4K3)/10);
    mediahd3(i) = mean(bHD3);
    termhd3(i) = norminv(1-alfa/2)*sqrt(var(bHD3)/10);
end
figure(3)
bar(lambda,[media4k;media4k2;media4k3])
grid on
title('Blocking probability 4K(%)')
xlabel('lambda(requests/hour)')
legend('configuration 1', 'configuration 2', 'configuration 3', 'location', 'North')
ylim([0 100])

figure(4)
bar(lambda,[mediahd;mediahd2;mediahd3])
grid on
title('Blocking probability HD(%)')
xlabel('lambda(requests/hour)')
legend('configuration 1', 'configuration 2', 'configuration 3', 'location', 'North')
ylim([0 100])
%}

%2c
W=400;
%{
for i=1:length(lambda)
    for j=1:10
        [bHD(j) b4K(j)] = simulator2(lambda(i),p,n,S,W,R,fname);
        [bHD2(j) b4K2(j)] = simulator2(lambda(i),p,n2,S2,W,R,fname);
        [bHD3(j) b4K3(j)] = simulator2(lambda(i),p,n3,S3,W,R,fname);
    end
    alfa= 0.1; %90% confidence interval%
    media4k(i) = mean(b4K);
    term4k(i) = norminv(1-alfa/2)*sqrt(var(b4K)/10);
    mediahd(i) = mean(bHD);
    termhd(i) = norminv(1-alfa/2)*sqrt(var(bHD)/10);
    media4k2(i) = mean(b4K2);
    term4k2(i) = norminv(1-alfa/2)*sqrt(var(b4K2)/10);
    mediahd2(i) = mean(bHD2);
    termhd2(i) = norminv(1-alfa/2)*sqrt(var(bHD2)/10);
    media4k3(i) = mean(b4K3);
    term4k3(i) = norminv(1-alfa/2)*sqrt(var(b4K3)/10);
    mediahd3(i) = mean(bHD3);
    termhd3(i) = norminv(1-alfa/2)*sqrt(var(bHD3)/10);
end
figure(5)
bar(lambda,[media4k;media4k2;media4k3])
grid on
title('Blocking probability 4K(%)')
xlabel('lambda(requests/hour)')
legend('configuration 1', 'configuration 2', 'configuration 3', 'location', 'North')
ylim([0 100])

figure(6)
bar(lambda,[mediahd;mediahd2;mediahd3])
grid on
title('Blocking probability HD(%)')
xlabel('lambda(requests/hour)')
legend('configuration 1', 'configuration 2', 'configuration 3', 'location', 'North')
ylim([0 100])
%}

%2d
W1=600;
%{
for i=1:length(lambda)
    for j=1:10
        [bHD(j) b4K(j)] = simulator2(lambda(i),p,n,S,W1,R,fname);
        [bHD2(j) b4K2(j)] = simulator2(lambda(i),p,n2,S2,W1,R,fname);
        [bHD3(j) b4K3(j)] = simulator2(lambda(i),p,n3,S3,W1,R,fname);
    end
    alfa= 0.1; %90% confidence interval%
    media4k(i) = mean(b4K);
    term4k(i) = norminv(1-alfa/2)*sqrt(var(b4K)/10);
    mediahd(i) = mean(bHD);
    termhd(i) = norminv(1-alfa/2)*sqrt(var(bHD)/10);
    media4k2(i) = mean(b4K2);
    term4k2(i) = norminv(1-alfa/2)*sqrt(var(b4K2)/10);
    mediahd2(i) = mean(bHD2);
    termhd2(i) = norminv(1-alfa/2)*sqrt(var(bHD2)/10);
    media4k3(i) = mean(b4K3);
    term4k3(i) = norminv(1-alfa/2)*sqrt(var(b4K3)/10);
    mediahd3(i) = mean(bHD3);
    termhd3(i) = norminv(1-alfa/2)*sqrt(var(bHD3)/10);
end
figure(7)
bar(lambda,[media4k;media4k2;media4k3])
grid on
title('Blocking probability 4K(%)')
xlabel('lambda(requests/hour)')
legend('configuration 1', 'configuration 2', 'configuration 3', 'location', 'North')
ylim([0 100])

figure(8)
bar(lambda,[mediahd;mediahd2;mediahd3])
grid on
title('Blocking probability HD(%)')
xlabel('lambda(requests/hour)')
legend('configuration 1', 'configuration 2', 'configuration 3', 'location', 'North')
ylim([0 100])
%}

%2e
Re = 100000;
Se = 10000;
pe = 24;
lambdae = 100000/24;
ne = 6;
We = 36500;
for j=1:10
    [bHD(j) b4K(j)] = simulator2(lambdae,pe,ne,Se,We,Re,fname);
end
media4k = mean(b4K);
mediahd = mean(bHD);

fprintf('n = %d W = %d\n',ne,We)
fprintf('blocking probability 4K = %.4e\n',media4k)
fprintf('blocking probability HD = %.10e\n',mediahd)