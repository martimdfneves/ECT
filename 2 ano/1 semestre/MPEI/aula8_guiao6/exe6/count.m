function [ value ] = count ( B, k, palavra )
    m = length(B);
    
    valores = zeros(1, k);
    palavraToHash=palavra;
    
    for j= 1: k
        palavraToHash = [palavraToHash num2str(j)];               
        h = rem (string2hash(palavraToHash), m) + 1;
        valores(j) = B(h);
    end
 
    value = min(valores);
end