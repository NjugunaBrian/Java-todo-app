# Java To-Do List Application

This is a simple To-Do List application built with Java. It features both a **console-based interface** and a **modern GUI** using Java Swing. You can add, update, delete, and view tasks, with each task assigned a category and completion status. Tasks are saved to disk and persist between runs.

## Features

- **Add, update, delete, and list tasks**
- **Task categories:** PERSONAL, WORK, HOBBY, OTHER
- **Mark tasks as completed**
- **Persistent storage** (tasks are saved to `tasks.dat`)
- **Console and GUI interfaces** (choose which to run)

## Directory Structure

```
src/
└── net
    └── javaguides
        └── todo
            ├── Category.java
            ├── Main.java           # Console interface
            ├── Task.java
            ├── TaskManager.java
            └── gui
                ├── Main.java       # GUI launcher
                ├── TaskPanel.java
                └── TodoAppFrame.java
```

## How to Run

### 1. Run the Console Application

```sh
javac -d bin src/net/javaguides/todo/*.java
java -cp bin net.javaguides.todo.Main
```

### 2. Run the GUI Application

```sh
javac -d bin src/net/javaguides/todo/*.java src/net/javaguides/todo/gui/*.java
java -cp bin net.javaguides.todo.gui.Main
```

## Requirements

- Java 8 or higher

## Usage

- **Console:** Follow the menu prompts to manage your tasks.
- **GUI:** Use buttons to add, update, delete, and refresh tasks. Click on a task to select it. Click the checkbox to toggle completion.

## Notes

- All tasks are saved in `tasks.dat` in the working directory.
- If you encounter errors, ensure all `.java` files are compiled and your Java version is up to date.

## License

This project is provided for educational purposes.