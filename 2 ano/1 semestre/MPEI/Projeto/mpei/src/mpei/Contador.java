package mpei;

public class Contador{
    static double cont = 1;
    static int n = 0;

    public static void add(){
        if(cont >= Math.random()){
            n++;
            update();
        }
    }

    public static void update(){
        cont = 1/(Math.pow(2,n));
    }

	public static double getCount() {
		return cont;
	}
	public static int getN() {
		return n;
	}
}