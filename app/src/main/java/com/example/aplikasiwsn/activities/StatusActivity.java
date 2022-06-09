package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewMenuAdapter;
import com.example.aplikasiwsn.adapters.RecycleViewStatusAdapter;
import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.NodeSensorStatus;
import com.example.aplikasiwsn.services.NodeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusActivity extends AppCompatActivity {

    RecycleViewStatusAdapter statusAdapter;
    ImageView btn_back;
    TextView toolbarName;
    private ArrayList<NodeSensorStatus> nodeArrayListStatusData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.153:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Status Node");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        NodeService nodeService = retrofit.create(NodeService.class);

        final ProgressDialog progressDialog = new ProgressDialog(StatusActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        nodeService.getNodeSensor().enqueue(new Callback<ArrayList<NodeSensorStatus>>() {
            @Override
            public void onResponse(Call<ArrayList<NodeSensorStatus>> call, Response<ArrayList<NodeSensorStatus>> response) {
                progressDialog.dismiss(); //dismiss progress dialog
                nodeArrayListStatusData = response.body();
                setDataInRecycleView();
            }

            @Override
            public void onFailure(Call<ArrayList<NodeSensorStatus>> call, Throwable t) {
                Toast.makeText(StatusActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }

    private void setDataInRecycleView() {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        statusAdapter = new RecycleViewStatusAdapter(this, nodeArrayListStatusData);
        recyclerView.setAdapter(statusAdapter);
    }
}
