package aula8_1;

import javax.swing.*;
import java.awt.*;

public class JogoDoGalo extends JFrame {

    public static void main(String[] args) {
        JogoDoGalo jogo = new JogoDoGalo();
        jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogo.setVisible(true);
        Button.iniciaJogadorX(args.length == 0 || !args[0].equalsIgnoreCase("O"));
    }

    public JogoDoGalo() {

        this.setSize(400, 400);
        this.setTitle("Jogo do Galo");
        Board tabuleiro = new Board(this);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        int i, j;
        for (i = 1; i <= 3; i++)
            for (j = 1; j <= 3; j++)
                panel.add(new Button(i, j, tabuleiro));
        this.add(panel, "Center");
    }

    public void notificaFim(int var1) {

        assert var1 >= -1 && var1 <= 1;

        String[] var2 = new String[]{"venceu jogador O", "empate", "venceu jogador X"};
        JOptionPane.showMessageDialog(this.getContentPane(), "Resultado: " + var2[var1 + 1]);
        System.exit(0);
    }
}