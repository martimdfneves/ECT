function str = generateStr(compMin, compMax, alfabeto)

length = floor(rand()*(compMax-2)) + compMin;
for i=1:length
    str(i) = alfabeto(ceil(rand()*52));
end