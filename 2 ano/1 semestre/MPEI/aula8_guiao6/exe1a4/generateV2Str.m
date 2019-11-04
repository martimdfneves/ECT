function str = generateV2Str(mean, variancia, alfabeto, distanciaLetrasPT)

length = sqrt(variancia) * (floor(randn()) + mean);

for i=1:length
    indiceLetras = find(distanciaLetrasPT > rand());
    str(i) = alfabeto(indiceLetras(1));
end