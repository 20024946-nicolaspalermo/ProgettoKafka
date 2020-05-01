package com.example.retrofitapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface KafkaApi {

    @Headers({
            "Accept: application/vnd.kafka.v2+json",
            "Content-Type: application/vnd.kafka.v2+json",
            "User-Agent: com.example.retrofitapp"
    })
    @GET("topics/")
    Call <List<String>> getTopics();
}
