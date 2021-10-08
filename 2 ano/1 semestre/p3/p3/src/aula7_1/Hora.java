package aula7_1;

public class Hora implements Comparable<Hora> {

    private int hora;
    private int minutos;
    private int tmins;

    public Hora(int hora, int minutos) {

        this.hora = hora;
        this.minutos = minutos;
        tmins = minutos + hora * 60;
    }

    public Hora() {

        this(0, 0);
    }

    public int getHora() {

        return hora;
    }

    public int getMinutos() {

        return minutos;
    }

    public int getTmins() {

        return tmins;
    }

    public static String minsToHoursStr(int minutes) {

        int mins = minutes % 60;
        int hours = minutes / 60;
        return hours + ":" + mins;
    }

    public static Hora addTimes(Hora begin, Hora extra) {

        Hora novo = new Hora();
        novo.hora = (begin.hora + extra.hora) % 24;
        novo.minutos = (begin.minutos + extra.minutos) % 60;
        return novo;
    }

    @Override
    public String toString() {

        return hora + ":" + minutos;
    }

    @Override
    public int compareTo(Hora arg0) {

        if (this.hora > arg0.hora)
            return 1;
        else if (this.hora == arg0.hora && this.minutos > arg0.minutos)
            return 1;
        else if (this.hora == arg0.hora && this.minutos == arg0.minutos)
            return 0;
        else
            return -1;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + hora;
        result = prime * result + minutos;
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
        Hora other = (Hora) obj;
        if (hora != other.hora)
            return false;
        if (minutos != other.minutos)
            return false;
        return true;
    }
}