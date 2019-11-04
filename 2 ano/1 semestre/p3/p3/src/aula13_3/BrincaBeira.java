package aula13_3;

import java.util.Arrays;

public class BrincaBeira {
	
	public static void main(String args[]) {
		
		System.out.println("------ Testing NameList ------");

		String[] workers = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
		String[] toys = {"Carro", "Comboio a vapor", "Darth Vader - Boneco de Ação"};
		EmpReg names = new EmpReg();

		Arrays.stream(workers).forEach(x -> names.add(x));

		System.out.println(names);

		System.out.println("\n------ Testing WorkerGifts (Worker,Gift) ------");
		EmpGifts gifts = new EmpGifts();
		System.out.println("\nGiving gifts...\n");
		for(int i = 0; i < 12; i++) {
			
			int randWorkerIndex = (int)(Math.random() * workers.length);
			int randGiftIndex = (int)(Math.random() * toys.length);
			gifts.give(toys[randGiftIndex],workers[randWorkerIndex]);
			System.out.println("'"+ toys[randGiftIndex] +"' given to "+workers[randWorkerIndex]);
		}
		System.out.println("\nPrinting lists of gifts...\n");
		System.out.println(gifts);
		System.out.println("\nTaking back gifts...\n");

		for(String worker : workers)
			gifts.get(worker);

		System.out.println("\nPrinting lists of gifts...\n");
		System.out.println(gifts);

		System.out.println("\n------ Testing ToyList ------");
		String randomWorker = workers[(int)(Math.random() * workers.length)];
		Toys toyList = new Toys(randomWorker);

		Arrays.stream(toys).forEach(x -> toyList.add(x));
		System.out.println(toyList);
		System.out.println(toyList);
		toyList.remove("Carro");
		toyList.remove("Barbie");

		System.out.println("\n------ Testing Name Counter ------");
		EmpReg counter = new EmpReg();

		Arrays.stream(workers).forEach(x -> counter.add(x));
		System.out.println(counter);
		counter.remove("Andre"); 
		counter.remove("Joana"); 
		counter.remove("Joana");
		System.out.println(counter);

		System.out.println("\n------ Testing Ticket Manager ------\n");

		SeasonPasses manager = new SeasonPasses(workers);
		System.out.println("Given tickets to: \n");
		manager.give(4).forEach(System.out::println);
		System.out.println("\nGiven tickets to: \n");
		manager.give(3).forEach(System.out::println);
		System.out.println("\nGiven tickets to: \n");
		manager.give(1).forEach(System.out::println);
		System.out.println("\nGiven tickets to: \n");

		manager.give(10).forEach(System.out::println);
	}
}