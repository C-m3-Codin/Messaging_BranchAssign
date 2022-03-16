package com.example.branchassign;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitIns {
    private  static Retrofit retrofit=null;
    private static final String baseUrl = "https://android-messaging.branch.co/";

   public static ApiInterface getRetrofitClient(){
       if(retrofit==null){
           retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
       }
       return  retrofit.create(ApiInterface.class);

   }
}
