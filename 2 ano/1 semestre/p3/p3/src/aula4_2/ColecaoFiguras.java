package aula4_2;

import aula3_2.*;
import java.util.*;

public class ColecaoFiguras {
	
	private double maxArea;
	private double currentArea;
	private ArrayList<Figura> colecao;
	
	public ColecaoFiguras(double maxArea) {
		
		colecao = new ArrayList<Figura>();
		this.maxArea = maxArea;
		currentArea = 0;
	}
	
	public boolean addFigura(Figura f) {
		
		if ((f.area() + currentArea > maxArea) || colecao.contains(f))
			return false;
		
		else {
			
			currentArea += f.area();
			colecao.add(f);
			return true;
		}	
	}
	
	public boolean delFigura(Figura f) {
		
		currentArea -= f.area();
		return colecao.remove(f);
	}
	
	public double areaTotal() {
		
		return currentArea;
	}
	
	public boolean exists(Figura f) {
		
		return colecao.contains(f);
	}
	
	@Override
	public String toString() {
		
		return "Coleção com: " +currentArea + " figuras e " + currentArea + " de area total.";
	}
	
	public Figura[] getFiguras() {
		
		return colecao.toArray(new Figura[0]);
	}
	
	public Ponto[] getCentros() {
		
		ArrayList<Ponto> centros = new ArrayList<Ponto>();
		Iterator<Figura> iter = colecao.iterator();
		while (iter.hasNext())
		{
			Figura f = iter.next();
			centros.add(f.getCentre());
		}
		
		return centros.toArray(new Ponto[0]);
	}
}