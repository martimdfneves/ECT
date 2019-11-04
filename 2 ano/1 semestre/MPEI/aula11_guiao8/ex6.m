%% (a)

H = [ 0.8    0.0    0.3    0.0; ...
      0.2    0.9    0.2    0.0; ...
      0.0    0.1    0.4    0.0; ...
      0.0    0.0    0.1    1.0];

%% (b) 

prob_1000_trans = H^1000 * [1; 0; 0; 0];

fprintf('(b) Probabilidade de estar na pagina 2 apos 100 transiçoes: %f\n', prob_1000_trans(2));

%% (c) 
prob_1_trans = zeros(4, 4);
prob_2_trans = zeros(4, 4);
prob_100_trans = zeros(4, 4);

for i=1:4
    
    I_0 = [1;2;3;4] == i;
    
    aux = H^1 * I_0;
    prob_1_trans(:,i) = aux(:);
    
    aux = H^2 * I_0;
    prob_2_trans(:,i) = aux(:);
    
    aux = H^100 * I_0;
    prob_100_trans(:,i) = aux(:);
end

for i=1:4
    for j=1:4
        fprintf('\tProbabilidade de estar no estado %d apos  1  transiçao:  %f\n', j, prob_1_trans(j,i))
        fprintf('\tProbabilidade de estar no estado %d apos  2  transiçoes: %f\n', j, prob_2_trans(j,i))
        fprintf('\tProbabilidade de estar no estado %d apos 100 transiçoes: %f\n\n', j, prob_100_trans(j,i))
    end
end

%% (d) Determine a matriz Q.
%Q = [ 0.8    0.0    0.3; ...
%      0.2    0.9    0.2; ...
%      0.0    0.1    0.4];
Q = H(1:3,1:3);

%% (e) Determine a matriz fundamental F.
n = size(Q, 1);
F = inv(eye(n) - Q);

%% (f) Q
meanOfIterations = sum(F);

fprintf('(f) Transiçoes para chegar ao estado 4 apos começar no estado 1: %f transiçoes\n', meanOfIterations(1));
fprintf('(f) Transiçoes para chegar ao estado 4 apos começar no estado 2: %f transiçoes\n', meanOfIterations(2));
fprintf('(f) Transiçoes para chegar ao estado 4 apos começar no estado 3: %f transiçoes\n\n', meanOfIterations(3));

%% (g) 

fprintf('(g) Tempo para o estado 1 ser absorvido: %f transiçoes\n', meanOfIterations(1));
fprintf('(g) Tempo para o estado 2 ser absorvido: %f transiçoes\n', meanOfIterations(2));
fprintf('(g) Tempo para o estado 3 ser absorvido: %f transiçoes\n\n', meanOfIterations(3));


%% (h)

NewH = [ 0.8    0.1    0.5    0.0; ...
         0.2    0.8    0.25   0.0; ...
         0.0    0.1    0.2    0.0; ...
         0.0    0.0    0.05   1.0];
     
NewQ = NewH(1:3,1:3);
NewF = inv(eye(3) - NewQ);
meanOfIterations = sum(NewF);

fprintf('(h) Novo tempo para o estado 1 ser absorvido: %f (transitions)\n', meanOfIterations(1));
fprintf('(h) Novo tempo para o estado 2 ser absorvido: %f (transitions)\n', meanOfIterations(2));
fprintf('(h) Novo tempo para o estado 3 ser absorvido: %f (transitions)\n\n', meanOfIterations(3));

%% (i) 

H = [ 0.8    0.0    0.3    0.0; ...
      0.2    0.9    0.2    0.0; ...
      0.0    0.1    0.4    0.0; ...
      0.0    0.0    0.1    1.0];
  
transientStatesNum = 3;
Q = H(1:transientStatesNum, 1:transientStatesNum);
F = inv(eye(transientStatesNum)-Q);

averageTransState = zeros(3,1);
expIterations = 5000;

tic
for i=1:expIterations
    for j=1:transientStatesNum
        averageTransState(j) = averageTransState(j) + crawl_length(H, j, 4);
    end
end
toc

averageTransState = averageTransState / expIterations;

fprintf('(i) Tempo experimental para o estado 1 ser absorvido: %f (transitions)\n', averageTransState(1));
fprintf('(i) Tempo experimental para o estado 2 ser absorvido: %f (transitions)\n', averageTransState(2));
fprintf('(i) Tempo experimental para o estado 3 ser absorvido: %f (transitions)\n', averageTransState(3));