package aula8_1;

public class Board {

    private JogoDoGalo jogo;
    private int[][] matriz = new int[3][3];
    private int numeroJogadas = 0;
    private int[] soma = new int[8];

    public Board(JogoDoGalo jogo) {

        this.jogo = jogo;
    }

    public void joga(int x, int y, boolean player) {

        if (!JogadaValida(x, y) || (JogadaValida(x, y) && !CasaVazia(x, y)))
            throw new IllegalArgumentException("Posi��o n�o v�lida");

        byte move;
        if (player) {

            move = 1;
        } else {

            move = -1;
        }

        matriz[x - 1][y - 1] = move;
        numeroJogadas++;
        soma[x - 1] += move;
        soma[3 + y - 1] += move;
        if (x == y) {

            this.soma[6] += move;
        }

        if (x == 4 - y) {

            this.soma[7] += move;
        }

        if (end()) {

            jogo.notificaFim(result());
        }

    }

    private boolean JogadaValida(int x, int y) {

        return x >= 1 && x <= 3 && y >= 1 && y <= 3;
    }

    private boolean CasaVazia(int x, int y) {

        if (!JogadaValida(x, y))
            throw new IllegalArgumentException("Posi��o n�o v�lida");

        return matriz[x - 1][y - 1] == 0;
    }

    private boolean end() {

        boolean fim = (numeroJogadas == 9);

        int i;
        for (i = 0; !fim && i < 8; i++) {

            fim = Math.abs(soma[i]) == 3;
        }

        return fim;
    }

    private int result() {

        byte win = 0;

        int i;
        for (i = 0; win == 0 && i < 8; i++) {
            if (soma[i] == 3) {

                win = 1;
            } else if (soma[i] == -3) {

                win = -1;
            }
        }
        return win;
    }
}