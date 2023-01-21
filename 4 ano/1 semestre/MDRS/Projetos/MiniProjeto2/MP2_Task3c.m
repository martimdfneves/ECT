clear all
close all
clc

load('InputDataProject2.mat')
nNodes= size(Nodes,1);
nLinks= size(Links,1);
nFlows= size(T_uni,1);
nAnyFlows= size(T_any,1);
T_any = [T_any(:, 1) zeros(length(T_any(:, 1)), 1) T_any(:, 2) T_any(:, 3)];
nodesTany=T_any(:, 1);
anycastCandidates=[4 5 6 12 13];

% Computing up to k=2 shortest paths for all flows from 1 to nFlows:
k= 6;
sPu= cell(1,nFlows);
nSPu= zeros(1,nFlows);
for f=1:nFlows
    [shortestPath, totalCost] = kShortestPath(L,T_uni(f,1),T_uni(f,2),k);
    sPu{f}= shortestPath;
    nSPu(f)= length(totalCost);
end

node1=0;
node2=0;
globalWorstLink=0;
globalBestEnergy=Inf;
globalLinksChanged='';
for y=1:length(anycastCandidates)-1
    for z=y+1:length(anycastCandidates)

        aNodes= [anycastCandidates(y),anycastCandidates(z)];  % Anycast Nodes

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
        timeLimit = 60;
        bestEnergy = inf;
        while toc(t) < timeLimit
            % greedy randomzied start
            % first greedy randomized solution
            [sol, load, Loads, energy] = greedyRandomizedEnergy3(nNodes, Links, T, sP, nSP, L);
            while energy == inf
                [sol, load, Loads, energy] = greedyRandomizedEnergy3(nNodes, Links, T, sP, nSP, L);
            end
        
            [sol, load, Loads, energy] = hillClimbingEnergy3(nNodes, Links, T, sP, nSP, sol, load, Loads, energy, L);
        
            if energy < bestEnergy
                bestSol= sol;
                bestLoad= load;
                bestLoads = Loads;
                bestEnergy = energy;
                bestLoadTime = toc(t);
            end
        end
        
        idx=1;
        changedLinks = '';
        for i = 1 : length(bestLoads)
            if max(bestLoads(i,3)) > 50 | max(bestLoads(i,4)) > 50
                changedLinks = append(changedLinks, ' {', num2str(bestLoads(i,1)), ',', num2str(bestLoads(i,2)), '}');
            end
        end

        fprintf('nodes=%d/%d, W=.%2f, E=%.2f, changed=%s',anycastCandidates(y), anycastCandidates(z), bestLoad, bestEnergy, changedLinks);

        if bestEnergy < globalBestEnergy
            globalBestEnergy = bestEnergy;
            globalWorstLink = bestLoad;
            node1 = anycastCandidates(y);
            node2 = anycastCandidates(z);
            globalLinksChanged = changedLinks;
        end
    end
end

fprintf('The chosen Anycast nodes are %d and %d\n', node1, node2);
fprintf('List of links that changed capacity to 100Gbps:%s\n', globalLinksChanged);
fprintf('W = %.2f\tE = %.2f Gbps', globalWorstLink, globalBestEnergy);