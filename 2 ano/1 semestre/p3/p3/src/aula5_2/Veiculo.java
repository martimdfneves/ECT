package aula5_2;

import java.util.*;

public class Veiculo {

    private int rodas;
    private String marca;
    private String cor;

    public Veiculo(int rodas, String marca, String cor) {

        if (rodas < 2 || rodas > 4) {

            throw new InputMismatchException();
        }
        this.rodas = rodas;
        this.marca = marca;
        this.cor = cor;
    }

    public int getRodas() {
        return rodas;
    }

    public String getMarca() {
        return marca;
    }

    public String getCor() {
        return cor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cor == null) ? 0 : cor.hashCode());
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + rodas;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Veiculo other = (Veiculo) obj;
        if (cor == null) {
            if (other.cor != null)
                return false;
        } else if (!cor.equals(other.cor))
            return false;
        if (marca == null) {
            if (other.marca != null)
                return false;
        } else if (!marca.equals(other.marca))
            return false;
        if (rodas != other.rodas)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Veiculo [rodas=" + rodas + ", marca=" + marca + ", cor=" + cor + "]";
    }
}