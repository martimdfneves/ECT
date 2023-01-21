function [sol, load, Loads] = hillClimbing(nNodes, Links, T, sP, nSP, sol, load, Loads)
    nFlows= size(T,1);
    % set the best local variables
    bestLocalLoad = load;
    bestLocalSol = sol;
    bestLocalLoads = Loads;

    % Hill Climbing Strategy
    improved = true;
    while improved
        % test each flow
        for flow = 1 : nFlows
            % test each path of the flow
            for path = 1 : nSP(flow)
                if path ~= sol(flow)
                    % change the path for that flow
                    auxSol = sol;
                    auxSol(flow) = path;
                    % calculate loads
                    Loads = calculateLinkLoads(nNodes,Links,T,sP,sol);
                    %Loads = [Loads_uni(:,1:2) Loads_uni(:,3)+BaseLoads(:,3) Loads_uni(:,4)+BaseLoads(:,4) ];
                    auxLoad = max(max(Loads(:, 3:4)));
                        
                    % check if the current load is better then start load
                    if auxLoad < bestLocalLoad
                        bestLocalLoad = auxLoad;
                        bestLocalSol = auxSol;
                        bestLocalLoads = Loads;
                    end
                end
            end
        end

        if bestLocalLoad < load
            load = bestLocalLoad;
            sol = bestLocalSol;
            Loads = bestLocalLoads;
        else
            improved = false;
        end
    end
end