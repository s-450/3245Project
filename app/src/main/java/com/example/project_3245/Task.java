package com.example.project_3245;

public class Task {
    private String taskId;
    private String taskName;
    private String taskDescription;
    private String taskDate;
    private String taskTime;
    private String taskPriority;

    public Task() {}



    public Task(String taskName, String taskDescription, String taskDate, String taskTime, String taskPriority) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskPriority = taskPriority;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskPriority() {
        return taskPriority;
    }
    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }
}
