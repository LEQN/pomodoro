package pomodoro;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private Dimension GUISize;
    private JPanel tasksPanel, taskComponentsPanel;
    private Dimension tasksPanelSize;
    private JPanel listPanel;
    private String saveDataFile = "savedData.dat";
    private List<TaskComponent> taskComponentList;

    public TaskList(int width, int height){
        GUISize = new Dimension(width, height);
        tasksPanelSize = new Dimension(GUISize.width-50, GUISize.height-200);
        taskComponentList = new ArrayList<>();
        //load content from save file
        loadContent();
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

        //populate taskcomponents from list to panel
        for (TaskComponent task : taskComponentList){
            taskComponentsPanel.add(task);
            addListeners(task);
        }
        return listPanel;
    }

    public void createTask(){
        TaskComponent taskComponent = new TaskComponent(taskComponentsPanel, tasksPanelSize);
        //set listeners for changes and deletion
        addListeners(taskComponent);
        //add component
        taskComponentList.add(taskComponent);
        taskComponentsPanel.add(taskComponent);
        taskComponent.getTaskField().requestFocus();
        listPanel.repaint();
        listPanel.revalidate();

        saveContent();
    }

    private void loadContent(){
        File file = new File(saveDataFile);
        if (file.exists()){
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveDataFile))) {
                taskComponentList = (List<TaskComponent>) ois.readObject();
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    private void saveContent(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveDataFile))){
            oos.writeObject(taskComponentList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addListeners(TaskComponent task){
        //set change listener to save when a task is checked
        task.setTaskChangeListener(new TaskComponent.TaskChangeListener() {
            @Override
            public void taskChanged(TaskComponent taskComponent) {
                int index = taskComponentList.indexOf(taskComponent);
                if (index != -1){
                    TaskComponent updated = taskComponent;
                    taskComponentList.set(index, updated);
                    saveContent();
                }
            }
        });
        //set delete listener  when the delete button of a task is clicked
        task.getDeleteButton().addActionListener(e -> {
            taskComponentsPanel.remove(task);
            taskComponentsPanel.repaint();
            taskComponentsPanel.revalidate();
            taskComponentList.remove(task);
            saveContent();
        });
    }

}
