nomeFicheiro = 'pg17186.txt';               
ficheiroPalavras = readFile (nomeFicheiro);

n =  length(ficheiroPalavras);                         
m = n * 15;                                     
k = round ((m / n) * log(2));                   
bloomFilter = init (m);

fprintf('\nA criar bloom filter para as palavras no ficheiro %s...\n', nomeFicheiro);
bar = waitbar(0,' criar bloom filter para as palavras...');            
for i = 1: n
    bloomFilter = insert (bloomFilter, k, ficheiroPalavras{i});
    ValorBar =  i / n;
    waitbar(ValorBar, bar, sprintf('\nA criar bloom filter para as palavras no ficheiro %s...\n%.2f%% done.', nomeFicheiro, ValorBar * 100))
end
waitbar(1, bar, sprintf('\nDone!'))
delete(bar)

fprintf('Número de ocurrências de palavras: \n');
ficheiroPalavras = sort(unique(ficheiroPalavras));     
n = length(ficheiroPalavras);
valores = zeros(1, n);

for i = 1: n
    palavra = ficheiroPalavras{i};
    valores(i) = count(bloomFilter, k, palavra);
    fprintf('\t%s -> %d\n', palavra, valores(i));
end

[maxValor, maxIndex] = max(valores);
fprintf('\n------------------------\nPalavra mais frequente: %s (%d vezes)\n', ficheiroPalavras{maxIndex}, maxValor);