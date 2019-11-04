function [ J ] = DistanciaJaccard( Set )
definirSize = length(Set);

J = zeros(definiirSize);        % array para guardar distâncias
h= waitbar(0,'Calculating');

for n1= 1: definirSize,
    valueBar =  n1 / setSize;
    waitbar(valueBar,h, sprintf('Calculating...\n%.2f%% done.', valueBar * 100));
    
    for n2 = n1+1: definirSize,
        % intersection length
        li = length(intersect(Set{n1}, Set{n2}));
        
        % union
        lu = length(Set{n1}) + length(Set{n2}) - intersectLength;
        
        % Jaccard Distance
        J(n1, n2) = 1 - li / lu;
        
    end
end
delete (h)

end