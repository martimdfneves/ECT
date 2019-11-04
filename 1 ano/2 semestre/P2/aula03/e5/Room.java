package e5;

public class Room{

	private String divisao;
	private Point cSd;
	private Point cIe;

	public Room(Room sala){
		this.divisao = sala.divisao;
		this.cSd = sala.cSd;
		this.cIe = sala.cIe;
	}

	public Room(Point cantoInferiorEsquerdo, Point cantoSuperiorDireito, String division){
		divisao = division;
		cSd = cantoSuperiorDireito;
		cIe = cantoInferiorEsquerdo;
	}

	public String roomType(){
		return divisao;
	}

	public Point bottomLeft(){
		return cIe;
	}	

	public Point topRight(){
		return cSd;
	}

	public Point geomCenter(){
		return cIe.halfWayTo(cSd);
	}

	public double area(){
		double comprimento = 0;
		double largura = 0;

		comprimento = Math.abs(cIe.x() - cSd.x());
		largura = Math.abs(cSd.y() - cIe.y());

		return comprimento * largura;
	}
}
