public class Fraction {
	private int num;
	private int den;
	private boolean zero = false;
	private boolean integer = false;
	
	public Fraction(int num, int den){
		this.num = num;
		this.den = den;
		if (num == 0){
			this.zero = true;
		}
		if (den == 1){
			this.integer = true;
		}
	}
	
	public int getNum(){
		return this.num;
	}
	
	public int getDen(){
		return this.den;
	}
	
	@Override
	public String toString(){
		if(this.zero == true){
			return "0";
		}
		else if(this.integer == true){
			return ""+this.num;
		}
		return this.num+"/"+this.den;
	}
}

