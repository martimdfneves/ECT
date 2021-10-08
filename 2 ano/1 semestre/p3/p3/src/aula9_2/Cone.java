package aula9_2;

public class Cone implements Gelado {

    private Gelado ice;
    private int base;

    public Cone(Gelado gel) {

        ice = gel;
    }

    @Override
    public void base(int arg) {

        base = arg;
        System.out.printf("\n%d %s de gelado de %s", base, base == 1 ? "bola" : "bolas", ice.getSabor());
    }

    @Override
    public String getSabor() {

        return ice.getSabor();
    }
}