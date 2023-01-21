clear all
close all
clc

load('InputDataProject2.mat')
nNodes= size(Nodes,1);
nLinks= size(Links,1);
nFlows= size(T_uni,1);
nAnyFlows= size(T_any,1);
aNodes= [5,12];  % Anycast Nodes
T_any = [T_any(:, 1) zeros(length(T_any(:, 1)), 1) T_any(:, 2) T_any(:, 3)];
nodesTany=T_any(:, 1);

% Computing up to k=6 shortest paths for all flows from 1 to nFlows:
k= 6;
sPu= cell(1,nFlows);
nSPu= zeros(1,nFlows);
for f=1:nFlows
    [shortestPath, totalCost] = kShortestPath(L,T_uni(f,1),T_uni(f,2),k);
    sPu{f}= shortestPath;
    nSPu(f)= length(totalCost);
end


sPa= cell(1,nAnyFlows);
nSPa= zeros(1,nAnyFlows);
for n = 1:nNodes
    best = inf;

    if ~ismember(n, nodesTany)    % if the node does not have an anycastFlow skip it

        continue;
    end

    for a = 1:length(aNodes)
        [shortestPath, totalCost] = kShortestPath(L, n, aNodes(a), 1);

        if totalCost < best
            sPa{n}(1)= shortestPath;
            best = totalCost;
            nSPa(n) = length(totalCost);
        end
    end
end

idx=1;
for p = sPa
    if isempty(p{1})
        continue;
    end
    T_any(idx, 2) = p{1}{1}(end);
    idx = idx + 1;
end
sPa = sPa(~cellfun(@isempty, sPa));

T=[T_uni;T_any];
sP=cat(2,sPu,sPa);
sP = sP(~cellfun(@isempty, sP));
nSP=cat(2,nSPu,nSPa);
nSP=nonzeros(nSP);
nSP=nSP';

t = tic;
timeLimit = 30;
bestEnergy = inf;
while toc(t) < timeLimit
    % greedy randomzied start
    % first greedy randomized solution
    [sol, load, Loads, energy] = greedyRandomizedEnergy(nNodes, Links, T, sP, nSP, L);
    while energy == inf
        [sol, load, Loads, energy] = greedyRandomizedEnergy(nNodes, Links, T, sP, nSP, L);
    end

    [sol, load, Loads, energy] = hillClimbingEnergy(nNodes, Links, T, sP, nSP, sol, load, Loads, energy, L);

    if energy < bestEnergy
        bestSol= sol;
        bestLoad= load;
        bestLoads = Loads;
        bestEnergy = energy;
        bestLoadTime = toc(t);
    end
end

fprintf('W = %.2f\tE = %.2f Gbps\ttime = %.2f\n', bestLoad, bestEnergy, bestLoadTime);