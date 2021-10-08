package aula3_1;

import aula1_2.Data;

import java.util.Calendar;

public class Bolseiro extends Estudante {

    private int bolsa;

    public Bolseiro(int cardnumber, Data date, String name, Data dinscricao) {

        super(cardnumber, date, name, dinscricao);
        bolsa = 0;
    }

    public Bolseiro(int cardnumber, Data date, String name) {

        this(cardnumber, date, name, new Data(Calendar.DAY_OF_MONTH, Calendar.MONTH + 1, Calendar.YEAR));
    }

    public void setBolsa(int b) {

        bolsa = b;
    }

    public int bolsa() {

        return bolsa;
    }


    @Override
    public String toString() {

        return super.getName() + ", BI: " + super.getNumber() + ", Nasc.Data:" + super.getDate() +
                ", NMec: " + super.getNmec() + ", inscrito em Data: " + super.getDinscricao() + ", Bolsa: " + bolsa;
    }
}
