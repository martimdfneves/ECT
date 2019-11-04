import java.util.*;

public class exe1_3 {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);
		int num, cont=0;
		
		System.out.print("Digite um número: ");
		num=sc.nextInt();
		
		for (int i = 1; i <= num; i++) {
			if (num%i==0) {
				cont++;
			}
				
		}
		
		if (cont>2) {
			System.out.print("O número não é primo");
		}
		
		else {
			System.out.print("O número é primo");
		}
		
	}
}

