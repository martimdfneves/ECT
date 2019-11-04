package aula2_2;

public class Position {
	
	private final int xx;
	private final int yy;
	
	public Position(int x, int y) {
		
		xx = x;
		yy = y;
	}
	
	public int getX() {
		
		return xx;
	}
	
	public int getY() {
		
		return yy;
	}
	
	@Override
	public String toString() {
		
		return "(" + (xx+1) + ", " + (yy+1) + ")";
	}

}
