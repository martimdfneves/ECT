package aula3_1;

import aula1_2.*;
import java.util.*;

public class Estudante extends Pessoa {
	
	private int nalunos=100;
	private int nmec;
	private Data dinscricao;
	
	public Estudante (int cardnumber, Data date, String name, Data dinscricao) {
		
		super(cardnumber, date, name);
		nmec=nalunos++;
		dinscricao=date;
	}
	
	public Estudante(int cardnumber, Data date, String name) {
		
		this(cardnumber, date, name, new Data(Calendar.DAY_OF_MONTH, Calendar.MONTH+1, Calendar.YEAR));
	}	

	public int getNalunos() {
		
		return nalunos;
	}

	public int getNmec() {
		
		return nmec;
	}

	public Data getDinscricao() {
		
		return dinscricao;
	}
	
	@Override
	public String toString() {
		
		return super.getName() + ", BI: " + super.getNumber() + ", Nasc.Data:" + super.getDate() +
				", NMec: " + nmec + ", inscrito em Data: " + dinscricao.toString();
	}
}
