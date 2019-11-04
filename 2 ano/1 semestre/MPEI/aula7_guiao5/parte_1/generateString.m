function [ str ] = generateString ( min, max, letras )
tamanho = randi([min max]);                                                     
for i=1:  tamanho
    str(i) = letras(ceil(rand()*52));
end

end