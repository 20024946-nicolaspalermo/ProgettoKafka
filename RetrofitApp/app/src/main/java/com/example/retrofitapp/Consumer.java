package com.example.retrofitapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Consumer {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("auto.offset.reset")
    @Expose
    private  String autoOffeset;

    @SerializedName("auto.commit.enable")
    @Expose
    private String autoCommit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAutoOffeset() {
        return autoOffeset;
    }

    public void setAutoOffeset(String autoOffeset) {
        this.autoOffeset = autoOffeset;
    }

    public String getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(String autoCommit) {
        this.autoCommit = autoCommit;
    }
}
