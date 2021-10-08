package aula5_1;

import java.util.*;

public interface UtilCompare {

    @SuppressWarnings("unchecked")
    public static <T> Comparable<T> findMax(Comparable<T>[] comp) {

        int maxIndex = 0;
        for (int i = 1; i < comp.length; i++)
            if (comp[i].compareTo((T) comp[maxIndex]) > 0)
                maxIndex = i;
        return comp[maxIndex];
    }

    public static <T> void sortArray(Comparable<T> toSort[]) {

        Arrays.sort(toSort);
    }
}