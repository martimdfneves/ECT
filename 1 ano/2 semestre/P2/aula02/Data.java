import static java.lang.System.*;
import java.util.Calendar;

public class Data {
  private int dia, mes, ano;

  /** Inicia esta data com o dia de hoje. */
  public Data() {
    Calendar today = Calendar.getInstance();

    dia = today.get(Calendar.DAY_OF_MONTH);
    mes = today.get(Calendar.MONTH) + 1;
    ano = today.get(Calendar.YEAR);
  }

  /** Inicia a data a partir do dia, mes e ano dados. */
  public Data(int dia, int mes, int ano) {
    this.dia=dia;
    this.mes=mes;
    this.ano=ano;
  }

  /** Devolve esta data segundo a norma ISO 8601. */
  public String toString() {
    return String.format("%04d-%02d-%02d", ano, mes, dia);
  }

  /** Indica se ano é bissexto. */
  public static boolean bissexto(int ano) {
    return ano%4 == 0 && ano%100 != 0 || ano%400 == 0;
  }

  public int val_dia() {
	return dia;  
  }
  
  public int val_mes() {
	return mes;  
  }
  
  public int val_ano() {
	return ano;  
  }
  
  /** Dimensões dos meses num ano comum. */
  private static final
  int[] diasMesComum = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
  
  private static final
  int[] diasMesBissexto = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  /** Devolve o número de dias do mês dado. */
  public static int diasDoMes(int mes, int ano) {
    int d=0;
    
    if (mes==2 && bissexto(ano)==true) {
		d=29;
	}
	else if (mes==2 && bissexto(ano)==false) {
		d=28;
	}
	else if (mes==1||mes==3||mes==5||mes==7||mes==8||mes==10||mes==12){ 
		d=31;
	}
	else {
		d=30;
	}
	
    return d;
  }

  /** Devolve o mes da data por extenso. */
  public String mesExtenso() {
	  
	  String me;
	  
    switch (mes) {
		case 1: me="Janeiro";
			break;
		case 2: me="Fevereiro";
			break;
		case 3: me="Março";
			break;
		case 4: me="Abril";
			break;
		case 5: me="Maio";
			break;
		case 6: me="Junho";
			break;
		case 7: me="Julho";
			break;
		case 8: me="Agosto";
			break;
		case 9: me="Setembro";
			break;
		case 10: me="Outubro";
			break;
		case 11: me="Novembro";
			break;
		case 12: me="Dezembro";
			break;
		default:
			me="Mês inválido!";
	}
	return me;
  }

  /** Devolve esta data por extenso. */
  public String extenso() {
    return dia + " de " + mesExtenso() + " de " + ano + "\n";
  }

  /** Indica se um terno (dia, mes, ano) forma uma data válida. */
  public static boolean dataValida(int dia, int mes, int ano) {
	  
	boolean val=false;  
	  
    if (mes==2 && bissexto(ano)==true) {
		if (dia>0&&dia<30) {
			val=true;
		}
		else {
			val=false;
		}
		
	}
	if (mes==2 && bissexto(ano)==false) {
		if (dia>0&&dia<29) {
			val=true;
		}
		else {
			val=false;
		}
		
	}
	if (mes==1||mes==3||mes==5||mes==7||mes==8||mes==10||mes==12){
		if (dia>0&&dia<32) {
			val=true;
		}
		else {
			val=false;
		}
		
	}
	if (mes==4||mes==6||mes==9||mes==11){
		if (dia>0&&dia<31) {
			val=true;
		}
		else {
			val=false;
		}
		
	}
	
	return val;
  }


  public void seguinte() {
    if (mes==1 && dia==31) {
		dia=1;
		mes=2;
	}
	else if (mes==5 && dia==31) {
		dia=1;
		mes=6;
	}
	else if (mes==7 && dia==31) {
		dia=1;
		mes=8;
	}
	else if (mes==8 && dia==31) {
		dia=1;
		mes=9;
	}
	else if (mes==10 && dia==31) {
		dia=1;
		mes=11;
	}
	else if (mes==12 && dia==31) {
		dia=1;
		mes=1;
		ano=ano+1;
	}
	else if (mes==4 && dia==30) {
		dia=1;
		mes=5;
	}
	else if (mes==6 && dia==30) {
		dia=1;
		mes=7;
	}
	else if (mes==9 && dia==30) {
		dia=1;
		mes=10;
	}
	else if (mes==11 && dia==30) {
		dia=1;
		mes=12;
	}
	else if (mes==3 && dia==31) {
		dia=1;
		mes=4;
	}
	else if (mes==2 && dia==29 && bissexto(ano)==true) {
		dia=1;
		mes=3;
	}
	else if (mes==2 && dia==28 && bissexto(ano)==false) {
		dia=1;
		mes=3;
	}
	else {
		dia=dia+1;
	}
	
  }


}

