package com.example.aplikasiwsn.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PetakCountService {
    @GET("petakcount")
    Call<String> getPetakCount();
}
