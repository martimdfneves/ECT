function [sol, load, Loads, energy] = greedyRandomizedEnergy(nNodes, Links, T, sP, nSP, L)
    nFlows = size(T,1);
    % random order of flows 
    randFlows = randperm(nFlows);
    sol = zeros(1, nFlows);

    % iterate through each flow
    for flow = randFlows
        path_index = 0;
        best_load = inf;
        best_Loads = inf;
        best_energy = inf;

        % test every path "possible" in a certain load
        for path = 1 : nSP(flow)
            % try the path for that flow
            sol(flow) = path;
            % calculate loads
            [load, Loads, energy] = calculateLinkEnergy(nNodes,Links,T,sP,sol, L);
            
            % check if the current energy is better then best_energy
            if energy < best_energy
                % change index of path and load
                path_index = path;
                best_load = load;
                best_Loads = Loads;
                best_energy = energy;
            end
        end
        
        %sol(flow) = path_index;

        %%%%%% NEW %%%%%%
        if path_index == 0
            break;
        else
            sol(flow) = path_index;
        end


    end
    load = best_load;
    Loads = best_Loads;
    energy = best_energy;
end