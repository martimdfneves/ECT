membros = {'Europa', 'Portugal', 'Suica', 'Alemanha', 'Franca', 'Espanha', ...
           'Italia', 'Belgica', 'Luxemburgo'};
       
teste = {'Reino Unido', 'Polonia', 'Bea', 'Ivan', 'Russia', 'Suica' };

m = 100;
num_hashes = 3;

bloom_filter = zeros(1, m, 'uint8');

for i=1:length(membros)
    bloom_filter = addToBloomFilter(bloom_filter, num_hashes, membros{i});
end
for i=1:length(teste)
    if belongsToBloomFilters(bloom_filter, num_hashes, teste{i})
        fprintf("O - %s pertence ao filtro.\n", teste{i});
    else
        fprintf("X - %s não pertence ao filtro.\n", teste{i});
    end
end