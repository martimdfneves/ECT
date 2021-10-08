import java.util.*;

public class E1101 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        //indices da primeira matriz
        int il1, ic1;

        //indices da segunda matriz
        int il2, ic2;

        do {
            //pede primeira matriz
            System.out.println("Índices da primeira matriz:");
            System.out.print("Linhas: ");
            il1 = k.nextInt();
            System.out.print("Colunas: ");
            ic1 = k.nextInt();

            //pede segunda matriz
            System.out.println("Índices da segunda matriz:");
            System.out.print("Linhas: ");
            il2 = k.nextInt();
            System.out.print("Colunas: ");
            ic2 = k.nextInt();

            if (ic1 != il2) {

                System.out.println("Matrizes não multiplicáveis.");
            }
        } while (ic1 != il2);

        //matrizes
        int[][] matriz1 = new int[il1][ic1];
        int[][] matriz2 = new int[il2][ic2];

        //lê a matriz 1
        System.out.println("\nPrimeira matriz:");
        matriz1 = leitorDeMatrizes(il1, ic1);

        //lê a matriz 2
        System.out.println("\nSegunda matriz");
        matriz2 = leitorDeMatrizes(il2, ic2);

        int[][] finla = new int[il1][ic2];

        finla = multiplicador(matriz1, matriz2, il1, il2, ic1, ic2);

        mostraMatriz(finla);

    }

    //leitor das matrizes
    public static int[][] leitorDeMatrizes(int il, int ic) {

        int[][] matriz = new int[il][ic];

        for (int i = 0; i < il; i++) {

            for (int j = 0; j < ic; j++) {

                System.out.printf("a%d%d: ", i + 1, j + 1);
                matriz[i][j] = k.nextInt();
            }
        }

        return matriz;
    }

    //multiplicador das matrizes
    public static int[][] multiplicador(int[][] matriz1, int[][] matriz2, int il1, int il2, int ic1, int ic2) {

        int[][] matrizFinal = new int[il1][ic2];
        int rez = 0;

        //linha n da matriz final
        for (int a = 0; a < il1; a++) {
            //coluna n da matriz final
            for (int b = 0; b < ic2; b++) {
                for (int i = 0; i < il2; i++) {
                    rez += matriz1[a][i] * matriz2[i][b];
                }
                matrizFinal[a][b] = rez;
                rez = 0;
            }
        }

        return matrizFinal;
    }

    //impressor de matrizes
    public static void mostraMatriz(int[][] matriz) {

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {

                System.out.printf("%d\t", matriz[i][j]);
            }
            System.out.println();
        }
    }
}