function state = nextState(H, currentState)
    probVector = H(:,currentState);  
    n = length(probVector);
    state = discrete_rnd(1:n, probVector);
end