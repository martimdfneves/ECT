% Jaccard Distance using Minhash
Set = carregarInformacao3('u.data');
definirSize = length(Set);

% Get minhashs
kMax = 15;
h = zeros(kMax, definirSize);
minHash = zeros(kMax);

% for each one of the k hash function
for k = 1: kMax
    % for each member of the set
    for n = 1: definirSize
        % get hash value
        key = tostring(Set(n)+num2str(k));
        h(k, n) = rem (string2hash(key), m) + 1;
    end
    % min hash
    minHash(k) = min(h(k,:));
end

% Estimate Jaccard Similarity
for k = 1: kMax
        valoresDiferentes = valoresDiferentes + length(unique(h(k:k+1,:)));
end
similaridadeJaccard = valoresDiferentes / kMax