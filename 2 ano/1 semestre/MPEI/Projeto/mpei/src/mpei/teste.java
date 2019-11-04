package mpei;

import java.io.IOException;

public class teste {

	public static void main(String[] args) throws IOException{
		
	
	Armazem xpto= new Armazem(1000,3,10);
	xpto.addPc("Pc001.txt");
	xpto.addPc("Pc001.txt");
	xpto.addPc("Pc001.txt");
	xpto.checkStock("Pc001.txt");
	xpto.checkStock("Pc002.txt");
	xpto.removePc("Pc001.txt");
	xpto.checkStock("Pc001.txt");
	xpto.compare("Pc001.txt","Pc020.txt");
	System.out.print("\n");
	System.out.print("\n");
	
	Armazem teste2= new Armazem(1000,10,27);
	teste.checkStock("Pc001.txt");
	teste.removePc("Pc001.txt");
	teste.addPc("Pc001.txt");
	teste.addPc("Pc001.txt");
	teste.addPc("Pc001.txt");
	teste.checkStock("Pc001.txt");
	teste.checkStock("Pc002.txt");
	teste.removePc("Pc001.txt");
	teste.checkStock("Pc001.txt");
	teste.compare("Pc001.txt","Pc020.txt");
	System.out.print("\n");
	System.out.print("\n");
	
	Armazem teste1= new Armazem(1000,30,40);
	teste1.addPc("Pc001.txt");
	teste1.addPc("Pc001.txt");
	teste1.addPc("Pc001.txt");
	teste1.checkStock("Pc001.txt");
	teste1.checkStock("Pc002.txt");
	teste1.removePc("Pc001.txt");
	teste1.checkStock("Pc001.txt");                     //para demonstrar que a distancia de jaccard 
	teste1.compare("Pc001.txt","Pc020.txt");            //varia dependendo dos parametros
	
	}
}
