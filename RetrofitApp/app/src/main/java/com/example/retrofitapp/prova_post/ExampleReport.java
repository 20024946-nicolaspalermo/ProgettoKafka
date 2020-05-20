package com.example.retrofitapp.prova_post;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleReport {

    @SerializedName("records")
    @Expose
    private List<Report> records = null;

    public List<Report> getReports() {
        return records;
    }

    public void setReports(List<Report> reports) {
        this.records = reports;
    }

}