package com.example.picgenerator_.ui.home;

import java.util.List;

public class Task_model {
    String task_name;
    String task_status;
    String style;
    List<String> img_urls;
    public Task_model(String task_name, String style , String task_status, List<String> img_urls) {
        this.task_name = task_name;
        this.task_status = task_status;
        this.style = style;
        this.img_urls = img_urls;
    }
    public List<String> getImg_urls() {
        return img_urls;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setImg_urls(List<String> img_urls) {
        this.img_urls = img_urls;
    }

    public String getStyle() {
        return style;
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
