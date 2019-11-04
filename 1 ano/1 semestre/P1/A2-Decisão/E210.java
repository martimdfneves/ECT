import java.util.Scanner;

public class E210 {

  //declaração do teclado
  static Scanner k = new Scanner(System.in);

  public static void main(String[] args) {

    //declaração das variáveis
    int dia, mes, ano, nd, nm, ny, pd, pm, py;

    //interrogatório para obter o dia
    System.out.println("Coloque a data da maneira especificada.");
    System.out.print("Dia :");
    dia = k.nextInt();
    System.out.print("Mes: ");
    mes = k.nextInt();
    System.out.print("Ano: ");
    ano = k.nextInt();

    //declaração do valor das dependentes
    nd = dia + 1;
    pd = dia - 1;
    pm = nm = mes;
    py = ny = ano;

    //caso muda pro mes anterior
    if (pd == 0) {

      pm = mes - 1;

      //caso mude pro ano anterior
      if (pm == 0) {

        pd = 31;
        pm = 12;
        py = ano - 1;

      } else if (pm == 1 || pm == 3 || pm == 5 || pm == 7 || pm == 8 || pm == 10 || pm == 12) {

        pd = 31;

      } else if (pm == 4 || pm == 6 || pm == 9 || pm == 11) {

        pd = 30;

      } else if (pm == 2) {

        //caso o mes anterior seja febreiro
        if (ano % 4 == 0 && ano % 100 == 0) {

          pd = 29;

        } else {

          pd = 28;

        }
      }
    }

    //caso mude pro mes seguinte
    if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {

      if (nd == 32) {

        nd = 1;
        nm += 1;

        if (mes == 12) {

          ny += 1;

        }
      }
    } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {

      if (nd == 31) {
        
        nd = 1;
        nm += 1;
      }
    } else if (mes == 2) {
      
      if (ano % 4 == 0 && ano % 100 == 0) {
        
        if (nd == 29) {
           
           nd = 1;
           nm = 3;

        }
      } else {

        if (nd == 28) {
          
          nd = 1;
          nm = 3;

        }
      }
    }

    System.out.println("A data anterior é " + pd + "/" + pm + "/" + py + " e a data posterior é " + nd + "/" + nm + "/" + ny + "." );
  }
}
