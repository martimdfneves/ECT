function walk_length = crawl_length(H, first, last)

    last_state = first;
    walk_length = 0;
    
    while (1)
        last_state = nextState(H, last_state);
        walk_length = walk_length + 1;
        if (last_state == last) 
            break; 
        end
    end
end