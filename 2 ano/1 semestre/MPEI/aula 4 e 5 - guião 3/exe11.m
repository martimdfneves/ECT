N = 1e5; 

% X ~ N(14, 2)
X = (randn(1, N) .* 2) + 14;

%% (a) 
P = (sum(X >= 12) - sum (X > 16)) /N

%% (b) 
P = (sum(X >= 10) - sum (X > 18)) /N

%% (c) 
P = (sum(X >= 10)) /N