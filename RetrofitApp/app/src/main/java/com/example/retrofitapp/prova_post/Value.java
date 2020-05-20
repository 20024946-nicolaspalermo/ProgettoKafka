package com.example.retrofitapp.prova_post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Time")
    @Expose
    private String time;

    @SerializedName("Position")
    @Expose
    private String position;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Category")
    @Expose
    private String category;

    @SerializedName("Priority")
    @Expose
    private String priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}