package aula4_1;

import java.util.Arrays;

import aula3_1.*;

public class Disciplina{
	
	private String nomeD;
	private String area;
	private int creditos;
	private Professor Responsavel;
	private Estudante[] Alunos;
	private int inscritos=0;
	
	public Disciplina(String nomeD, String area, int creditos,Professor responsavel, Estudante[] alunos) {

		this.nomeD = nomeD;
		this.area = area;
		this.creditos = creditos;
		Responsavel = responsavel;
		Alunos[inscritos] = alunos[inscritos];

	}
	
	public Disciplina(String nomeD, String area, int creditos,Professor responsavel) {

		this.nomeD = nomeD;
		this.area = area;
		this.creditos = creditos;
		Responsavel = responsavel;

	}

	public String getNomeD() {
		
		return nomeD;
	}

	public String getArea() {
		
		return area;
	}

	public int getCreditos() {
		
		return creditos;
	}

	public Professor getResponsavel() {
		
		return Responsavel;
	}

	public Estudante[] getAlunos() {
		
		return Alunos;
	}

	@Override
	public String toString() {
		
		return "Disciplina [Nome da Disciplina=" + nomeD + ", Área Científica =" + area + ", ECTS=" + creditos + ", Responsável da Disciplina="
				+ Responsavel + ", Alunos=" + Arrays.toString(Alunos) + "]";
	}
	
	public boolean addAluno(Estudante est) {
		
		for (int i=0;i<Alunos.length;i++) {
			
			if(Alunos[i]==est) 
			
			{System.out.println("Aluno já existente!");
			return false;}
		}
		
		Alunos[inscritos]=est;
		inscritos++;
		return true;
	}
	
	public boolean delAluno(int nMec) {
		
		for (int i=0;i<Alunos.length;i++) {
			
			if(Alunos[i].getNmec()==nMec) 
			
			{Alunos[i+1]=Alunos[i];
			inscritos--;
			}
			
			else 
				
			{System.out.println("Estudante não existente!");
			return false;
			}
		}
		return true;
	}
	
	public boolean alunoInscrito(int nMec) {
		
		for(int i=0;i<Alunos.length;i++) {
			
			if(Alunos[i].getNmec()==nMec) {return true;}
		}
		return false;
	}
	
	public int numAlunos() {
		
		return Alunos.length;
	}
	
	public Estudante[] getAlunos(String tipo) {
		
		
	}
}