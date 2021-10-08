package aula3_2;

public class Quadrado extends Retangulo {

    public Quadrado(double l, Ponto c) {

        super(l, l, c);
    }

    public Quadrado(double l, int x, int y) {

        this(l, new Ponto(x, y));
    }

    public Quadrado(double l) {

        this(l, new Ponto(0, 0));
    }

    public Quadrado() {

        this(1, new Ponto(0, 0));
    }

    public Quadrado(Quadrado q) {

        this(q.getLarg(), q.getCentre());
    }
}
