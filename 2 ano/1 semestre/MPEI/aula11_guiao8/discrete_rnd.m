function state = discrete_rnd(states, probVector)

transitionsProbs = cumsum(probVector);
state = find(rand() < transitionsProbs, 1);