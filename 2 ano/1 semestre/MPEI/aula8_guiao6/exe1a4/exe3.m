alfabeto_pt = ['A':'Z', 'À', 'Á', 'Â', 'Ã', 'Ç', 'É', 'Ê', 'Í', 'Ó', 'Ô', ...
    'Õ', 'Ú', 'a':'z', 'à', 'á', 'â', 'ã', 'ç', 'é', 'ê', 'í', 'ó', 'ô', ...
    'õ', 'ú', '0':'9'];  

ficheiros = {'pg21209.txt','pg26017.txt'};
pmfPT = pmfLetrasPT(ficheiros, alfabeto_pt);
distLetrasPT = cumsum(pmfPT);

tamanho_Bloom = 8000;
hashes_num = 3;
bloom_filter = zeros(1, tamanho_Bloom, 'uint8');

string_num = 1000;
tamanhoStr = 40;

for i=1:string_num   
    bloom_filter = addToBloomFilter(bloom_filter, hashes_num,  ...
        generateRandomPTString(tamanhoStr, alfabeto_pt, distLetrasPT));
end


%%  (b) 

numTest = 10000;

falsos_positivos = 0;
for i=1:numTest
    
    test_str = generateRandomPTString(tamanhoStr, alfabeto_pt, distLetrasPT);
    
    if belongsToBloomFilters(bloom_filter, hashes_num, test_str) == 1
        fprintf("O ");
        falsos_positivos = falsos_positivos + 1;
    else
        fprintf("X ");
    end
    if rem(i, 50) == 0
        fprintf("\n");
    end
end

fprintf("\nPercentagem de falsos positivos: %4.3f%%\n", (falsos_positivos/numTest)*100);

% Cerca de 15%. Sim, pode talvez ter sido um pouco mais baixo que esperado,
% mas também foi usado um valor elevado de n (= 8000), portanto não é um
% resultado surpreendente.