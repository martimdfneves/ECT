import java.util.*;

import static java.lang.Math.*;

public class E704 {

    static Scanner k = new Scanner(System.in);

    public static void main(String[] args) {

        Complex n1 = new Complex();
        Complex n2 = new Complex();
        Complex rez = new Complex();

        char oper;

        do {
            System.out.print("Operação: ");
            oper = k.next().charAt(0);

            switch (oper) {

                case '+':

                    //leitura
                    n1 = lerComplex();
                    n2 = lerComplex();
                    rez = addComp(n1, n2);

                    //impressão
                    printComp(n1);
                    System.out.print(" + ");
                    printComp(n2);
                    System.out.print(" = ");
                    printComp(rez);
                    System.out.println();
                    break;

                case '-':

                    //leitura
                    n1 = lerComplex();
                    n2 = lerComplex();
                    rez = subComp(n1, n2);

                    //impressão
                    printComp(n1);
                    System.out.print(" - ");
                    printComp(n2);
                    System.out.print(" = ");
                    printComp(rez);
                    System.out.println();
                    break;

                case '/':

                    //leitura
                    n1 = lerComplex();
                    n2 = lerComplex();
                    rez = divComp(n1, n2);

                    //impressão
                    printComp(n1);
                    System.out.print(" / ");
                    printComp(n2);
                    System.out.print(" = ");
                    printComp(rez);
                    System.out.println();
                    break;

                case '*':

                    //leitura
                    n1 = lerComplex();
                    n2 = lerComplex();
                    rez = multComp(n1, n2);

                    //impressão
                    printComp(n1);
                    System.out.print(" * ");
                    printComp(n2);
                    System.out.print(" = ");
                    printComp(rez);
                    System.out.println();
                    break;

                case '=':

                    System.out.println("O pragrama vai terminar...");
                    break;

                default:

                    System.out.println("Opção não permitida!");
                    break;

            }
        } while (oper != '=');
    }

    //função para ler o complexo
    public static Complex lerComplex() {

        Complex num = new Complex();

        System.out.println("Introduza um número complexo:");

        //leitura da parte real
        System.out.print("Parte real: ");
        num.real = k.nextDouble();

        //leitura da parte imaginária
        System.out.print("Parte imaginária: ");
        num.img = k.nextDouble();

        return num;
    }

    //impressor do numero
    public static void printComp(Complex num) {

        if (num.img >= 0) {

            System.out.printf("%4.2f + %4.2fi", num.real, num.img);

        } else if (num.img < 0) {

            System.out.printf("%4.2f %4.2fi", num.real, num.img);
        }
    }

    //modulo da adiçao
    public static Complex addComp(Complex n1, Complex n2) {

        Complex rex = new Complex();

        rex.real = n1.real + n2.real;
        rex.img = n1.img + n2.img;

        return rex;
    }

    //modulo da subtração
    public static Complex subComp(Complex n1, Complex n2) {

        Complex rex = new Complex();

        rex.real = n1.real - n2.real;
        rex.img = n1.img - n2.img;

        return rex;
    }

    //modulo da divisao
    public static Complex divComp(Complex n1, Complex n2) {

        Complex rex = new Complex();

        rex.real = ((n1.real * n2.real) + (n1.img * n2.img)) / (pow(n2.real, 2) + pow(n2.img, 2));
        rex.img = ((n1.img * n2.real) - (n1.real * n2.img)) / (pow(n2.real, 2) + pow(n2.img, 2));

        return rex;
    }

    //modulo da multiplicação
    public static Complex multComp(Complex n1, Complex n2) {

        Complex rex = new Complex();

        rex.real = (n1.real * n2.real) - (n1.img * n2.img);
        rex.img = (n1.img * n2.real) + (n1.real * n2.img);

        return rex;
    }


}

class Complex {

    double real;
    double img;
}