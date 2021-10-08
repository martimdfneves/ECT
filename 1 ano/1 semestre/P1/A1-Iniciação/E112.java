import java.util.*;

public class E112 {

    public static void main(String[] args) {

        //declaração das variáveis
        int horas, minutos, segundos;

        //declaração do teclado
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Qual o tempo em segundos: ");
        segundos = keyboard.nextInt();

        //cálculo de cada variável
        //nota: % -> resto da divisão
        horas = segundos / 3600;
        minutos = (segundos % 3600) / 60;
        segundos = segundos % 60;

        System.out.printf("Tempo inserido: %2d/%2d/%2d\n", horas, minutos, segundos);

    }
}
