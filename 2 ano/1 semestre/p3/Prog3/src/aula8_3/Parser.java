package aula8_3;

import java.io.*;
import java.util.*;

public class Parser {
	
    private static Random rand = new Random();

    public static void parse(Collection<Question> q, String filename) throws IOException {
    	
    	BufferedReader file = new BufferedReader(new FileReader(filename));
    	String linhas[] = (file.lines()).toArray(String[]::new);
    	for(String linha: linhas) {
    		
			String parts[] = linha.split("&&");
			q.add(new Question(parts[0].split(":")[1], parts[1], parts[2], Integer.parseInt(parts[6]), parts[2], parts[3], parts[4], parts[5]));
		}
    	file.close();
    }
        
    public static <T> T[] scrambleArray(T[] ops) {
    	
        T tmp;
        for (int i = ops.length - 1, idx; i > 0; i--){
        	
            idx = rand.nextInt(i + 1);
            tmp = ops[idx];
            ops[idx] = ops[i];
            ops[i] = tmp;
        }
        return ops;
    }

    public static <T extends Comparable<T>> T[] sort(T[] arr){
    	
        Arrays.sort(arr);
        return arr;
    }
}