package number;

/**
 * Tipo de dados representando uma fracção.
 * Esta versão impõe um invariante (interno) mais forte:
 * as frações armazenadas têm sempre denominador positivo.
 * Isto permite simplificar alguns métodos.
 *
 * @author João Manuel Rodrigues
 * 2007-2017
 */

public class Fraction{
    /*assert invariant();
  }*/

  //dados
  private int den;
  private int num;

  //inicializa a classe sem argumentos
  public Fraction(){
  	num = 0;
  	den = 1;
  }

  //inicializa a classe com argumentos
  public Fraction(int numero, int denumero) {
    assert denumero != 0;

    if (denumero < 0) {
    	num = -numero;
	    den = -denumero;
    } else {
    	num = numero;
	    den = denumero;
    }
    
    assert invariant();

  }

  /* Testa o invariante do objeto.
   * Ou seja, a propriedade que define a validade de uma fração.
   * É para testar em asserções nos métodos.
   */
  public boolean invariant() {
    return den > 0;   // O denominador não pode ser nulo!
  }

  /** Converte uma string numa fracção.
   *  Chamava-se fromString na v5.  Implementado de outra forma.
   *  @param str String no formato {@code "inteiro/inteiro"}
   *             representando uma fracção válida.
   *  @return fracção correspondente a {@code str}.
   */
  public static Fraction parseFraction(String str) {
    String[] p = str.split("/", 2);  // divide a string em até 2 partes
    int n = Integer.parseInt(p[0]);  // extrai numerador
    int d = (p.length==2) ? Integer.parseInt(p[1]) : 1;
        // se tem 2 partes, extrai denominador, senão fica 1
    if (d < 0) {
    	d = -d;
    	n = -n;
    }else{
    	d = d;
    	n = n;
    }

    Fraction lol = new Fraction(n, d);
    return lol;
  }

  /** Converte a fracção numa string.
   *  @return string com a representação desta fracção.
   */
  public String toString() {
    assert invariant();
    // Com um invariante mais forte, pode-se simplificar este método!
    String s;
    if (den > 0)
      s = "(" + num + "/" + den + ")";
    else
      s = "(" + (-num) + "/" + (-den) + ")";
    return s;
  }

  /** Devolve o numerador da fracção.
   *  @return numerador desta fração.
   */
  public int num() { return num; }

  /** Devolve o denominador da fracção.
   *  @return denominador desta fração.
   */
  public int den() { return den; }

  /** Multiplica esta fracção por outra (this * b).
   *  @param b multiplicando.
   *  @return fracção produto de this * b.
   */
  public Fraction multiply(Fraction b) {
    Fraction r = new Fraction();  // fracção para o resultado
    r.num = num*b.num;  // possibilidade de overflow!
    r.den = den*b.den;
    assert r.invariant() : "Result should be valid.";
    return r;
  }

  /** Adiciona esta fracção com outra (this + b).
   *  @param b fracção a adicionar a esta.
   *  @return fracção soma de this + b.
   */
  public Fraction add(Fraction b) {
    Fraction r = new Fraction();  // fracção para o resultado
    r.num = num*b.den + den*b.num;  // possibilidade de overflow!
    r.den = den*b.den;
    assert r.invariant() : "Result should be valid.";
    return r;
  }

  public Fraction divide(Fraction b) {
    assert (b.den != 0) && (den != 0): "Division by zero!";
    Fraction r = new Fraction();  
    r.num = num * b.den;
    r.den = den * b.num;
    assert r.invariant() : "Result should be valid.";
    return r;
  }

  public Fraction subtract(Fraction b) {
    Fraction r = new Fraction();
    if (den > b.den) {
    	r.den = mdc(den, b.den);
    } else if (den < b.den) {
    	r.den = mdc(b.den, den);
    } else {
    	r.den = den;
    }
    r.num = num * b.den - b.num * den;
    assert r.invariant() : "Result should be valid.";
    return r;
  }

  public boolean equals(Fraction b) {
  	if ((b.num / b.den) == (num / den)) {
  		return true;
  	} else {
  		return false;
  	}
  }
/*
  public int compareTo(Fraction b) {
    //...
  }
*/
  //calcula o maximo divisor comum
  //NOTA: Usa recursividade, não copiar direto 
  public int mdc(int n1, int n2){
  	if (n2 == 0) {
  		return n1;
  	} else {
  		return mdc(n1, n1 % n2);
  	}
  }
}