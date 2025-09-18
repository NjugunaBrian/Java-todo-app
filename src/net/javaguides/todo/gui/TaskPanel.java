
package net.javaguides.todo.gui;

import javax.swing.*;
import java.awt.*;
import net.javaguides.todo.*;

public class TaskPanel extends JPanel {
    private TaskManager taskManager;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JButton addButton, updateButton, deleteButton, refreshButton;

    public TaskPanel(TaskManager taskManager) {
        this.taskManager = taskManager;
        setLayout(new BorderLayout());

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
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
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task to update.");
            return;
        }
        String description = JOptionPane.showInputDialog(this, "Edit description:", selected.getDescription());
        if (description == null || description.trim().isEmpty()) return;
        String[] categories = {"PERSONAL", "WORK", "HOBBY", "OTHER"};
        String categoryStr = (String) JOptionPane.showInputDialog(this, "Select category:", "Category", JOptionPane.QUESTION_MESSAGE, null, categories, selected.getCategory().toString());
        if (categoryStr == null) return;
        Category category = Category.valueOf(categoryStr);
        int confirm = JOptionPane.showConfirmDialog(this, "Is the task completed?", "Completed", JOptionPane.YES_NO_OPTION);
        boolean isCompleted = (confirm == JOptionPane.YES_OPTION);
        taskManager.updateTask(selected.getId(), description, category, isCompleted);
        refreshTasks();
    }

    private void deleteTask() {
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a task to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskManager.deleteTask(selected.getId());
            refreshTasks();
        }
    }

    private void refreshTasks() {
        taskListModel.clear();
        for (Task t : taskManager.getTasks()) {
            taskListModel.addElement(t);
        }
    }
}
