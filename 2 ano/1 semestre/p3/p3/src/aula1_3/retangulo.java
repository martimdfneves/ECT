package aula1_3;

public class retangulo {

    private Ponto centro;
    private double comprimento;
    private double largura;

    public retangulo(double l, double comp, Ponto c) {

        largura = l;
        comprimento = comp;
        centro = c;
    }

    public retangulo(double l, double c) {

        this(l, c, new Ponto(0.0, 0.0));
    }

    public retangulo(Ponto c) {

        this(1, 2, c);
    }

    public retangulo() {

        this(1, 2, new Ponto(0.0, 0.0));
    }

    public double getArea() {

        return largura * comprimento;
    }

    public double getLarg() {

        return largura;
    }

    public double getCompr() {

        return comprimento;
    }

    public Ponto getCentre() {

        return centro;
    }

    public double getSize() {

        return largura * 2 + comprimento * 2;
    }

    public String toString() {

        return "Ret�ngulo com centro em " + centro.toString() + " largura de " + largura + " unidades e comprimento de " + comprimento + " unidades.\n";
    }

}