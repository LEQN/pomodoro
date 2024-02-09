package pomodoro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PomodoroFrame extends JFrame{
    private JTextField pomodoroTime;
    private JTextField restTime;
    private int sessions;
    private RoundedButton startButton;
    private GridBagConstraints gbc = new GridBagConstraints();

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
//        startPanel.setLayout(new BorderLayout());
        startPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(35, 5, 35, 5);
        // time input
        pomodoroTime = new JTextField("00:00");
        pomodoroTime.setFont(new Font("Arial", Font.BOLD, 170));
        pomodoroTime.setForeground(new Color(1, 186, 239));
        pomodoroTime.setOpaque(false);
        pomodoroTime.setBorder(BorderFactory.createEmptyBorder());
        //gridbag positioning
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        startPanel.add(pomodoroTime, gbc);

        // session input
        JSpinner sessionInput = new JSpinner();
        sessionInput.setPreferredSize(new Dimension(125, 125));
        JLabel sessionLabel = new JLabel("Sessions:");
        sessionLabel.setForeground(new Color(1, 186, 239));
        sessionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        //layout position
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;
        startPanel.add(sessionInput, gbc);
        //label
//        gbc.gridx = 3;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.PAGE_END;

        startPanel.add(sessionLabel, gbc);

        // pomo button
        RoundedButton pomoButton = new RoundedButton("Pomo");
        pomoButton.setPreferredSize(new Dimension(200, 50));
        pomoButton.setFont(new Font("Arial", Font.BOLD, 20));
        pomoButton.setForeground(Color.white);
        pomoButton.setBackground(new Color(246, 174, 45));
        pomoButton.setFocusable(false);
        //layout positioning
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        startPanel.add(pomoButton, gbc);

        // rest button
        RoundedButton restButton = new RoundedButton("Rest");
        restButton.setPreferredSize(new Dimension(200, 50));
        restButton.setFont(new Font("Arial", Font.BOLD, 20));
        restButton.setForeground(Color.white);
        restButton.setBackground(new Color(246, 174, 45));
        restButton.setFocusable(false);
        //gridbag layout
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        startPanel.add(restButton, gbc);

        // start button
        startButton = new RoundedButton("Start");
        startButton.setPreferredSize(new Dimension(600, 90));
        startButton.setFont(new Font("Arial", Font.BOLD, 40));
        startButton.setForeground(Color.white);
        startButton.setBackground(new Color(172, 57, 49));
        startButton.setFocusable(false);
        //gridbag layout positioning
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        startPanel.add(startButton, gbc);
        return startPanel;
    }
}
