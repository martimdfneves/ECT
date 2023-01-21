function [sol, load, Loads, energy] = hillClimbingEnergy(nNodes, Links, T, sP, nSP, sol, load, Loads, energy, L)
    nFlows= size(T,1);
    % set the best local variables
    bestLocalLoad = load;
    bestLocalSol = sol;
    bestLocalLoads = Loads;
    bestLocalEnergy = energy;

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
                    [auxLoad, auxLoads, auxEnergy] = calculateLinkEnergy(nNodes,Links,T,sP,auxSol, L);
                        
                    % check if the current load is better then start load
                    if auxEnergy < bestLocalEnergy
                        bestLocalLoad = auxLoad;
                        bestLocalSol = auxSol;
                        bestLocalLoads = auxLoads;
                        bestLocalEnergy = auxEnergy;
                    end
                end
            end
        end

        if bestLocalEnergy < energy
            load = bestLocalLoad;
            sol = bestLocalSol;
            Loads = bestLocalLoads;
            energy = bestLocalEnergy;
        else
            improved = false;
        end
    end
end