package net.javaguides.todo;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String description;
    private Category category;
    private boolean isCompleted;

    public Task(int id, String description, Category category){
        this.id = id;
        this.description = description;
        this.category = category;
    }

    // Getter and Setters
    public int getId() {return id;}
    public String getDescription(){return description; }
    public void setDescription(String description){ this.description = description; }
    public Category getCategory() { return category; }
    public void setCategory(Category category){ this.category = category; }
    public boolean isCompleted(){ return isCompleted; }
    public void setCompleted(boolean completed){ isCompleted = completed; }

    // Toggle completion status
    public void toggleCompletion (){
        this.isCompleted = !this.isCompleted;
    }


    @Override
    public String toString(){
        String statusIcon = isCompleted ? "✓" : "○";
        return String.format("%d. %s %s [%s]", id, statusIcon, description, category);
    }

    // Method to get checkbox part
    public String getCheckboxDisplay(){
        return isCompleted ? "✓" : "○";
    }
}