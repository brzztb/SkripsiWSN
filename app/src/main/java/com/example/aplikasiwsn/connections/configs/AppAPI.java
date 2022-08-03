package com.example.aplikasiwsn.connections.configs;

import java.sql.Ref;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppAPI {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.18.160:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
