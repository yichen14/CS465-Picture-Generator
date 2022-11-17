package com.example.picgenerator_.ui.home;

public class Task_model {
    String task_name;
    String task_status;
    String style;

    public String getStyle() {
        return style;
    }

    public Task_model(String task_name, String style , String task_status) {
        this.task_name = task_name;
        this.task_status = task_status;
        this.style = style;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }
}
