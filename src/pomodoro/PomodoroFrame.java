package pomodoro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PomodoroFrame extends JFrame{
    private JTextField pomodoroTimeField;
    private JTextField restTimeField;
    private JTextField sessionsField;
    private RoundedButton startButton;

    public PomodoroFrame(){
        this.setTitle("Pomo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //frame icon image

//        background color
        this.getContentPane().setBackground(new Color(11, 79, 108));
        // add start panel
        this.add(initialPanel());
    }

    private JComponent initialPanel(){
        JComponent startPanel = new JPanel();
        startPanel.setBackground(new Color(11, 79, 108));

        // start button
        startButton = new RoundedButton("Start");
        startButton.setPreferredSize(new Dimension(600, 50));
        startButton.setFont(new Font("Arial", Font.BOLD, 40));
        startButton.setForeground(Color.white);
        startButton.setBackground(new Color(172, 57, 49));
        startButton.setFocusable(false);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startPanel.add(startButton);
        return startPanel;
    }
}
