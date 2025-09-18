package net.javaguides.todo.gui;

import javax.swing.SwingUtilities;

import net.javaguides.todo.TaskManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManager taskManager = new TaskManager();
            TodoAppFrame frame = new TodoAppFrame(taskManager);
            frame.setVisible(true);
        });
    }
}
