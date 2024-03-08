package pomodoro;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TaskList {

    private Dimension GUISize;
    private JPanel tasksPanel, taskComponentsPanel;
    private Dimension tasksPanelSize;
    private JPanel listPanel;

    public TaskList(int width, int height){
        GUISize = new Dimension(width, height);
        tasksPanelSize = new Dimension(GUISize.width-50, GUISize.height-200);
    }

    public JComponent listPanel(){
        listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBackground(new Color(11, 79, 108));
        //banner label
        JLabel bannerLabel = new JLabel("Tasks List:");
        bannerLabel.setForeground(new Color(1, 186, 239));
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 25));
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        listPanel.add(bannerLabel, BorderLayout.NORTH);

        tasksPanel = new JPanel();
//        //taskComponentPanel
        taskComponentsPanel = new JPanel();
        taskComponentsPanel.setLayout(new BoxLayout(taskComponentsPanel, BoxLayout.Y_AXIS));
        tasksPanel.add(taskComponentsPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setPreferredSize(tasksPanelSize);
        scrollPane.setBorder(new LineBorder(new Color(246, 174, 45), 3));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(contentPanel, BorderLayout.CENTER);

        //add task button
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setPreferredSize(new Dimension(GUISize.width, 80));
        addTaskButton.setForeground(Color.white);
        addTaskButton.setBackground(new Color(246, 174, 45));
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 20));
        addTaskButton.setFocusable(false);
        addTaskButton.addActionListener(e -> createTask());
        listPanel.add(addTaskButton, BorderLayout.SOUTH);
        return listPanel;
    }

    public void createTask(){
        TaskComponent taskComponent = new TaskComponent(taskComponentsPanel, tasksPanelSize);
        taskComponentsPanel.add(taskComponent);
        taskComponent.getTaskField().requestFocus();
        listPanel.repaint();
        listPanel.revalidate();
    }

}
