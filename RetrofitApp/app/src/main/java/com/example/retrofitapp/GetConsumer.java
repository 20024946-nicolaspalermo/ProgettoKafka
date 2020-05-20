package com.example.retrofitapp;

import com.example.retrofitapp.prova_post.Value;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetConsumer {
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private Value value;
    @SerializedName("partition")
    @Expose
    private Integer partition;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
