package com.example.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
                .baseUrl("http://192.168.1.10:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);

        final Example example = new Example();
        final ArrayList<Records> recordsArrayList = new ArrayList<>();
        final Records records = new Records();
        final Records recors_2 = new Records();
        records.setKey("Name");
        records.setValue("LLLLL");
        recordsArrayList.add(records);
        recors_2.setKey("Description");
        recors_2.setValue("AAAAA");
        recordsArrayList.add(recors_2);

        example.setRecordsList(recordsArrayList);

        Call<Void> call = kafkaApi.createPost(example);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    texvtViewResult.setText("Code: " + response.code());
                    return;
                }
                System.out.println("AAAAA" + recordsArrayList.toString());
                response.body();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("2");
                if (t instanceof IOException)
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();

                texvtViewResult.setText(t.getMessage());

            }
        });

      /*  Call <List<String>> call = kafkaApi.getTopics();

       call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    texvtViewResult.setText("Code: " + response.code());
                    return;
                }
                List<String> topics = response.body();
                texvtViewResult.append(topics.toString());

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("2");
                if (t instanceof IOException)
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();

                texvtViewResult.setText(t.getMessage());
            }
        });*/
    }

}
