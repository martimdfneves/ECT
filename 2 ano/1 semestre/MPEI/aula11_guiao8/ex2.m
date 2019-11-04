%% (a) 

T = [ 1/3    .25    0;   ...
      1/3    .55    1/2; ...
      1/3    .20    1/2];
  

stochastic = sum(sum(T)) - size(T,1);
fprintf("\nT é estocástica? %d\n\n", stochastic == 0);
%% (b)

I_0 = [1/2; 1/4; 1/4];

%% (c)

final_distribution = T^30 * I_0;

fprintf('(c) número de estudantes no grupo A: %d\n', round(90*final_distribution(1)))
fprintf('(c) número de estudantes no grupo B: %d\n', round(90*final_distribution(2)))
fprintf('(c) número de estudantes no grupo C: %d\n\n', round(90*final_distribution(3)))

%% (d)

I_0 = [1/3; 1/3; 1/3];

final_distribution = T^30 * I_0;

fprintf('(d) número de estudantes no grupo A: %d\n', round(90*final_distribution(1)))
fprintf('(d) número de estudantes no grupo B: %d\n', round(90*final_distribution(2)))
fprintf('(d) número de estudantes no grupo C: %d\n', round(90*final_distribution(3)))