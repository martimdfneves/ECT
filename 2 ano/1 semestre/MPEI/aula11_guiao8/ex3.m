T = rand(20, 20);
for i=1:20
    T(:, i) = T(:, i) / sum(T(:, i));
end

I_0 = zeros(20,1);
I_0(1) = 1;

after_20_trans = T^20 * I_0;
fprintf('Probabilidade de transitar entre o primeiro e ultimo estado em 20 transiçoes: %f\n', after_20_trans(20))
after_40_trans = T^40 * I_0;
fprintf('Probabilidade de transitar entre o primeiro e ultimo estado em 20 transiçoes: %f\n', after_40_trans(20))
after_100_trans = T^100 * I_0;
fprintf('Probabilidade de transitar entre o primeiro e ultimo estado em 20 transiçoes: %f\n', after_100_trans(20))