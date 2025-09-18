package net.javaguides.todo.gui;

import javax.swing.*;
import java.awt.*;
import net.javaguides.todo.TaskManager;

public class TodoAppFrame extends JFrame {
    private TaskPanel taskPanel;
    @SuppressWarnings("unused")
    private TaskManager taskManager;

    public TodoAppFrame(TaskManager taskManager) {
        this.taskManager = taskManager;
        setTitle("To-Do List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        taskPanel = new TaskPanel(taskManager);
        add(taskPanel, BorderLayout.CENTER);
    }
}
