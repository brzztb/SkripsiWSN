package com.example.aplikasiwsn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewMenuAdapter;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.services.NodeCountService;
import com.example.aplikasiwsn.services.PetakCountService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecycleViewMenuAdapter.ItemClickListener {

    RecycleViewMenuAdapter menuAdapter;
    ImageView btn_back;
    CredentialSharedPreferences cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btn_back = findViewById(R.id.btn_back);
        this.btn_back.setVisibility(View.GONE);
        cd = new CredentialSharedPreferences(this);

        PetakCountService petakCountService = AppAPI.getRetrofit().create(PetakCountService.class);
        petakCountService.getPetakCount().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                cd.saveJumlahKodePetak(response.body());

                NodeCountService nodeCountService = AppAPI.getRetrofit().create(NodeCountService.class);
                nodeCountService.getNodeCount().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        cd.saveJumlahNodeSensing(response.body());
                        // data to populate the RecyclerView with
                        String[] data = {"Cek Status", "Sensing", "History", "My Crop", "My Chart", "Logout"};

                        // set up the RecyclerView
                        RecyclerView recyclerView = findViewById(R.id.rvMenu);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        menuAdapter = new RecycleViewMenuAdapter(MainActivity.this, data);
                        recyclerView.setAdapter(menuAdapter);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}