function [ str ] = generateString2 (ValorMedio, variancia, letras, distletrasPT)
tamanho = ValorMedio + (sqrt(variancia)* randn());           
for i=1:  tamanho
    IndiceLetra = find(distletrasPT > rand());
    str(i) = letras(IndiceLetra(1));
end

end
