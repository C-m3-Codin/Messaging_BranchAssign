package com.example.branchassign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        MaterialButton loginButton = (MaterialButton)  findViewById(R.id.loginBut);
        super.onCreate(savedInstanceState);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(username.getText().toString(),password.getText().toString());
                loginUser(user);
//                if(loginUser(user)) {
//                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT);
//                    loginButton.setText("Logged In");
//                    fetchMessages();
//
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Login Fail",Toast.LENGTH_SHORT);
//                    loginButton.setText("wrong Password ");
//
//                }

            }
        });

    }
    private static boolean LoggedIn = false;

    public  boolean loginUser(User user){

        RetrofitIns.getRetrofitClient().loginUser(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println(" \n\n\n\n logged in " + response.isSuccessful());

                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();


                    SharedPreferences Shared_pref;
                    Shared_pref = getSharedPreferences("authentication", MODE_PRIVATE);
                    SharedPreferences.Editor editor = Shared_pref.edit();
                    editor.putString("token", response.body().getAuth_token());
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this,MessageHome.class);
                    intent.putExtra("auth", response.body().getAuth_token());
                    startActivity(intent);
                }
                else{

                    System.out.println("\n\n\n\n baaaaam");
                    Toast.makeText(MainActivity.this, "Incorrect username or Password", Toast.LENGTH_SHORT).show();

                }
//                    loginButton.setText("Logged In");
//                    fetchMessages();
                    LoggedIn=response.isSuccessful();
//                user.setAuth_token();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Reason can not be blank", Toast.LENGTH_SHORT).show();
                System.out.println(" \n\n\n\n not logged in " + t.toString());

            }
        });
        return  LoggedIn;
    }

    public  void fetchMessages(){
        RetrofitIns.getRetrofitClient().getMessages("hmbo6sRnY24ikRaP_l3LGw").enqueue(new Callback<List<PostPojo>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<PostPojo>> call, Response<List<PostPojo>> response) {
                if(response.isSuccessful() && response.body()!=null){
//                    loginButton.setText("data fetched");
                    System.out.println("\n\n\n\n");
                    System.out.println(response.body().get(2).getThread_id());

                }

            }

            @Override
            public void onFailure(Call<List<PostPojo>> call, Throwable t) {

            }
        });

    }







}