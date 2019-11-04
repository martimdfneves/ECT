import pt.ua.prog2.*;
import static java.lang.System.*;

public class DatasPassadas {

  public static void main(String[] args) {
    Data atual = new Data();
    
    Data d2 = new Data(25, 12, 2017);
    System.out.printf("%s", d2.extenso());
    
    do {
		
		d2.seguinte();
		System.out.printf("%s", d2.extenso());
		
	} while ((atual.val_dia()!=d2.val_dia())||(atual.val_mes() != d2.val_mes()) || (atual.val_ano() != d2.val_ano()));
	
  }

}

