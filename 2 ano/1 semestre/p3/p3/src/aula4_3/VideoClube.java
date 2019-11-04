package aula2_1;

import java.util.*;
import java.time.*;

public class VideoClube {
	
	private ListaSocios lista;
	private VideoList listaVideos;
	private Emprestimos listaEmprestimos;
	private Scanner sc;
	private ArrayList<Integer> delSocs;
	private int delIndSoc;
	private int maxLoans;
	
	public VideoClube(int l) {
	
		lista = new ListaSocios();
		listaVideos = new VideoList();
		listaEmprestimos = new Emprestimos();
		sc = new Scanner(System.in);
		delSocs = new ArrayList<Integer>();
		delIndSoc= 0;
		maxLoans = l;
	}
	
	public void addsoc() {
		
		System.out.print("Nome do sócio: ");
		String nm = sc.nextLine();
		  
		System.out.print("Data de Nascimento (dd/mm/yyyy): ");
		String data = sc.nextLine();
		String dof[] = data.split("/");
		if (dof.length < 3) {
			
			System.out.println("Falta um dado na data");
			return;
		}
		int dOf[] = {Integer.parseInt(dof[0]), Integer.parseInt(dof[1]), Integer.parseInt(dof[2])};
		 
		System.out.print("Data de inscrição (dd/mm/yyyy): ");
		data = sc.nextLine();
		String doi[] = data.split("/");
		if (doi.length < 3) {
			
			System.out.println("Falta um dado na data");
			return;
		}
		int dOi[] = {Integer.parseInt(doi[0]), Integer.parseInt(doi[1]), Integer.parseInt(doi[2])};
		  
		System.out.print("Número do cartão de cidadão: ");
		int cc = sc.nextInt();
		  
		int alORfu;
		do {
			
			System.out.print("Aluno(0) ou Funcionário(1): ");
			alORfu = sc.nextInt();
		} while (alORfu != 0 && alORfu != 1);
		  
		if (alORfu == 1) {
			
			System.out.printf("Número fiscal: ");
			int nFisc = sc.nextInt();
			System.out.printf("Número de funcionário: ");
			int nFunc = sc.nextInt();
			sc.nextLine();
				  
			lista.putFunc(new Funcionario(new Data(dOi[0], dOi[1], dOi[2]), new Data(dOf[0], dOf[1], dOf[2]), nm, cc, nFunc, nFisc, maxLoans));
		}
		else {
			
			sc.nextLine();
			System.out.printf("Curso: ");
			String course = sc.nextLine();
			System.out.printf("Número mecanográfico: ");
			int nmec = sc.nextInt();
			sc.nextLine();
				  
			lista.putAluno(new Aluno(new Data(dOi[0], dOi[1], dOi[2]), new Data(dOf[0], dOf[1], dOf[2]), nm, cc, nmec, maxLoans, course));
		}
		
		System.out.println("Sócio adicionado com sucesso.\n");
	}
	
	public void remsoc() {
		
		int alORfu;
		do {
			
			System.out.printf("Aluno(0) ou Funcionário(1): ");
			alORfu = sc.nextInt();
		}while(alORfu != 0 && alORfu != 1);
		
		System.out.printf("Número de Sócio: ");
		int ns = sc.nextInt();
		sc.nextLine();
		
		if(alORfu == 1)
		{
			lista.remFunc(ns);
			listaEmprestimos.remEmprestimosOfSoc(ns, listaVideos.movieAmount());
			delSocs.add(delIndSoc++, ns);
			System.out.printf("Membro removido com sucesso\n");
		}
		else if (alORfu == 0)
		{
			lista.remAl(ns);
			listaEmprestimos.remEmprestimosOfSoc(ns, listaVideos.movieAmount());
			delSocs.add(delIndSoc++, ns);
			System.out.printf("Membro removido com sucesso\n");			
		}
		else
		{
			System.out.println("Sócio não removido.\n");
		}
	}
	
	public void searchVid() {
		
		System.out.printf("Id do vídeo: ");
		int vID = sc.nextInt();
		sc.nextLine();
		listaVideos.video(vID);
	}  
	
	public void listVid() {
		
		int alORfu;
		do {
			
			System.out.print("Aluno(0); ou Funcionário(1): ");
			alORfu = sc.nextInt();
		} while (alORfu!= 0 && alORfu != 1);
		
		System.out.printf("Número de sócio: ");
		int soc = sc.nextInt();
		sc.nextLine();
		
		try {
			
			if (alORfu == 1) {
				
				Funcionario tmp = lista.getFunc(soc);
				int idade = (Year.now().getValue()) - (tmp.getBirthdate()).getYear();
				int m = Calendar.getInstance().get(Calendar.MONTH) + 1 - (tmp.getBirthdate()).getMonth();
				int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - (tmp.getBirthdate()).getDay();
				if (m < 0 || d < 0) {
					
					idade--;
				}
				listaVideos.videoDisplay(idade);
			}
			else {
				
				Aluno tmp = lista.getAluno(soc);
				int idade = (Year.now().getValue()) - (tmp.getBirthdate()).getYear();
				int m = Calendar.getInstance().get(Calendar.MONTH) + 1  - (tmp.getBirthdate()).getMonth();
				int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - (tmp.getBirthdate()).getDay();
				if (m < 0 || d < 0) {
					
					idade--;
				}
				listaVideos.videoDisplay(idade);
			}
		}
		catch(NullPointerException e) {
			
			System.out.println("Sócio não existente");
		}
	}  
	
	public void putVid() {
		
		System.out.printf("\nTítulo: ");
		String title = sc.nextLine();
		String cat, age;
		do {
			
			System.out.printf("Categoria (Ação, Comédia, Drama, Infantil): ");
			cat = sc.nextLine();
		} while(!cat.equals("Ação") && !cat.equals("Comédia") && !cat.equals("Infantil") && !cat.equals("Drama"));
		do {
			
			System.out.printf("Faixa etária (ALL, M6, M12, M16, M18): ");
			age = sc.nextLine();
		} while(!age.equals("ALL") && !age.equals("M6") && !age.equals("M12") && !age.equals("M16") && !age.equals("M18"));

		listaVideos.addVideo(new Video(title, cat, age));
		System.out.println("Video adicionado com sucesso.\n");
	}
	
	public void remVid() {
		
		System.out.print("Id do Vídeo: ");
		int vID = sc.nextInt();
		sc.nextLine();
		listaVideos.delVideo(vID);
		System.out.println("Vídeo removido com sucesso.\n");
	}
	
	public void canVid() {
		
		System.out.print("Id do vídeo: "); 
		int vID = sc.nextInt();
		sc.nextLine();
		listaVideos.videoDisponivel(vID);
	}
	
	public void loanVid() {
		
		System.out.printf("Id do vídeo a levantar: ");
		int vID = sc.nextInt();
		Video tmp = listaVideos.getVideoById(vID);
		
		int alORfu;
		do {
			
			System.out.print("Aluno(0) ou Funcionário(1): ");
			alORfu = sc.nextInt();
		} while(alORfu != 1 && alORfu != 0);
		
		System.out.printf("Id de sócio: ");
		int socID = sc.nextInt();
		sc.nextLine();
		
		System.out.printf("Data de empréstimo(dd/mm/yyyy): ");
		String dat = sc.nextLine();
		String data[] = dat.split("/");
		if(data.length < 3) {
			
			System.out.println("Faltam dados na data.");
			return;
		}
		
		if(alORfu == 1 && lista.containsFunSoc(socID)) {
			
			Funcionario tmpf = lista.getFunc(socID);
			if (tmpf.newLoan())
				listaEmprestimos.newLoanFunc(new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])), tmpf, tmp);
			else  {
				
				System.out.printf("Já atingiu o limite de empréstimos.\n");
				return;
			}
		}
		else if (lista.containsAlunSoc(socID)) {
			
			Aluno tmpf = lista.getAluno(socID);
			if (tmpf.newLoan())
				listaEmprestimos.newLoanAluno(new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])), tmpf, tmp);
			else {
				
				System.out.printf("Já atingiu o limite de empréstimos.\\n");
				return;
			}
		}
		
		System.out.println("Empréstimo feito com sucesso.\n");
	}
	
	public void retVideo() {
		
		System.out.printf("Id do vídeo a depositar: ");
		int vID = sc.nextInt();		
		Video tmp;
		try {
			
			tmp = listaVideos.getVideoById(vID);
		}
		catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("Nenhum filmedom esse ID.\n");
			return;
		}
		
		int alORfu;
		do {
			
			System.out.print("Aluno(0) ou Funcionário(1): ");
			alORfu = sc.nextInt();
		} while(alORfu != 1 && alORfu != 0);
		
		System.out.printf("Id de sócio: ");
		int socID = sc.nextInt();
		sc.nextLine();
		
		System.out.printf("Data de entrega(dd/mm/yyyy): ");
		String dat = sc.nextLine();
		String data[] = dat.split("/");
		if (data.length < 3) {
			
			System.out.println("Falta um dado na data.\n");
			return;
		}
			
		int rating;
		do {
			
			System.out.printf("Rating (1-10): ");
			rating = sc.nextInt();
		}while (rating < 1 || rating > 10);
		if(alORfu == 1) {
			
			Funcionario tmpf = lista.getFunc(socID);
			tmpf.returnvideo();
			listaEmprestimos.returnVideoFunc(new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])), tmpf, tmp, rating);
		}
		else {
			
			Aluno tmpa = lista.getAluno(socID);
			tmpa.returnvideo();
			listaEmprestimos.returnVideoAluno(new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])), tmpa, tmp, rating);
		} 
		
		System.out.println("Entrega de filme com sucesso.\n");
	}
	
	public void checkReq() {
		
		System.out.printf("Id do video: ");
		int vID = sc.nextInt();
		sc.nextLine();
		try
		{		  
			int socios = lista.lastSocNumb();
			int users[] =listaEmprestimos.getVideoHistory(socios, vID, delSocs);
			
			if (users.length == 0) {
				
				System.out.printf("Nenhum utilizador viu o filme %s.\n\n",(listaVideos.getVideoById(vID)).getTitle());
				return;
			}
			
			System.out.printf("Utilizadores que viram o filme %s:\n", (listaVideos.getVideoById(vID)).getTitle());
			for (socios = 0; socios < users.length; socios++) {
				
				if(lista.containsAlunSoc(users[socios])) {
					
					System.out.println((lista.getAluno(users[socios])).toString());
				}
				else if (lista.containsFunSoc(users[socios])) {
					
					System.out.println((lista.getFunc(users[socios])).toString());
				}
				else {
					
					System.out.println();
				}
			}
			
			System.out.println();
		}
		catch (IndexOutOfBoundsException e) {
			
			System.out.println("Filme não existente.");
		}
	}
	
	public void showAll() {
		
		listaVideos.videoDisplay(18);
	}
	
	public void showRated() {
		
		listaVideos.showRated();
	}
	
	public void checkSocHist() {
		
		System.out.printf("Id do sócio: ");
		int soc = sc.nextInt();
		
		try {
			
			int[] movieIDs = listaEmprestimos.getSocHistory(soc, listaVideos.movieAmount());
			if (movieIDs.length == 0) {
				
				System.out.printf("Sócio com id %d não requesitou nenhum filme.\n", soc);
				return;
			}
			
			System.out.printf("O sócio %d requesitou %s: \n", soc, movieIDs.length == 1 ? "o seguinte filme" : "os seguintes filmes");
			for(int i = 0; i < movieIDs.length; i++) {
				
				if (listaVideos.getVideoById(movieIDs[i]) != null)
					System.out.print(listaVideos.getVideoById(movieIDs[i]));
			}
			
		}
		catch (IndexOutOfBoundsException e) {
			
			System.out.println("Sócio não encontrado.\n");
		}
		sc.nextLine();
	}
}
