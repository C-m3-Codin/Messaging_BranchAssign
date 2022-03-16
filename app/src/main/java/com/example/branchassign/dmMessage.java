package com.example.branchassign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dmMessage extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    List<Message> messageList;
    Context context;

    String auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_message);
        SharedPreferences newPreference;
        newPreference = getSharedPreferences("authentication", MODE_PRIVATE);
        auth = newPreference.getString("token",null);
        System.out.println("auth related"+auth.toString());

        Button sendButton = (Button) findViewById(R.id.button_gchat_send);
        EditText messageText = (EditText)findViewById(R.id.edit_gchat_message);
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        messageList = (List<Message>) getIntent().getSerializableExtra("MessagesHere");
        System.out.println("here lol ");
        context=this;
        System.out.println("got it here baam " +messageList.get(0).getBody());
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                Message message = new Message(time.format(dateTimeFormatter),messageText.getText().toString(),time,null, messageList.get(0).getThreadId());
                sendMessages(message);

            }
        });



    }
    public void sendMessages(Message newMessage){

System.out.println("auth ngot here" +auth);
        System.out.println("new message here is "+newMessage.getBody()+ " thread "+ newMessage.getThreadId());
        SendMessage sendmessage =  new SendMessage(newMessage.getBody(),Math.round(newMessage.getThreadId()));
        RetrofitIns.getRetrofitClient().sendMessage(auth,
                sendmessage.body,newMessage.getThreadId()
        ).enqueue(new Callback<PostPojo>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<PostPojo> call, Response<PostPojo> response) {

                if(response.isSuccessful()){



//                    System.out.println("new Meessage  "+response.headers());
//                    System.out.println("new Meessage  "+response.errorBody());
                    System.out.println("new Meessage  success"+response.body().getBody());
                    System.out.println(response.toString());

//                    private Float threadId;
//                    private String timestamp;
//                    private String body;
//                    private LocalDateTime time;
                    LocalDateTime time = LocalDateTime.now();

                    Message newMessage = new  Message(response.body().getTimestamp(),response.body().getBody(),time,response.body().getAgent_id(),response.body().getThread_id());
                    messageList.add(newMessage);
                    mMessageAdapter = new MessageListAdapter(context, messageList);
                    mMessageRecycler.setLayoutManager(new LinearLayoutManager(context));
                    mMessageRecycler.setAdapter(mMessageAdapter);
                    mMessageAdapter.notifyDataSetChanged();




                }
                else{
                    System.out.println("new Meessag error    \n "+response.toString());
//
//                    System.out.println("new Meessage  "+response.headers());
//                    System.out.println("new Meessage  "+response.errorBody());
                    System.out.println("new Meessage error    \n "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PostPojo> call, Throwable t) {
                System.out.println("new Meessag " + t.toString());

            }
        });

    }



}