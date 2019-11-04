alfabeto_pt = ['A':'Z', 'À', 'Á', 'Â', 'Ã', 'Ç', 'É', 'Ê', 'Í', 'Ó', 'Ô', ...
    'Õ', 'Ú', 'a':'z', 'à', 'á', 'â', 'ã', 'ç', 'é', 'ê', 'í', 'ó', 'ô', ...
    'õ', 'ú', '0':'9'];  

ficheiros = {'pg21209.txt','pg26017.txt'};
pmfPT = pmfLetrasPT(ficheiros, alfabeto_pt);
distLetrasPT = cumsum(pmfPT);

tamanhoBloom = 8000;

string_num = 1000;
tamanhoStr = 40;

numTeste = 10000;
falso_positivo_teorico = zeros(15, 1);
falsos_positivos = zeros (15, 1);



for num_hashes = 1:15
      
    bar = waitbar(0, sprintf('A gerar bloom filters para k=%d...', num_hashes));            % wait bar

    bloom_filter = zeros(1, tamanhoBloom, 'uint8');

    for i=1:string_num   
        bloom_filter = addToBloomFilter(bloom_filter, num_hashes,  ...
            generateRandomPTString(tamanhoStr, alfabeto_pt, distLetrasPT));
    end
    
    falso_positivo_teorico(num_hashes, :) = (1 - exp(-num_hashes*string_num/numTeste))^num_hashes;

    for i=1:numTeste
        
        waitbar(i/numTeste, bar);

        test_str = generateRandomPTString(tamanhoStr, alfabeto_pt, distLetrasPT);
        
        if (belongsToBloomFilters(bloom_filter, num_hashes, test_str) == 1)
            falsos_positivos(num_hashes, :) = falsos_positivos(num_hashes, :) + 1;
        end
        if rem(i, 10) == 0
            fprintf(".");
            
            if rem(i, 1000) == 0
                fprintf("\n");
            end
        end
    end
    
    waitbar(1, bar, sprintf('\nDone!'));
    delete(bar);

end

figure(1);
stem(falsos_positivos/numTeste);
hold on;
stem(falso_positivo_teorico);
axis([-.5 15.5 0 1]);

fprintf("\nPercentagem de falsos positivos: %4.2f%%\n", (falsos_positivos/numTeste)*100);