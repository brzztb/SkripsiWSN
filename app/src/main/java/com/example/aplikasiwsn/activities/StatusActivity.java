package com.example.aplikasiwsn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewMenuAdapter;
import com.example.aplikasiwsn.adapters.RecycleViewStatusAdapter;
import com.example.aplikasiwsn.models.NodeSensor;

import java.util.ArrayList;
import java.util.Locale;

public class StatusActivity extends AppCompatActivity {

    RecycleViewStatusAdapter statusAdapter;
    private ArrayList<NodeSensor> arrOfNodes;
    ImageView btn_back;
    TextView toolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        arrOfNodes = new ArrayList<>();
        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Status Node");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addArrOfNodes(R.drawable.tes_icon, "Raspberry Pi 3 B+", "Online", "Offline");
        addArrOfNodes(R.drawable.tes_icon, "Node 1", "Offline", "Offline");
        addArrOfNodes(R.drawable.tes_icon, "Node 2", "Online", "Offline");
        addArrOfNodes(R.drawable.tes_icon, "Node 3", "Online", "Offline");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        statusAdapter = new RecycleViewStatusAdapter(this, arrOfNodes);
        recyclerView.setAdapter(statusAdapter);
    }

    private void addArrOfNodes(Integer img, String name, String status, String statusSensing) {
        NodeSensor nodeSensor = new NodeSensor(img, name, status, statusSensing);
        arrOfNodes.add(nodeSensor);
    }
}
