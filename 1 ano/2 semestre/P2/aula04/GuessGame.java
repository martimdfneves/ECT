import static java.lang.System.*;

public class GuessGame{

	private static int numDeTentativas;
	private int tentativa;
	private int num;
	private int max;
	private int min;

	public static void main(String[] args) {
		out.println("Starting tests.");
		GuessGame game = new GuessGame(1,10);
		// initial tests:
		assert !game.finished() : "game should not be finished yet";
		assert game.min() == 1;
		assert game.max() == 10;
		assert game.numAttempts() == 0 : "there should be no attempts yet";
	
		for(int i = -10; i <= 20; i++) {
			assert game.validAttempt(i) == (i >= 1 && i <= 10);
		}
		// trying all wrong answers:
		int nplay = 0; // how may times play was called
		for(int n = 1; n <= 10; n++) {
			if (n != game.num) {
				game.play(n); // make a wrong guess
				nplay++;
				assert game.numAttempts() == nplay;
				assert !game.finished() : "game should not be finished yet";
				assert (n < game.num) == game.attemptIsLower();
				assert (n > game.num) == game.attemptIsHigher();
			}
		}
		// make the right guess:
		game.play(game.num);
		nplay++;
		assert game.finished() : "game should be finished now";
		assert game.numAttempts() == nplay;
		out.println("No error detected!");
	}

	//inicializa o jogo
	public GuessGame(int min, int max){
		assert min < max : "Máximo não maior que o mínimo";
		this.max = max;
		this.min = min;
		num = (int)Math.round(((Math.random() * (max - min))) + min);	
	}

	//retorna o minimo
	public int min(){
		return min;
	}

	//retorna o maximo
	public int max(){
		return max;
	}

	//diz se a tentativa é válida
	public boolean validAttempt(int attempt){
		if (attempt >= min && attempt <= max) {
			return true;
		} else {
			return false;
		}
	}

	//diz se acabou
	public boolean finished(){
		if (tentativa == num) {
			return true;
		} else {
			return false;
		}
	}

	//método de registo da tentativa
	public void play(int attempt){
		assert validAttempt(attempt);
		assert !finished();

		numDeTentativas++;
		tentativa = attempt;
	}

	//metodo para ver se é superior ou inferior
	public boolean attemptIsHigher(){
		if (tentativa > num) {
			return true;
		} else {
			return false;
		}
	}

	public boolean attemptIsLower(){
		if (tentativa < num) {
			return true;
		} else {
			return false;
		}
	}

	//metodo para retornar o numero de tentativas
	public static int numAttempts(){
		return numDeTentativas;
	}


}
