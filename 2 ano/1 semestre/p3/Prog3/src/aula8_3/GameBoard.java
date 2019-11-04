package aula8_3;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GameBoard extends JFrame {
	
    private static Random rand = new Random();
    private Questions questions;
    private Question quest;
    private JLabel amount, picture;
    private JTextArea questionText;
    private JButton publico, call, fifty;
    private JRadioButton o1, o2, o3, o4;
    private ButtonGroup options;

    public GameBoard(String questionsFName) 
    throws IOException {
    	
        questions = new Questions(questionsFName);
        quest = questions.getQuestion();
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Quem quer ser milionario? Powered by ZMPPaiva 3:->");
        initiateGUI(this.getContentPane());
        this.pack();
        this.setVisible(true);
    }

    private void initiateGUI(Container contentPane) 
    throws IOException {
    	
    	amount = new JLabel("Pote: " + String.valueOf(quest.getPrize()) + "€", SwingConstants.CENTER);
        contentPane.add(amount, BorderLayout.PAGE_START);

        picture = new JLabel(new ImageIcon(ImageIO.read(new File(quest.getImgPath())).getScaledInstance(250, 250, 0)));
        picture.setPreferredSize(new Dimension(300, 300));
        contentPane.add(picture, BorderLayout.LINE_START);

        questionText = new JTextArea(quest.getQuestText());
        questionText.setPreferredSize(new Dimension(100, 20));
        questionText.setEditable(false);
        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(true);
        questionText.setFont(questionText.getFont().deriveFont(20f));
        contentPane.add(questionText, BorderLayout.CENTER);

        o1 = new JRadioButton("<html>" + quest.getOption(0) + "</html>");
        o2 = new JRadioButton("<html>" + quest.getOption(1) + "</html>");
        o3 = new JRadioButton("<html>" + quest.getOption(2) + "</html>");
        o4 = new JRadioButton("<html>" + quest.getOption(3) + "</html>");
        
        options = new ButtonGroup();
        options.add(o1);
        options.add(o2);
        options.add(o3);
        options.add(o4);
        
        JPanel radioPanel = new JPanel(new GridLayout(2, 2));
        radioPanel.setPreferredSize(new Dimension(50, 50));
        radioPanel.add(o1);
        radioPanel.add(o2);
        radioPanel.add(o3);
        radioPanel.add(o4);
        
        publico = new JButton("Publico");
        call = new JButton("Chamada");
        fifty = new JButton("50-50");
        JButton giveUp = new JButton("Desisto");
        JButton confirm = new JButton("Confirmo");
        
        JPanel helpPane = new JPanel();
        helpPane.add(publico);
        helpPane.add(call);
        helpPane.add(fifty);
        
        JPanel actionPane = new JPanel();
        actionPane.add(giveUp);
        actionPane.add(confirm);
        
        JPanel buttonPane = new JPanel(new BorderLayout());
        buttonPane.add(helpPane, BorderLayout.WEST);
        buttonPane.add(actionPane, BorderLayout.EAST);
        buttonPane.add(radioPanel, BorderLayout.NORTH);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);

        confirm.addActionListener(e -> {
        	
            for (JRadioButton button : new JRadioButton[]{o1, o2, o3, o4})
                if (button.isSelected())
                    if (isSolution(button))
                        try {
                        	
                            nextQuestionOrWin();
                        } 
            			catch (IOException e1) {
            				
                            e1.printStackTrace();
                            endGame();
                        }
                    else
                        lose();
        });

        giveUp.addActionListener(e -> endGame());

        publico.addActionListener(e -> publico());

        call.addActionListener(e -> call());

        fifty.addActionListener(e -> fifty());
    }

    private void displayNextQuestion() 
    throws IOException {
    	
        quest = questions.getQuestion();
        amount.setText(String.valueOf(quest.getPrize()) + "€");
        picture.setIcon(new ImageIcon(ImageIO.read(new File(quest.getImgPath()))));
        questionText.setText(quest.getQuestText());
        
        options.clearSelection();
        
        o1.setText("<html>" + quest.getOption(0) + "</html>");
        o1.setEnabled(true);
        
        o2.setText("<html>" + quest.getOption(1) + "</html>");
        o2.setEnabled(true);
        
        o3.setText("<html>" + quest.getOption(2) + "</html>");
        o3.setEnabled(true);
        
        o4.setText("<html>" + quest.getOption(3) + "</html>");
        o4.setEnabled(true);
    }

    private void fifty() {
    	
        int i = 2;
        JRadioButton[] buttons = {o1, o2, o3, o4};
        while (i != 0) {
            int idx = rand.nextInt(buttons.length);
            if (buttons[idx].isEnabled() && !isSolution(buttons[idx])) {
            	
                buttons[idx].setEnabled(false);
                i--;
            }
        }
        fifty.setEnabled(false);
    }

    private void call() {
    	
        JRadioButton[] arr = new JRadioButton[2];
        JRadioButton[] buttons = {o1, o2, o3, o4};
        int i = 0;
        while (i != 2) {
        	
            int index = rand.nextInt(buttons.length);
            if (i == 0 || arr[i - 1] != buttons[index])
                arr[i++] = buttons[index];
        }
        
        String textMessage = "I thinks it is...";
        
        for (JRadioButton b : arr)
            textMessage += "\n" + getButtonText(b);
        
        JOptionPane.showMessageDialog(this.getContentPane(), textMessage);
        call.setEnabled(false);
    }

    private void publico() {
    	
        JRadioButton[] buttons = {o1, o2, o3, o4};
        int max = 100;
        int[] vals = new int[buttons.length];
        
        for (int i = 0; i < vals.length; i++)
            max -= (vals[i] = rand.nextInt(max));
        
        int idx = 0;
        String textMessage = "Público:";
        
        for (JRadioButton bt : new JRadioButton[]{o1, o2, o3, o4})
            textMessage += "\n" + vals[idx++] + "% - " + getButtonText(bt);
        
        JOptionPane.showMessageDialog(this.getContentPane(), textMessage);
        publico.setEnabled(false);
    }

    private void nextQuestionOrWin() throws IOException {
    	
        if (questions.noOtherQuestion())
            win();
        else {
        	
            JOptionPane.showMessageDialog(this.getContentPane(), "Correto!");
            displayNextQuestion();
        }
    }

    private String getButtonText(JRadioButton b) {
    	
        String buttonText = b.getText();
        return buttonText.substring(6, buttonText.length() - 7);
    }
    
    private boolean isSolution(JRadioButton button) {
    	
        return quest.getQuestAnswer().equals(getButtonText(button));
    }
    
    private void win() {
    	
        JOptionPane.showMessageDialog(this.getContentPane(), "Ganhou o prémio máximo de " + quest.getPrize() + "€.");
        endGame();
    }

    private void lose() {
    	
        JOptionPane.showMessageDialog(this.getContentPane(), "Perdeu. Prémio de consolação: " + questions.lastQuestionPrize() + "€.");
        endGame();
    }

    private void endGame() {
    	
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}