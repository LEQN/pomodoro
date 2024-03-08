package pomodoro;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskComponent extends JPanel implements ActionListener {
    private JCheckBox checkBox;
    private JTextPane taskField;
    private JButton deleteButton;
    private Dimension taskfieldSize;
    private JPanel parentPanel;

    public TaskComponent(JPanel parent, Dimension panelSize){
        this.parentPanel = parent;
        this.taskfieldSize = new Dimension((int)(panelSize.width*0.80), 50);

        //checkBox
        checkBox = new JCheckBox();
        checkBox.setPreferredSize(new Dimension((int)(taskfieldSize.width*0.05), 50));
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkBox.addActionListener(this);

        //textpane taskfield
        taskField = new JTextPane();
        taskField.setContentType("text/html");
        taskField.setPreferredSize(taskfieldSize);
        taskField.setBorder(new LineBorder(Color.BLACK, 2));

        //delete button
        deleteButton = new JButton("X");
        deleteButton.setPreferredSize(new Dimension((int)(taskfieldSize.width*0.10), 50));
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setForeground(Color.white);
        deleteButton.setFocusable(false);
        deleteButton.setBackground(new Color(172, 57, 49));
        deleteButton.addActionListener(this);

        add(checkBox);
        add(taskField);
        add(deleteButton);
    }

    public JTextPane getTaskField(){
        return taskField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkBox.isSelected()){
            //replace the html tags to get just the text
            String taskText = taskField.getText().replaceAll("<[^>]*>", "");
            //apply the line throuhg the text
            taskField.setText("<html><s>"+taskText+"</s></html>");
        }else if (!checkBox.isSelected()){
            String taskText = taskField.getText().replaceAll("<[^>]*>", "");
            //apply the line throuhg the text
            taskField.setText(taskText);
        }

        //delete the component.
        if (e.getActionCommand().equalsIgnoreCase("X")){
            parentPanel.remove(this);
            parentPanel.repaint();
            parentPanel.revalidate();
        }
    }
}
