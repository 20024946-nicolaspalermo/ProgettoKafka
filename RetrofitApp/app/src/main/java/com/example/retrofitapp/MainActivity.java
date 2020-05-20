package com.example.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitapp.prova_post.ExampleReport;
import com.example.retrofitapp.prova_post.Report;
import com.example.retrofitapp.prova_post.Value;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        //getTopics();
        //getPost();
        //getConsumer();
        //getSubscribeConsumer();
        getConsumeJson();

    }


    private void getTopics(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);
        Call <List<String>> call = kafkaApi.getTopics();

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
        });
    }

    private void getPost(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.9:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);

        ExampleReport exampleReport = new ExampleReport();
        ArrayList<Report> reportArrayList = new ArrayList<>();
        Report report = new Report();
        Value value = new Value();
        Value value2 = new Value();
        value.setName("Segnalazione123");
        value.setDate("12/10/981");
        value.setTime("11:281");
        value.setPosition("Milano2");
        value.setDescription("Descrizione4");
        value.setCategory("Traffic4");
        value.setPriority("Green4");

        value2.setName("Segnalazione1245");
        value2.setDate("12/10/98145");
        value2.setTime("11:28415");
        value2.setPosition("Milano254");
        value2.setDescription("Descrizione44");
        value2.setCategory("Traffic44");
        value2.setPriority("Green44");

        report.setKey("reports");
        report.setValue(value);
        report.setValue(value2);
        reportArrayList.add(report);

        exampleReport.setReports(reportArrayList);
        Call<Void> call = kafkaApi.createPost(exampleReport);

        /*final Example example = new Example();
        final ArrayList<Records> recordsArrayList = new ArrayList<>();
        final Records records = new Records();
        records.setKey("Nuova");
        records.setValue("Nuovo333");
        recordsArrayList.add(records);
        example.setRecordsList(recordsArrayList);*/


        //Call<Void> call = kafkaApi.createPost(example);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    texvtViewResult.setText("Code: " + response.code());
                    return;
                }
                response.body();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t instanceof IOException)
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();

                texvtViewResult.setText(t.getMessage());

            }
        });
    }

    private void getConsumer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.9:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);


        final Consumer consumer = new Consumer();
        consumer.setName("prova26");
        consumer.setFormat("json");
        consumer.setAutoOffeset("earliest");
        consumer.setAutoCommit("false");

        Call<Void> call = kafkaApi.createConsumer(consumer);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    texvtViewResult.setText("CodeGetConsumer: " + response.code());
                    return;
                }
                System.out.println("GET CONSUMER");
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t instanceof IOException)
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();

                texvtViewResult.setText(t.getMessage());
            }
        });
    }

    private void getSubscribeConsumer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.9:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);

        ConsumerSubiscribe consumerSubiscribe = new ConsumerSubiscribe();
        ArrayList<String> str = new ArrayList<>();
        str.add("prova_topic_new");
        consumerSubiscribe.setRecordsList(str);

        Call <Void> call = kafkaApi.subscribeConsumer(consumerSubiscribe);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    texvtViewResult.setText("CodeSubscribe: " + response.code());
                    return;
                }

                System.out.println("GET SUBSCRIBE");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t instanceof IOException)
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();

                texvtViewResult.setText(t.getMessage());
            }
        });
    }

    private void getConsumeJson(){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.9:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        KafkaApi kafkaApi = retrofit.create(KafkaApi.class);


         Call<List<GetConsumer>> call = kafkaApi.getConsumerJson();

                call.enqueue(new Callback<List<GetConsumer>>() {
                    @Override
                    public void onResponse(Call<List<GetConsumer>> call, Response<List<GetConsumer>> response) {
                        if(!response.isSuccessful()){
                            texvtViewResult.setText("CodeGETJSON: " + response.code());
                            return;
                        }
                        List<GetConsumer> consumers = response.body();

                        for (GetConsumer consumer: consumers){
                            Value value = new Value();

                            value.setName(consumer.getValue().getName());
                            System.out.println("AAAAAAAA " + value.getName());
                            value.setPosition(consumer.getValue().getPosition());
                            value.setDescription(consumer.getValue().getDescription());
                            value.setCategory(consumer.getValue().getCategory());
                            value.setPriority(consumer.getValue().getPriority());
                            value.setDate(consumer.getValue().getDate());
                            value.setTime(consumer.getValue().getTime());


                            String content = "";
                            content += "TOPIC: " + consumer.getTopic() + "\n";
                            content += "KEY " + consumer.getKey() + "\n";
                            consumer.setValue(value);
                            content += "VALUE " + "\n";
                            content += "    NAME: " + consumer.getValue().getName() + "\n";
                            content += "    DATE: " + consumer.getValue().getDate() + "\n";
                            content += "    TINE " + consumer.getValue().getTime() + "\n";
                            content += "    POSITION: " + consumer.getValue().getPosition() + "\n";
                            content += "    DESCRIPTION: " + consumer.getValue().getDescription() + "\n";
                            content += "    CATEGORY" + consumer.getValue().getCategory() + "\n";
                            content += "    PRIORITY" + consumer.getValue().getPriority() + "\n";

                            content += "PARTITION " + consumer.getPartition() + "\n";
                            content += "OFFSET " + consumer.getOffset() + "\n" + "\n";

                            texvtViewResult.append(content);
                            //System.out.println("CONTENT " + content);

                        }

                        System.out.println("GET CONSUMER JSON");

                    }

                    @Override
                    public void onFailure(Call<List<GetConsumer>> call, Throwable t) {
                        if (t instanceof IOException)
                            Toast.makeText(MainActivity.this, "TIME OUT CONSUME JSON", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "TIME OUT CONSUME JSON2", Toast.LENGTH_SHORT).show();

                        texvtViewResult.setText(t.getMessage());
                    }
                });

            }
}
