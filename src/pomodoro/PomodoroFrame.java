package pomodoro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PomodoroFrame extends JFrame{
    private JTextField pomodoroTimeField;
    private JTextField restTimeField;
    private JTextField sessionsField;
    private JButton startButton;

    public PomodoroFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000, 600));
        this.pack();
    }
}
