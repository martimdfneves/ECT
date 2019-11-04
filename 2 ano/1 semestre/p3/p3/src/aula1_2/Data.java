package aula1_2;

import java.util.*;

public class Data {

	private int dia;
	private int mes;
	private int ano;
	private int daysInMonths[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};	
	
	public Data(int d, int m , int a) {
		
		validData(d, m, a);
		dia = d;
		mes = m;
		ano = a;
	}
	
	public Data() {
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		this.dia = calendar.get(Calendar.DATE);
		this.mes = calendar.get(Calendar.MONTH) + 1;
		this.ano = calendar.get(Calendar.YEAR);
	}
	
	public String toString() {
		
		return dia + "/" + mes + "/" + ano;
	}
	
	public int getDay() {
		
		return dia;
	}
	
	public int getMonth() {
		
		return mes;
	}
	
	public int getYear() {
		
		return ano;
	}
	
	private void validData(int dia, int mes, int ano) {
		
		if (mes < 0 || mes > 12) {
			
			throw new RuntimeException();
		}
		
		else if (leapYear() && daysInMonths[mes-1] < dia || dia < 0) {
			
			throw new RuntimeException();
		}
		
		else if (!leapYear() && mes != 2 && daysInMonths[mes-1] < dia || dia < 0 || daysInMonths[1] - 1 < dia ) {
			
			throw new RuntimeException();
		}
	}
	
	private boolean leapYear() {
		
		return ((ano % 4 == 0 && ano % 100 == 0) || ano % 400 == 0);
	}
}
