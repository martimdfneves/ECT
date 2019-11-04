function [pertence] = pertenceToBloomFilters(bloom_filter, num_hashes, elemento)
    pertence = 1;
    for i=1:num_hashes
        
        elem_unico = [elemento num2str(i^5)];
        
        elem_index = rem(string2hash(elem_unico), length(bloom_filter)) + 1;
        
        if (bloom_filter(elem_index) == 0)
            pertence = 0;
            break;
        end 
    end
end