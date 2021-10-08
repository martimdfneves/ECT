package aula2_1;

public class Emprestimo {

    private Data checkout;
    private Data checkin;
    private int emprestado;
    private Video escolhido;

    public Emprestimo(Data out, int emp, Video esc) {

        checkout = out;
        emprestado = emp;
        escolhido = esc;
    }

    public void devolucao(Data in) {

        checkin = in;
        escolhido.returnvid();

    }

    public Data getCheckout() {

        return checkout;
    }

    public Data getCheckin() {

        return checkin;
    }

    public int getEmprestado() {

        return emprestado;
    }

    public Video getEscolhido() {

        return escolhido;
    }

    @Override
    public String toString() {

        if (checkin == null)
            return "Loan [checkOut=" + checkout.toString() + ", emprestado=" + emprestado + ", picked=" + escolhido.getTitle() + "]";
        else
            return "Loan [checkOut=" + checkout.toString() + ", emprestado=" + emprestado + ", escolhido=" + escolhido.getTitle() + ", checkIn=" + checkin.toString() + "]";
    }
}
