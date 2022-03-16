package com.example.branchassign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageHome extends AppCompatActivity implements  RecyclerViewInterface {

    Bundle intentExtras;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    PostsAdapter adapter;
    List<PostPojo> postList =  new ArrayList<>();
    Map<String, List<Message>> threads = new HashMap<>();
    String authString=" ";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_home);
        intentExtras = getIntent().getExtras();

        SharedPreferences newPreference;
        newPreference = getSharedPreferences("authentication", MODE_PRIVATE);
        authString = newPreference.getString("token",null);
        System.out.println("auth related"+authString.toString());


        recyclerView = findViewById(R.id.recyclerMessages);
        progressBar = findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager((this));
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter  = new PostsAdapter(threads,this);
        recyclerView.setAdapter(adapter);

//       recyclerView.setAdapter(adapter);
       fetchMessages();


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onRestart() {
// TODO Auto-generated method stub
        super.onRestart();


        intentExtras = getIntent().getExtras();

        SharedPreferences newPreference;
        newPreference = getSharedPreferences("authentication", MODE_PRIVATE);
        authString = newPreference.getString("token",null);
        System.out.println("auth related"+authString.toString());


        recyclerView = findViewById(R.id.recyclerMessages);
        progressBar = findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager((this));
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter  = new PostsAdapter(threads,this);
        recyclerView.setAdapter(adapter);

//       recyclerView.setAdapter(adapter);
        fetchMessages();

        //Do your code here
        System.out.println("getting back ");
    }

    public  void fetchMessages(){

        progressBar.setVisibility(View.VISIBLE);
        RetrofitIns.getRetrofitClient().getMessages(authString).enqueue(new Callback<List<PostPojo>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<PostPojo>> call, Response<List<PostPojo>> response) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                if(response.isSuccessful() && response.body()!=null){

                    System.out.println("hey there  times here" + response.body().size());

                    for(int i=0;i<response.body().size();i++){

                        LocalDateTime localDate=LocalDateTime.parse(response.body().get(i).getTimestamp(),inputFormatter);
                        response.body().get(i).setTime(localDate);
//                       System.out.println("times here" + localDate.toString());

                    }
//                    Collections.sort(response.body());

                    for(int i=0;i<response.body().size();i++){
                        String threadId= response.body().get(i).getThread_id()+"";
                        if(threads.containsKey(threadId)){
                            List<Message> temp = threads.get(threadId);
                            temp.add(new Message(response.body().get(i).getTimestamp(),response.body().get(i).getBody(),response.body().get(i).getTime(),response.body().get(i).getAgent_id(),response.body().get(i).getThread_id()));
                            threads.put(threadId,temp);
                        }
                        else{
                            List<Message> temp = new ArrayList<Message>() ;
                            temp.add(new Message(response.body().get(i).getTimestamp(),response.body().get(i).getBody(),response.body().get(i).getTime(),response.body().get(i).getAgent_id(),response.body().get(i).getThread_id()));

                            threads.put(threadId,temp);

                        }
                    }
                    threads.forEach((k,v)->{
                        System.out.println("\n\n\nmessage her new thread "+ k.toString());
                        Collections.sort(v,Collections.reverseOrder());
                        v.forEach((a)->{
                            System.out.println( "message here =for auth  "+authString+ a.getBody() + "\n\n");
                        });
                        threads.put(k,v);
                    });
                    postList.addAll(response.body());






                    adapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
//                    loginButton.setText("data fetched");
                    System.out.println("\n\n\n\n here comes the val" + threads.toString());

                    System.out.println(response.body().get(2).getThread_id());

                }

            }

            @Override
            public void onFailure(Call<List<PostPojo>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onItemClick(int position) {


        Intent intent = new Intent(this,dmMessage.class);
        String[] asd = threads.keySet().toArray(new String[postList.size()]);

//        holder.tvTitle.setText(postList.get(asd[position]).get(0).getTimestamp());
        System.out.println("here lol" + threads.get(asd[position]));
        intent.putExtra("MessagesHere",(Serializable) threads.get(asd[position]));
        startActivity(intent);

    }
}