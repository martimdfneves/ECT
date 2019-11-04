package aula2_1;

import java.util.*;

public class E1
{
  static final Scanner sc = new Scanner(System.in);
  public static void main(String args[])
  {

	  VideoClube v = new VideoClube(5);
	  int option = -1;
	  do {
		  try
		  {
			  menu();
			  option = sc.nextInt();
			  switch(option) {
				  case 0:
					  System.out.println("Adeus.\n");
					  break;
				  case 1:
					  v.addsoc();
					  break;
				  case 2:
					  v.remsoc();
					  break;
				  case 3:
					  v.searchVid();
					  break;
				  case 4:
					  v.listVid();
					  break;
				  case 5:
					  v.putVid();
					  break;
				  case 6:
					  v.remVid();
					  break;
				  case 7:
					  v.canVid();
					  break;
				  case 8:
					  v.loanVid();
					  break;
				  case 9:
					  v.retVideo();
					  break;
				  case 10:
					  v.checkReq();
					  break;
				  case 11:
					  v.showAll();
					  break;
				  case 12:
					  v.showRated();
					  break;
				  case 13:
					  v.checkSocHist();
					  break;
				  default:
					  System.out.println("Não é uma opção");
					  break;
			  }
		  }
		  catch (InputMismatchException e)
		  {
			  System.out.print("Erro na última entrada.\n");
			  sc.nextLine();
		  }
		  catch (NumberFormatException e)
		  {
			  System.out.print("Colocadasletras onde se esperavam números na última entrada.\n");
			  sc.nextLine();
		  }
		  catch (ArrayIndexOutOfBoundsException e)
		  {
			  System.out.println("Erro no acesso pretendido.\n");
		  }
	  } while(option != 0);
  }
  
  public static void menu()
  {
	  System.out.printf("|-------------------------------------------------------------------|\n");
	  System.out.printf("|                                Menu                               |\n");
	  System.out.printf("|-------------------------------------------------------------------|\n");
	  System.out.printf("|   1  -> Introduzir novo Sócio                                     |\n");
	  System.out.printf("|   2  -> Remover sócio por número de sócio                         |\n");
	  System.out.printf("|   3  -> Pesquisar vídeo pelo Id                                   |\n");
	  System.out.printf("|   4  -> Listar todos os vídeos acessíveis ao utilizador           |\n");
	  System.out.printf("|   5  -> Introduzir novo vídeo                                     |\n");
	  System.out.printf("|   6  -> Remover vídeo pelo Id                                     |\n");
	  System.out.printf("|   7  -> Verificar se o vídeo pretendido está disponível           |\n");
	  System.out.printf("|   8  -> Efetuar empréstimo de um vídeo                            |\n");
	  System.out.printf("|   9  -> Devolver um vídeo                                         |\n");
	  System.out.printf("|  10  -> Ver quem requesitou um vídeo                              |\n");
	  System.out.printf("|  11  -> Ver catálogo de Filmes                                    |\n");
	  System.out.printf("|  12  -> Ver catálogo de Filmes por Rating                         |\n");
	  System.out.printf("|  13  -> Ver os filmes repositados por um Sócio                    |\n");
	  System.out.printf("|   0  -> Sair do programa                                          |\n");
	  System.out.printf("|-------------------------------------------------------------------|\n");
	  System.out.printf("\nOpção: ");
  }
}