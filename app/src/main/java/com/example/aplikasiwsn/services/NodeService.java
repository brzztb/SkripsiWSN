package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.NodeSensorStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NodeService {
    @GET("status")
    Call<ArrayList<NodeSensorStatus>> getNodeSensor();
}