package com.example.retrofitapp;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface KafkaApi {



    @GET("topics/")
    Call <List<String>> getTopics();

    @Headers({  "Host: com.example.retrofitapp",
                "Content-Type: application/vnd.kafka.json.v2+json",
                "Accept: application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json"

    })
    @POST("topics/prova_topic_new")
    Call<Void>createPost(@Body Example example);


    @Headers({  "Host: com.example.retrofitapp",
            "Content-Type: application/vnd.kafka.json.v2+json",
            "Accept: application/vnd.kafka.v2+json"
    })
    @POST("consumers/my_json_consumer")
    Call<Void>createConsumer(@Body Consumer consumer);


    @Headers("Content-Type: application/vnd.kafka.v2+json")
    @POST("consumers/my_json_consumer/instances/prova26/subscription")
    Call<Void> subscribeConsumer(@Body ConsumerSubiscribe consumerSubiscribe);

    @Headers("Accept: application/vnd.kafka.json.v2+json")
    @GET("consumers/my_json_consumer/instances/prova26/records")
    Call<List<GetConsumer>> getConsumerJson();

}
