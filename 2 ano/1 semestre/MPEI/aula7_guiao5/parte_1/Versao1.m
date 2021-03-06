%% Primeira Vers�o
figure
n = 1000;
FatorCarga = 0.8;
alfabeto = ['a':'z' 'A':'Z'];

m= round(n/FatorCarga);
contador_djb2 = zeros(1, m);
contador_sdbm = zeros(1, m);

for i = 1: n
    key = generateString (3,20, alfabeto);            
    valor_djb2 = rem(string2hash(key), m) + 1;  
    valor_sdbm = rem(string2hash(key, 'sdbm'), m) + 1;
    contador_djb2(valor_djb2)  = contador_djb2(valor_djb2) + 1;
    contador_sdbm(valor_sdbm) = contador_sdbm(valor_sdbm) + 1;
    
    if rem (i, 25) == 0
        % djb2
        subplot(221)
        bar(contador_djb2, 'r')
        grid on
        drawnow
        
        subplot(222)
        hist(contador_djb2, 0:9, 'b')
        grid on
        drawnow
        
        % sdbm
        subplot(223)
        bar(contador_sdbm, 'r')
        grid on
        drawnow
        
        subplot(224)
        hist(contador_sdbm, 0:9, 'r')
        grid on
        drawnow
    end
 
end

x = [0:9]; 

% Para o algoritmo djb2
figure
subplot(1,2,1)
sucessos_djb2 = histc(contador_djb2, 0:9);
pmfX_djb2 = sucessos_djb2 / m;
stem(0:9, pmfX_djb2);
grid on;
axis([-0.5, 9.5, 0, 0.5]);
drawnow

fprintf('Algoritmo djb2: ');
e_x = sum(x.*pmfX_djb2);
fprintf('\n\tE[X] = %f', e_x);
e_x2 = sum((x.^2).*pmfX_djb2);
fprintf('\n\tVar(X) = %f', e_x2 - e_x^2);
fprintf('\n')

% Para o algoritmo sdbm
subplot(1, 2, 2)
sucessos_sdbm = histc(contador_sdbm, 0:9);
pmfX_sdbm = sucessos_sdbm / m;
stem(x, pmfX_sdbm, 'r');
grid on;
axis([-0.5, 9.5, 0, 0.5]);
drawnow

fprintf('Algoritmo sdbm: ');
e_x = sum(x.*pmfX_sdbm);
fprintf('\n\tE[X] = %f', e_x);
e_x2 = sum((x.^2).*pmfX_sdbm);
fprintf('\n\tVar(X) = %f\n', e_x2 - e_x^2);
fprintf('\n')
