package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewSensingAdapter;
import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.Tanah;
import com.example.aplikasiwsn.services.SensingService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SensingActivity extends AppCompatActivity {

    RecycleViewSensingAdapter sensingAdapter;
    ImageView btn_back;
    TextView toolbarName;
    private ArrayList<Tanah> sensingArrayListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensing);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.48:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Sensing");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SensingService sensingService = retrofit.create(SensingService.class);

        final ProgressDialog progressDialog = new ProgressDialog(SensingActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        sensingService.getSensing().enqueue(new Callback<ArrayList<Tanah>>() {
            @Override
            public void onResponse(Call<ArrayList<Tanah>> call, Response<ArrayList<Tanah>> response) {
                progressDialog.dismiss();
                sensingArrayListData = response.body();
                setDataInRecycleView();
            }

            @Override
            public void onFailure(Call<ArrayList<Tanah>> call, Throwable t) {
                Toast.makeText(SensingActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }

    private void setDataInRecycleView() {// set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSensing);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensingAdapter = new RecycleViewSensingAdapter(this, sensingArrayListData);
        recyclerView.setAdapter(sensingAdapter);
    }
}
