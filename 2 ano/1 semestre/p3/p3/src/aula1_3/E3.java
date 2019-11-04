package aula1_3;

public class E3 {

	public static void main(String[] args) {
		
		Circulo circle[] = new Circulo[5];
		quadrado quad = new quadrado(4, new Ponto(2, 3));
		retangulo ret = new retangulo(10, 12, new Ponto(0, 0));
		
		circle[0] = new Circulo(10, new Ponto(1,1));
		circle[1] = new Circulo(10);
		circle[2] = new Circulo(5, 5);
		circle[3] = new Circulo();
		circle[4] = new Circulo(10, 12, 11);
		
		System.out.println(quad.getArea());
		System.out.println(quad.getSize());
		System.out.println(ret.getArea());
		System.out.println(ret.getSize());
		System.out.println();
		
		for (int i = 0; i < 5; i++)
		{
			System.out.printf("Circulo #%d:\n", i+1);
			System.out.println(circle[i].getArea());
			System.out.println(circle[i].getSize());
			System.out.println(circle[i].getCentre().toString());
			System.out.println(circle[i].getRay());
			System.out.println(circle[i].interception(circle[3]));
			System.out.println(circle[i].equals(circle[1]));
			System.out.println(circle[i].toString());
		}	
	}
}
