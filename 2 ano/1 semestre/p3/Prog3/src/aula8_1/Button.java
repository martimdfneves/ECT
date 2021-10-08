package aula8_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Button extends JToggleButton implements ActionListener {

    protected Board tabuleiro;
    protected int linhas;
    protected int colunas;
    protected static boolean jogadorX;

    public Button(int x, int y, Board player) {

        this.addActionListener(this);
        linhas = x;
        colunas = y;
        tabuleiro = player;
        this.setFont(new Font("Arial", 1, 30));
    }

    public void actionPerformed(ActionEvent e) {

        if (jogadorX)
            this.setText("X");
        else
            this.setText("O");


        this.setEnabled(false);
        tabuleiro.joga(linhas, colunas, jogadorX);
        jogadorX = !jogadorX;
    }

    public static void iniciaJogadorX(boolean player) {

        jogadorX = player;
    }
}