package aula6_2;

import java.util.*;
import java.util.function.*;

public class ListsProcess {
	
	public static <T> List<T> filter(List<T> lista, Predicate<T> tester) {
		
		List<T> tmp = new ArrayList<T>();
		for (T t : lista)
			if (tester.test(t))
				tmp.add(t);
		return tmp;
	}
}
