function [load, Loads, energy] = calculateLinkEnergy3(nNodes,Links,T,sP,Solution, L)
    nFlows= size(T,1);
    nLinks= size(Links,1);
    aux= zeros(nNodes);
    for i= 1:nFlows
        if Solution(i)>0
            path= sP{i}{Solution(i)};
            for j=2:length(path)
                aux(path(j-1),path(j))= aux(path(j-1),path(j)) + T(i,3); 
                aux(path(j),path(j-1))= aux(path(j),path(j-1)) + T(i,4);
            end
        end
    end
    Loads= [Links zeros(nLinks,2)];
    for i= 1:nLinks
       Loads(i,3)= aux(Loads(i,1),Loads(i,2));
       Loads(i,4)= aux(Loads(i,2),Loads(i,1));
    end
    load = max(max(Loads(:, 3:4)));
    if load > 100   % If the worst link load is greater than max capacity , energy will be infinite 
        energy = inf;
    else
        energy = 0;
        for i= 1:nLinks
            % link in sleeping mode
            if max(Loads(i, 3:4)) == 0
                energy = energy + 2;
            elseif max(Loads(i,3)) > 50 | max(Loads(i,4)) > 50
                len = L(Loads(i, 1), Loads(i, 2));
                energy = energy + (8 + 0.3 * len);
            else
                len = L(Loads(i, 1), Loads(i, 2));
                energy = energy + (6 + 0.2 * len);
            end
        end

        C=500;
        t=zeros(1,nNodes);
        for j=1:size(T,1)
            if Solution(j)>0
                for k=1:length(sP{j}{Solution(j)})
                    t(sP{j}{Solution(j)}(k))=t(sP{j}{Solution(j)}(k))+T(j,3)+T(j,4);
                end
            end
        end
        En=0;
        for i=1:length(t)
            En=En+10+90*(t(i)/C)^2;
        end
        energy=energy+En;
    end
end