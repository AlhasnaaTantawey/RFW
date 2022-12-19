package com.example.reducefoodewaste.Retrofit;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL = "https://api.logmeal.es/";
    public static Retrofit retrofit = null;
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @NotNull
                        @Override
                        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                            Request originalRequest = chain.request();
                            Request newRequest = originalRequest.newBuilder()
                                    .build();

                            return chain.proceed(newRequest);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}




//    public static Retrofit retrofit = null;
//    public static Retrofit getRetrofit() {
//        if (retrofit == null) {
//            Gson gson = new GsonBuilder().serializeNulls().create();
//
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new Interceptor() {
//                        @NotNull
//                        @Override
//                        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
//                            Request originalRequest = chain.request();
//                            Request newRequest = originalRequest.newBuilder()
//                                    .build();
//
//                            return chain.proceed(newRequest);
//                        }
//                    })
//                    .addInterceptor(loggingInterceptor)
//                    .build();
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }