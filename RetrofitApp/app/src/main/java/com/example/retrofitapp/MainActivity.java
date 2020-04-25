package com.example.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView texvtViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texvtViewResult = findViewById(R.id.text_view_result);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://localhost:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);

        Call<List<Topics>> call = kafkaApi.getTopics();

        call.enqueue(new Callback<List<Topics>>() {
            @Override
            public void onResponse(Call<List<Topics>> call, Response<List<Topics>> response) {
                if(!response.isSuccessful()){
                    texvtViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Topics> topics = response.body();

                for(Topics topic: topics){
                    String content = "";
                    content += "Topics: " + topic.getTopics() + "\n";

                    texvtViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Topics>> call, Throwable t) {
                texvtViewResult.setText(t.getMessage());
            }
        });
    }
}
