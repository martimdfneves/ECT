%2a
n1 = 100 * 8;
p1 = 10^-2;
i1 = 0;
res1 = (nchoosek(n1, i1) * p1^i1 * (1-p1)^(n1-i1)) * 100;

%2b
n2 = 1000 * 8;
p2 = 10^-3;
i2 = 1;
res2 = (nchoosek(n2, i2) * p2^i2 * (1-p2)^(n2-i2)) * 100;

%2c
n3 = 200 * 8;
p3 = 10^-4;
res3 = (1 - (nchoosek(n3, 0) * p3^0 * (1-p3)^n3-0)) * 100;

%2d
p4 = logspace(-8, -2, 100);
f1 = (nchoosek(800, i1) * p4.^i1 .* (1-p4).^(800-i1)) * 100;
f2 = (nchoosek(1600, i1) * p4.^i1 .* (1-p4).^(1600-i1)) * 100;
f3 = (nchoosek(8000, i1) * p4.^i1 .* (1-p4).^(8000-i1)) * 100;
figure(1)
semilogx(p4, f1, 'b-', p4, f2, 'r--', p4, f3, 'g:')
title("Probability of packet reception without errors (%)")
xlabel("Bit Error Rate")
legend('100 Bytes', '200 Bytes', '1000 Bytes', 'location', 'SouthWest')
grid on

%2e
n5 = linspace(64*8, 1518*8, 100);
p = 10^-4;
f4 = 1 * p^0 * (1-p).^(n5-0);
p = 10^-3;
f5 = 1 * p^0 * (1-p).^(n5-0);
p = 10^-2;
f6 = 1 * p^0 * (1-p).^(n5-0);
figure(2)
semilogy(n5/8, f4, 'b-', n5/8, f5, 'r:', n5/8, f6, 'g--')
title("Probability of packet reception without errors")
xlabel("Packet Size (bytes)")
legend('ber=1e-4', 'ber=1e-3', 'ber=1e-2', 'location', 'SouthWest')
grid on