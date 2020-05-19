package com.example.retrofitapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsumerSubiscribe {

    @SerializedName("topics")
    @Expose
    private List<String> recordsList = null;

    public List<String> getRecordsList() {
        return recordsList;
    }

    public void setRecordsList(List<String> recordsList) {
        this.recordsList = recordsList;
    }
}
