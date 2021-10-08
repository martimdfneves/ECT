import java.util.*;

public class E114 {

    public static void main(String[] args) {

        //declaração do teclado
        Scanner keyboard = new Scanner(System.in);

        //declaração das variáveis
        double c1, c2, hip, angle;

        System.out.print("Qual o valor do cateto A? ");
        c1 = keyboard.nextDouble();
        System.out.print("Qual o valor do cateto B? ");
        c2 = keyboard.nextDouble();

        hip = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));

        angle = Math.toDegrees(Math.acos(c1 / hip));

        System.out.printf("A hipotenusa do triângulo é %4.2f e o ângulo que esta faz com o cateto A é de %4.2fº.\n", hip, angle);
    }
}
