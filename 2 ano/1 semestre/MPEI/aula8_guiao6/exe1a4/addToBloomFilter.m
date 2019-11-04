function [bloom_filter] = addToBloomFilter(bloom_filter, num_hashes, elemento)

    for i=1:num_hashes
        elem_unico = [elemento num2str(i^5)];

        elem_index = rem(string2hash(elem_unico), length(bloom_filter)) + 1;
        bloom_filter(elem_index) = 1;
    end
end