import java.util.*;

public class E806 {

    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);

        String frase;
        String traduzida;

        System.out.print("Frase a traduzir -> ");
        frase = k.nextLine();

        traduzida = tradutor(frase);

        System.out.printf("Frase traduzida -> %s\n", traduzida);
    }

    //modulo de tradução
    public static String tradutor(String pre) {

        pre = pre.replace('l', 'u');
        pre = pre.replace('L', 'U');
        pre = pre.replace('R', ' ');
        pre = pre.replace('r', ' ');

        return pre;
    }
}