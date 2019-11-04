import java.util.*;

public class exe1_2 {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);
		double PG1, PG2, EF, TP1, NF;
		
		System.out.print("Digite a nota de PG1: ");
		PG1=sc.nextDouble();
		
		System.out.print("Digite a nota de PG2: ");
		PG2=sc.nextDouble();
		
		System.out.print("Digite a nota de TP1: ");
		TP1=sc.nextDouble();
		
		System.out.print("Digite a nota de EF: ");
		EF=sc.nextDouble();
		
		NF=0.15*PG1+0.15*PG2+0.2*TP1+0.5*EF;
		
		System.out.printf("A nota de PG1 é: %2.2f \n", PG1);
		System.out.printf("A nota de PG2 é: %2.2f \n", PG2);
		System.out.printf("A nota de TP1 é: %2.2f \n", TP1);
		System.out.printf("A nota de EF é: %2.2f \n", EF);
		
		System.out.printf("A NF é %2.2f*0.15 + %2.2f*0.15 + %2.2f*0.2 + %2.2f*0.5 \n", PG1, PG2, TP1, EF);
		System.out.printf("A NF é: %2.2f", NF);
		
	}
}

