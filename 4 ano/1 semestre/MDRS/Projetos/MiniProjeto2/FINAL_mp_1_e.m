clear all
close all
clc

load('InputDataProject2.mat')
nNodes= size(Nodes,1);
nLinks= size(Links,1);
nFlows= size(T_uni,1);

k=1;
v = 2e5; %propagation delay (km/sec)
D = L/v;
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
        nSPa(n) = 0;
        continue;
    end

    for a = 1:length(aNodes)
        [shortestPath, totalCost] = kShortestPath(D, n, aNodes(a), k);

        if totalCost < best
            sPa{n}(1)= shortestPath;
            best = totalCost;
            nSPa(n) = totalCost;
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
nAnyFlows= size(T_any,1);
sPa = sPa(~cellfun(@isempty, sPa));
AnycastLoads= calculateLinkLoads(nNodes, Links, T_any, sPa, ones(1, nAnyFlows));

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
fprintf('The network energy consumption is %.4f, the nodes energy consumption is %.4f and the links energy consultion is %.4f\n', En+El,En,El);






clc









%%%%%%%%%% 1.e.) %%%%%%%%%%

%load('InputDataProject2.mat')

nNodes= size(Nodes,1);
nLinks= size(Links,1);
nAllFlows= size(T,1);

% Computing up to k=inf shortest paths for all flows from 1 to nAllFlows:
k= 6;                   %%% ALTERADO
sP= cell(1,nAllFlows);
nSP= zeros(1,nAllFlows);

for f=1:nAllFlows
    [shortestPath, totalCost] = kShortestPath(L,T(f,1),T(f,2),k);
    sP{f}= shortestPath;
    nSP(f)= length(totalCost);
end
% sP{f}{i} is the i-th path of flow f
% nSP(f) is the number of paths of flow f

% Optimization algorithm based on Multi Start Hill Climbing algorithm with Greedy Randomized strategy:
t = tic;
timeLimit= 30;

bestLoad = inf;
contador= 0;
somador= 0;
bestTime = 0;

while toc(t) < timeLimit

    [sol, load, Loads] = greedyRandomized(nNodes, Links, T_uni, sP, nSP, AnycastLoads);
    %%%%load= max(max(Loads(:,3:4)));
    [sol, load, Loads] = hillClimbing(nNodes, Links, T_uni, sP, nSP, sol, load, Loads, AnycastLoads);

    if load<bestLoad
        bestSol= sol;
        bestLoad= load;
        bestLoads= Loads; %%%%% TODO:
        bestTime = toc(t);
    end
    contador= contador+1; %%%%% TODO:
    somador= somador+load; %%%%% TODO:

end





%%%%% Energy - 1.b)
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