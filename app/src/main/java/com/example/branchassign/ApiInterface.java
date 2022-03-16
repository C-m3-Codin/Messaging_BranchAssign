package com.example.branchassign;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {




 @POST("api/login")
    Call<LoginResponse> loginUser(@Body User user);

    @GET("api/messages")
    Call<List<PostPojo>> getMessages(@Header("X-Branch-Auth-Token") String authtoken);

//    @Headers("X-Branch-Auth-Token:NS9yTR8j-R6cYilvciA6gg")
    @POST("api/messages")
    Call<PostPojo> sendMessage(@Header("X-Branch-Auth-Token") String auth,@Query("body") String body, @Query("thread_id") float thread_id);
}
//String authToken="NS9yTR8j-R6cYilvciA6gg";
