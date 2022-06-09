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
import com.example.aplikasiwsn.adapters.RecycleViewHistoryAdapter;
import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.Tanah;
import com.example.aplikasiwsn.services.TanahService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryActivity extends AppCompatActivity {
    RecycleViewHistoryAdapter historyAdapter;
    ImageView btn_back;
    TextView toolbarName;
    private ArrayList<Tanah> tanahArrayListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.153:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Sensing History");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TanahService tanahService = retrofit.create(TanahService.class);

        final ProgressDialog progressDialog = new ProgressDialog(HistoryActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        tanahService.getNodeSensor().enqueue(new Callback<ArrayList<Tanah>>() {
            @Override
            public void onResponse(Call<ArrayList<Tanah>> call, Response<ArrayList<Tanah>> response) {
                progressDialog.dismiss(); //dismiss progress dialog
                tanahArrayListData = response.body();
                setDataInRecycleView();
            }

            @Override
            public void onFailure(Call<ArrayList<Tanah>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });

    }

    private void setDataInRecycleView() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new RecycleViewHistoryAdapter(this, tanahArrayListData);
        recyclerView.setAdapter(historyAdapter);
    }
}
