import java.util.*;

public class E809 {

    public static void main(String[] args) {

        readChar();
    }

    public static void readChar() {

        Scanner k = new Scanner(System.in);

        String car;

        do {

            System.out.print("Coloca um caracter: ");
            car = k.nextLine();

            if (car.length() > 1 || car.isEmpty()) {

                System.out.println("Caracter não válido coloca outro.");
            }
        } while (car.length() > 1 || car.isEmpty());

        System.out.printf("O caractere %s é válido\n", car);
    }
}