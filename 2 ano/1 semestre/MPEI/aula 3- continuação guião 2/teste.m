N = 1e5;
n = 1;
sucesses = 0;

while (sucesses / N < 0.5)
    n = n+1;
    
    peopleBirthdays = ceil(366*rand(n, N));

    sucesses = 0;
    for i=1:N
        sucesses = sucesses + (length(unique(peopleBirthdays(:,i)))~=n);
    end
end
n