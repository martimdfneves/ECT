%1a
p1 = 0.6;
n1 = 4;
res1 = 1*p1 + 1/n1 * (1-p1);

%1b
p2 = 0.7;
n2 = 5;
res2 = 1*p2 / (p2+(1-p2)/n2);

%1c
p3 = linspace(0, 1, 100);  
f_1 = 1*p3 + (1/3) * (1-p3);
f_2 = 1*p3 + (1/4) * (1-p3);
f_3 = 1*p3 + (1/5) * (1-p3);

figure(1)
plot(p3 * 100, f_1 * 100, 'b-', p3 * 100, f_2 * 100, 'r--', p3 * 100, f_3 * 100, 'g:')
title("Probability of right answer (%)")
xlabel("p(%)")
legend('n=3', 'n=4', 'n=5', 'location', 'NorthWest')
axis([0 100 0 100])
grid on

%1d
p4 = linspace(0, 1, 100);  
f_4 = 1*p4 ./ (p4+(1-p4)/3);
f_5 = 1*p4 ./ (p4+(1-p4)/4);
f_6 = 1*p4 ./ (p4+(1-p4)/5);

figure(2)
plot(p4 * 100, f_4 * 100, 'b-', p4 * 100, f_5 * 100, 'r--', p4 * 100, f_6 * 100, 'g:')
title("Probability of right answer (%)")
xlabel("p(%)")
legend('n=3', 'n=4', 'n=5', 'location', 'NorthWest')
axis([0 100 0 100])
grid on