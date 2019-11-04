function state = crawl(H, first, last)
    state = [first];
    while (1)
        state(end+1) = nextState(H, state(end));
        if (state(end) == last) 
            break; 
        end
    end
end