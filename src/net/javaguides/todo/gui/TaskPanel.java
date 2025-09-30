
package net.javaguides.todo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import net.javaguides.todo.*;

public class TaskPanel extends JPanel {
    private TaskManager taskManager;
    private JPanel tasksContainer;
    private JScrollPane scrollPane;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private Task selectedTask;

    public TaskPanel(TaskManager taskManager) {
        this.taskManager = taskManager;
        setLayout(new BorderLayout());

        // Create a container for individual task panels
        tasksContainer = new JPanel();
        tasksContainer.setLayout(new BoxLayout(tasksContainer, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(tasksContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask());
        updateButton.addActionListener(e -> updateTask());
        deleteButton.addActionListener(e -> deleteTask());
        refreshButton.addActionListener(e -> refreshTasks());

        refreshTasks();
    }

    private JPanel createTaskPanel(Task task){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(550, 60));
        panel.setMinimumSize(new Dimension(550, 60));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panel.setBackground(Color.WHITE);

        // Add selection functionality
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), // Thicker black border
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Create clickable checkbox
        String checkboxText = task.getCheckboxDisplay();
        JLabel checkbox = new JLabel(task.getCheckboxDisplay());
        checkbox.setFont(new Font("Arial", Font.BOLD, 16)); //Bigger bold font
        checkbox.setForeground(Color.BLUE); // Force blue color
        checkbox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkbox.setPreferredSize(new Dimension(40, 40));
        checkbox.setMinimumSize(new Dimension(40, 40));
        checkbox.setOpaque(true);
        checkbox.setBackground(Color.WHITE);
        checkbox.setHorizontalAlignment(SwingConstants.CENTER);
        checkbox.setVerticalAlignment(SwingConstants.CENTER);

        System.out.println("DEBUG: Checkbox text: '" + checkboxText + "'");

        // Add click listener to checkbox
        checkbox.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                boolean newStatus  = taskManager.toggleTaskCompletion(task.getId());
                checkbox.setText(task.getCheckboxDisplay());

                // Add visual feedback
                if (newStatus){
                    checkbox.setForeground(Color.GREEN);
                } else {
                    checkbox.setForeground(Color.BLACK);
                }

                // Refresh the entire display
                refreshTasks();
            }
        });

        // Create main content panel
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        // Task description
        JLabel taskLabel = new JLabel(String.format("%d. %s [%s]",
            task.getId(), task.getDescription(), task.getCategory()));

        taskLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Explicit font
        taskLabel.setForeground(Color.BLACK); // Force black text
        taskLabel.setOpaque(true);
        taskLabel.setBackground(Color.WHITE);
        

        // Apply strikethrough if completed
        if (task.isCompleted()) {
            taskLabel.setText("<html><strike>" + taskLabel.getText() + "</strike></html>");
            taskLabel.setForeground(Color.GRAY);
            
        }  

        // Add test label
        // JLabel testLabel = new JLabel(" [TEST-" + task.getId() + "] ");
        // testLabel.setFont(new Font("Arial", Font.BOLD, 14));
        // testLabel.setForeground(Color.RED);
        // testLabel.setOpaque(true);
        // testLabel.setBackground(Color.CYAN);
        
        // Add components to content panel
        contentPanel.add(taskLabel);
        //contentPanel.add(testLabel);

        // Add selection functionality
        MouseAdapter selectionListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectTask(panel, task);
            }
        };
        
        panel.addMouseListener(selectionListener);
        taskLabel.addMouseListener(selectionListener);
        contentPanel.addMouseListener(selectionListener);

        // Add components to main panel using BorderLayout
        panel.add(checkbox, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;

    }

    private void selectTask(JPanel panel, Task task){
        // Clear previous selection
        for(Component comp: tasksContainer.getComponents()){
            if (comp instanceof JPanel){
                comp.setBackground(null);
                ((JPanel) comp).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5,  5, 5)
                ));
            }
        }

        // Highlight selected panel
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(5, 5,  5, 5)
        ));

        selectedTask = task;
        panel.repaint();
    }

    private void addTask() {
        String description = JOptionPane.showInputDialog(this, "Enter task description:");
        if (description == null || description.trim().isEmpty()) return;
        String[] categories = {"PERSONAL", "WORK", "HOBBY", "OTHER"};
        String categoryStr = (String) JOptionPane.showInputDialog(this, "Select category:", "Category", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
        if (categoryStr == null) return;
        Category category = Category.valueOf(categoryStr);
        Task task = new Task(taskManager.getNextId(), description, category);
        taskManager.addTask(task);
        refreshTasks();
    }

    private void updateTask() {
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Select a task to update.");
            return;
        }
        String description = JOptionPane.showInputDialog(this, "Edit description:", selectedTask.getDescription());
        if (description == null || description.trim().isEmpty()) return;
        String[] categories = {"PERSONAL", "WORK", "HOBBY", "OTHER"};
        String categoryStr = (String) JOptionPane.showInputDialog(this, "Select category:", "Category", JOptionPane.QUESTION_MESSAGE, null, categories, selectedTask.getCategory().toString());
        if (categoryStr == null) return;
        Category category = Category.valueOf(categoryStr);
        int confirm = JOptionPane.showConfirmDialog(this, "Is the task completed?", "Completed", JOptionPane.YES_NO_OPTION);
        boolean isCompleted = (confirm == JOptionPane.YES_OPTION);
        taskManager.updateTask(selectedTask.getId(), description, category, isCompleted);
        refreshTasks();
    }

    private void deleteTask() {
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Select a task to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskManager.deleteTask(selectedTask.getId());
            refreshTasks();
        }
    }

    private void refreshTasks() {
        tasksContainer.removeAll();
        selectedTask = null;

        for (Task task : taskManager.getTasks()){
            JPanel taskPanel = createTaskPanel(task);
            tasksContainer.add(taskPanel);
        }
        tasksContainer.revalidate();
        tasksContainer.repaint();
    }
}
