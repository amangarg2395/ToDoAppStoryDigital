package com.amangarg.todoapp.model;

/**
 * Created by amangarg on 6/29/18.
 */

public class TaskData {
    int TaskID, CategoryID;
    String TaskTitle, TaskDescription, TaskImage;
    Boolean TaskStatus;

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setTaskTitle(String taskTitle) {
        TaskTitle = taskTitle;

    }

    public String getTaskTitle() {
        return TaskTitle;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;

    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public String getTaskImage() {
        return TaskImage;
    }

    public void setTaskImage(String taskImage) {
        TaskImage = taskImage;
    }

    public Boolean getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(Boolean taskStatus) {
        TaskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "TaskData {taskID-" + TaskID + ", taskTitle-" + TaskTitle + ", taskDescription-" + TaskDescription + ", taskImage-" + TaskImage + ", taskStatus-" + TaskStatus + ", categoryID-" + CategoryID + "}";
    }

}
