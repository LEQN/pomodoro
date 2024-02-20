package pomodoro;

import javax.swing.*;
import java.awt.*;

public class PomodoroFrame implements PomodoroListener{
    private JFrame frame = new JFrame();
    private JSpinner spinner; //sessions spinner
    private JTextField pomodoroTimeInput;//time input for pomo and rest
    private PomodoroTimer countdown;
    private int pomodoroTime = 0;//pomo  time in seconds
    private int restTime = 0;//rest time in seconds
    private int sessions = 0;
    private JLabel countdownLabel;
    private JLabel sessionCount;
    private RoundedButton startButton;
    private GridBagConstraints gbc = new GridBagConstraints();

    public PomodoroFrame(){
        frame.setTitle("Pomo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //frame icon image

//        background color
        frame.getContentPane().setBackground(new Color(11, 79, 108));
        // add start panel
        frame.add(initialPanel());

        frame.setVisible(true);
    }

    private JComponent initialPanel(){
        JComponent startPanel = new JPanel();
        startPanel.setBackground(new Color(11, 79, 108));
        startPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(35, 5, 35, 5);
        // time input
        pomodoroTimeInput = new JTextField();
        pomodoroTimeInput.setDocument(new JTextFieldLimit(5));
        pomodoroTimeInput.setText("00:00");
        pomodoroTimeInput.setFont(new Font("Arial", Font.BOLD, 170));
        pomodoroTimeInput.setForeground(new Color(1, 186, 239));
        pomodoroTimeInput.setOpaque(false);
        pomodoroTimeInput.setBorder(BorderFactory.createEmptyBorder());
        //gridbag positioning
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        startPanel.add(pomodoroTimeInput, gbc);

        // session input
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(125, 125));
        JTextField editor = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        editor.setForeground(new Color(246, 174, 45)); // Change text color
        editor.setBackground(new Color(11, 79, 108));
        editor.setFont(new Font("Arial", Font.PLAIN, 60));
        JLabel sessionLabel = new JLabel("Sessions:");
        sessionLabel.setForeground(new Color(1, 186, 239));
        sessionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        //layout position
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;
        startPanel.add(spinner, gbc);
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
        pomoButton.addActionListener(e -> setPomodoroTime());
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
        restButton.addActionListener(e -> setRestTime());
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
        startButton.addActionListener(e -> startTimer());
        //gridbag layout positioning
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        startPanel.add(startButton, gbc);
        return startPanel;
    }

    private JComponent countDownDisplay(){
        JComponent countPanel = new JPanel();
        countPanel.setBackground(new Color(11, 79, 108));
        countPanel.setLayout(new GridBagLayout());
        //countdown display
        countdownLabel = new JLabel("00:00");
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 190));
        countdownLabel.setForeground(new Color(1, 186, 239));
        countdownLabel.setOpaque(false);
        // layout placement
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,100, 50, 100);
        countPanel.add(countdownLabel, gbc);

        //session count display
        sessionCount = new JLabel("Current session: ");
        sessionCount.setFont(new Font("Arial", Font.BOLD, 20));
        sessionCount.setForeground(new Color(1, 186, 239));
        sessionCount.setOpaque(false);
        //layout placement
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(35, 5, 35, 5);
        countPanel.add(sessionCount, gbc);
        //continue button
        RoundedButton continueButton = new RoundedButton("Continue");
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueButton.setForeground(Color.white);
        continueButton.setBackground(new Color(246, 174, 45));
        continueButton.setFocusable(false);
        continueButton.addActionListener(e -> countdown.continueTimer());
        // layout placement
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 20;
        gbc.ipady = 20;
        countPanel.add(continueButton, gbc);
        //pause button
        RoundedButton pauseButton = new RoundedButton("Pause");
        pauseButton.setPreferredSize(new Dimension(200, 50));
        pauseButton.setFont(new Font("Arial", Font.BOLD, 20));
        pauseButton.setBackground(new Color(246, 174, 45));
        pauseButton.setForeground(Color.white);
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(e -> countdown.pauseTimer());
        // layout placement
        gbc.gridx = 2;
        gbc.gridy = 1;
        countPanel.add(pauseButton, gbc);
        //quit button
        RoundedButton quitButton = new RoundedButton("Quit");
        quitButton.setPreferredSize(new Dimension(200, 50));
        quitButton.setFont(new Font("Arial", Font.BOLD, 20));
        quitButton.setBackground(new Color(172, 57, 49));
        quitButton.setForeground(Color.white);
        quitButton.setFocusable(false);
        quitButton.addActionListener(e -> quitTimer());
        // layout placement
        gbc.gridx = 1;
        gbc.gridy = 2;
        countPanel.add(quitButton, gbc);

        return countPanel;
    }

    /**
     * Takes the pomodoroTime field and gets the string to split into minutes and seconds.
     * @return array with minutes and seconds
     */
    private void setPomodoroTime(){
        //resut current pomo time value
        pomodoroTime = 0;
        try {
            //split string and convert to total time in seconds
            String[] inputs = pomodoroTimeInput.getText().split(":");
            if(inputs.length < 2){
                throw new IllegalArgumentException();
            }
            //minutes to total pomo time
            pomodoroTime += Integer.parseInt(inputs[0]) * 60;
            //add seconds input
            pomodoroTime += Integer.parseInt(inputs[1]);
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, "Invalid time format. Must use ':' \n e.g., 00:00", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setRestTime(){
        // reset current rest value to 0
        restTime = 0;
        try {
            //split string and convert to total time in seconds
            String[] input = pomodoroTimeInput.getText().split(":");
            if (input.length < 2) {
                throw new IllegalArgumentException();
            }
            //minutes to total rest time
            restTime += Integer.parseInt(input[0]) * 60;
            // add seconds to rest time
            restTime += Integer.parseInt(input[1]);
            System.out.println(restTime);
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, "Invalid time format. Must use ':' \n e.g., 00:00", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void setSessions(){
                sessions = (int) spinner.getValue();;
    }

    private void startTimer(){
        setSessions();
        //check valid pomo time been set ie > 0 and rest time set if sessions > 1
        if(pomodoroTime > 0){
            if (sessions > 1 && restTime == 0){
                JOptionPane.showMessageDialog(frame, "For multiple sessions you must have a rest period set.", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                //swap initial input panel with the countdown display panel
                frame.setContentPane(countDownDisplay());
                frame.revalidate();
                frame.repaint();
                //start the countdown
                countdown = new PomodoroTimer(this, pomodoroTime, restTime, sessions);//make this a field
                countdown.start();
            }
        }else{
            JOptionPane.showMessageDialog(frame, "No time provided. \n Please enter a pomo time period.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void timeDisplayUpdate(int min, int sec){
        countdownLabel.setText(String.format("%02d:%02d", min, sec));
    }

    @Override
    public void sessionDisplayUpdate(int curSession){
        sessionCount.setText(String.format("Sessions: %01d / %01d", curSession, sessions));
    }

    @Override
    public void onSessionsEnd(){
        // return to initial screen panel
        frame.setContentPane(initialPanel());
        frame.revalidate();
        frame.repaint();
    }


    private void quitTimer(){
        //quit countdown timer logic
        countdown.endTimer();
        countdown = null;
        // return to initial screen panel
        onSessionsEnd();
    }

}
