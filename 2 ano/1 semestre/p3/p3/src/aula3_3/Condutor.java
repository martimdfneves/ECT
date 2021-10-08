package aula3_3;

import aula1_2.*;

public class Condutor extends Pessoa {

    private String carta;

    public Condutor(String cartac, int cc, Data d, String name) {

        super(cc, d, name);
        if (!cartac.equals("A") && !cartac.equals("B") && !cartac.equals("C") && !cartac.equals("D"))
            throw new IllegalArgumentException("Carta n�o v�lida");
        carta = cartac;
    }

    public Condutor(String cartac, Pessoa p) {

        this(cartac, p.getNumber(), p.getDate(), p.getName());
    }

    public Condutor(Condutor c) {

        this(c.carta, c.getNumber(), c.getDate(), c.getName());
    }

    public String getCarta() {

        return carta;
    }

    @Override
    public String toString() {

        return "Condutor: " + super.getName() + " | Carta : " + carta;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Condutor other = (Condutor) obj;
        if (carta == null) {
            if (other.carta != null)
                return false;
        } else if (!carta.equals(other.carta))
            return false;
        return true;
    }
}
