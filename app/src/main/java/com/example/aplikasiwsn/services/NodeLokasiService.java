package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.NodeLokasi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NodeLokasiService {
    @GET("nodelokasi")
    Call<ArrayList<NodeLokasi>> getNodeLokasi();
}
