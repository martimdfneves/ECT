package aula13_2;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
		Map<String, TreeMap<String, Integer>> mapa = ScanCont.readFile("Policarpo.txt");
		for (String key : mapa.keySet()) {
			
			System.out.print(key + "={");
			Iterator<Entry<String, Integer>> iter = mapa.get(key).entrySet().iterator();
			while (iter.hasNext()) {
				
				Entry<String, Integer> entry = iter.next();
				if (!iter.hasNext())
					System.out.print(entry.getKey() + "=" + entry.getValue() + "}");
				else
					System.out.print(entry.getKey() + "=" + entry.getValue() + ", ");
			}
			System.out.println();
		}
	}
}