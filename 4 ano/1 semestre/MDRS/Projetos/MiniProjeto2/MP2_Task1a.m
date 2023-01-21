clear all
close all
clc

load('InputDataProject2.mat')
nNodes= size(Nodes,1);
nFlows= size(T_uni,1);

k=1;
sPu= cell(1,nNodes);
sPa= cell(1,nNodes);
aNodes= [5,12];  % Anycast Nodes
T_any = [T_any(:, 1) zeros(length(T_any(:, 1)), 1) T_any(:, 2) T_any(:, 3)];
nodesTany=T_any(:, 1);

%a)
for f=1:nFlows
    [shortestPath, totalCost] = kShortestPath(L,T_uni(f,1),T_uni(f,2),k);
    sPu{f}= shortestPath;
    nSPu(f)= totalCost;
end

for n = 1:nNodes
    best = inf;

    if ~ismember(n, nodesTany)    % if the node does not have an anycastFlow skip it

        continue;
    end

    for a = 1:length(aNodes)
        [shortestPath, totalCost] = kShortestPath(L, n, aNodes(a), k);

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

nAnyFlows= size(T_any,1);
sol= ones(1,nFlows+nAnyFlows);
sP = sP(~cellfun(@isempty, sP));
Loads = calculateLinkLoads(nNodes,Links,T,sP,sol);
maxLoad= max(max(Loads(:,3:4)));
fprintf('Worst link load = %.1f Gbps\n', maxLoad);
for i = 1:length(Loads)
    fprintf("{%d-%d}:  \t%.2f %.2f\n", Loads(i, 1),Loads(i, 2),Loads(i, 4),Loads(i, 3));
end

%b)
idx=1;
sleepNodes=[];
sleepingNodes = '';
for i = 1 : length(Loads)
    if max(Loads(i, 3:4)) == 0
        sleepingNodes = append(sleepingNodes, ' {', num2str(Loads(i,1)), ',', num2str(Loads(i,2)), '}');
        aux=[Loads(i,1) Loads(i,2)];
        sleepNodes=[sleepNodes;aux];
        idx=idx+1;
    end
end
fprintf('List of links in sleeping mode:%s\n', sleepingNodes);

C=500;
t=zeros(1,nNodes);
for j=1:size(T,1)
    for k=1:length(sP{j}{1})
        t(sP{j}{1}(k))=t(sP{j}{1}(k))+T(j,3)+T(j,4);
    end
end
En=0;
for i=1:length(t)
    En=En+(10+90*(t(i)/C)^2);
end

El=0;
for i=1:length(L)-1
    for j=i+1:length(L)
        if(L(i,j)~=0 & L(i,j)~=Inf)
            if ~ismember([i j], sleepNodes,'rows')
                El=El+(6+0.2*L(i,j));
            else
                El=El+2;
            end
        end
    end
end
fprintf('The network energy consumption is %.4f\n', En+El);
fprintf('The nodes energy consumption is %.4f\n', En);
fprintf('The links energy consumption is %.4f\n',El);


%%%%%%%%%% 1.e.) %%%%%%%%%%

% Computing up to k=6 shortest paths for all flows from 1 to nAllFlows:
k= 6;                   
sPud= cell(1,nFlows);
nSPud= zeros(1,nFlows);

for f=1:nFlows
    [shortestPath, totalCost] = kShortestPath(L,T_uni(f,1),T_uni(f,2),k);
    sPud{f}= shortestPath;
    nSPud(f)= length(totalCost);
end

sPd=cat(2,sPud,sPa);
sPd = sPd(~cellfun(@isempty, sPd));
nSPd=cat(2,nSPud,nSPa);
nSPd=nonzeros(nSPd);
nSPd=nSPd';

% Optimization algorithm based on Multi Start Hill Climbing algorithm with Greedy Randomized strategy:
t = tic;
timeLimit= 30;

bestLoad = inf;
bestTime = 0;

while toc(t) < timeLimit

    [sol, load, Loads] = greedyRandomized(nNodes, Links, T, sPd, nSPd);

    [sol, load, Loads] = hillClimbing(nNodes, Links, T, sPd, nSPd, sol, load, Loads);

    if load<bestLoad
        bestSol= sol;
        bestLoad= load;
        bestLoads= Loads; 
        bestTime = toc(t);
    end
end


idx=1;
sleepNodes=[];
for i = 1 : length(Loads)
    if max(Loads(i, 3:4)) == 0
        aux=[Loads(i,1) Loads(i,2)];
        sleepNodes=[sleepNodes;aux];
        idx=idx+1;
    end
end

C=500;
t=zeros(1,nNodes);
for j=1:size(T,1)
    for l=1:length(sPd{j}{bestSol(j)})
        t(sPd{j}{bestSol(j)}(l))=t(sPd{j}{bestSol(j)}(l))+T(j,3)+T(j,4);
    end
end
En=0;
for i=1:length(t)
    En=En+(10+90*(t(i)/C)^2);
end

El=0;
for i=1:length(L)-1
    for j=i+1:length(L)
        if(L(i,j)~=0 & L(i,j)~=Inf)

            if isempty(sleepNodes)
                El=El+(6+0.2*L(i,j));
            else
                if ~ismember([i j], sleepNodes, 'rows')
                    El=El+(6+0.2*L(i,j));
                else
                    El=El+2;
                end
            end

        end
    end
end
fprintf('Greedy Randomized - Hill Climbing:\n')
fprintf('W = %.2f Gbps, E = %.4f, time = %.2f sec\n', bestLoad, En+El, bestTime)