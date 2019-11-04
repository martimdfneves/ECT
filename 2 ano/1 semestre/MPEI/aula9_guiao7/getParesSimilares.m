function [ ParesSimilares ] = getParesSimilares(Set, J, threshold)

definirSize = length(Set);

% Array para guardar pares similares (user1, user2, distância)
ParesSimilares= zeros(1,3);
k= 1;
for n1= 1: definirSize,
    for n2= n1+1: definirSize,
        if J(n1, n2) < threshold
            ParesSimilares(k,:) = [Set(n1) Set(n2) J(n1,n2)];
            k= k+1;
        end
    end
end

numParesSimilares = length(ParesSimilares);
fprintf('Número de pares considerados semelhantes (com distâncias inferiores ao limiar %1.1f): %d\n', threshold, numParesSimilares) 
fprintf('\nPair 1\tPair 2\tJaccard Distance\n')
for n = 1 : numParesSimilares
    fprintf('%d\t%d\t%f\n', ParesSimilares(n, 1), ParesSimilares(n,2), ParesSimilares(n,3));
end

end