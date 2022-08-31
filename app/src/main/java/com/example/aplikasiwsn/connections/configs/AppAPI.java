package com.example.aplikasiwsn.connections.configs;

import java.sql.Ref;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppAPI {
    //Emulator
    //10.0.2.2

    private static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
