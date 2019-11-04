T = [0.7 0.8; 0.3 0.2];

%% (a)
I_0 = [1; 0];
next_week_probabilities = T^2 * I_0;

fprintf('(a) probabilidade de estar presente: %f\n', next_week_probabilities(1))

%% (b)
I_0 = [0; 1];
next_week_probabilities = T^2 * I_0;

fprintf('(b) probabilidade de estar presente: %f\n', next_week_probabilities(1))

%% (c)
I_0 = [1; 0];
last_week_probabilities = T^29 * I_0;

fprintf('(c) probabilidade de estar presente: %f\n', last_week_probabilities(1))

%% (d)
I_0 = [0.85; 0.15];
prob_present = zeros(30,1);
prob_not_present = zeros(30,1);

for i=1:30
    ith_week_probabilities = T^(i-1) * I_0;
    prob_present(i) = ith_week_probabilities(1);
    prob_not_present(i) = ith_week_probabilities(2);
end

figure; 
hold on;
p1 = plot(prob_present);
p2 = plot(prob_not_present);
legend([p1, p2], ["probabilidade de estar presente", "probabilidade de não estar presente"])
axis([0 30 0 1])