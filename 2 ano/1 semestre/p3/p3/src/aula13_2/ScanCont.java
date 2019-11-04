package aula13_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ScanCont {
	
	public static Map<String, TreeMap<String, Integer>> readFile(String file) throws IOException {
		
		Path fix = Paths.get(file);
		List<String> lines = Files.readAllLines(fix);
		List<String> words = new ArrayList<>();
		Map<String , TreeMap<String, Integer>> all = new TreeMap<>();
		TreeMap<String, Integer> aux;
		for (String linha : lines)
			Arrays.stream(linha.split("\\W+")).forEach(words::add);

		String last_word = "";
		for (String word: words) {
			
			word = word.toLowerCase();
			if (word.length() >= 3) {
				
				if (last_word.equals(""))
					last_word = word;
				else {
					
					if (all.containsKey(last_word)) {
						
						aux = all.get(last_word);
						if (aux.containsKey(word))
							aux.replace(word, aux.get(word)+1);
						else
							aux.put(word, 1);
					}
					else {
						
						aux = new TreeMap<>();
						aux.put(word, 1);
						all.put(last_word, aux);
					}
					last_word = word;
				}
			}
		}
		return all;
	}
}