function [ B ] = insert ( B, k,  palavra )
    m = length(B);   
    palavraToHash = palavra;
    
    for i = 1: k
        palavraToHash = [palavraToHash num2str(i)];           
        h = rem (string2hash(palavraToHash), m) + 1;       
        B(h) = B(h) + 1;                                                        
    end
end