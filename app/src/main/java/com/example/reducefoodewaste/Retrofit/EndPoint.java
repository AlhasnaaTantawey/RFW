package com.example.reducefoodewaste.Retrofit;

import com.squareup.okhttp.RequestBody;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPoint {
    @Multipart
    @POST("v2/image/recognition/type/v1.0")
   //Bearer "+ "e7c57dfb3b94fa2a2b62eb6328e1eaface810a77"
    Call<DetectFood> determineFoodOrNot(@Part ()MultipartBody.Part image,@Header("Authorization") String authHeader) ;




}
