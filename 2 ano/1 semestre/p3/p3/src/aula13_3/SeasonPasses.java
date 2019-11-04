package aula13_3;

import java.util.ArrayList;
import java.util.List;

public class SeasonPasses {

		private int i;
		private String employees[];

		public SeasonPasses(String employees[]) {
			
			i = 0;
			this.employees = employees;
		}

		public List<String> give(int amount) {
			
			List<String> getters = new ArrayList<>();
			for (int j = 0; j < amount; j++)
				getters.add(give());

			return getters;
		}

		private String give() {
			
			i = (i + 1) % employees.length;
			return employees[i];
		}
}