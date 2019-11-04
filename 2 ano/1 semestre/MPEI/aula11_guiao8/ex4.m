%% (a) D

p = 0.4;
q = 0.6;
T = [   p.^2, 0, 0, q.^2;       ...
    (1-p).^2, 0, 0, q.*(1-q);   ...
    p.*(1-p), 0, 0, q.*(1-q);   ...
    p.*(1-p), 1, 1, (1-q).^2];

%% (b) 

I_0 = [1; 0; 0; 0];

after_10_trans = T^10 * I_0;

fprintf('Probabilidade de estar no estado A apos 10 transiçoes: %f\n', after_10_trans(1))
fprintf('Probabilidade de estar no estado B apos 10 transiçoes: %f\n', after_10_trans(2))
fprintf('Probabilidade de estar no estado C apos 10 transiçoes: %f\n', after_10_trans(3))
fprintf('Probabilidade de estar no estado D apos 10 transiçoes: %f\n', after_10_trans(4))