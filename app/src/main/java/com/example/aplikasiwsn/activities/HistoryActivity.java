package com.example.aplikasiwsn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewHistoryAdapter;
import com.example.aplikasiwsn.models.NodeSensor;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecycleViewHistoryAdapter historyAdapter;
    private ArrayList<NodeSensor> arrOfNodes;
    ImageView btn_back;
    TextView toolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        arrOfNodes = new ArrayList<>();
        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Sensing History");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");
        addArrOfNodes("Node 1", "4.4", "2.5%", "25C", "23C", "2022-01-01 12:00:00");
        addArrOfNodes("Node 2", "1.4", "5.0%", "22C", "22C", "2022-01-01 12:10:00");
        addArrOfNodes("Node 3", "1.67", "3.7%", "21C", "35C", "2022-01-01 12:53:00");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new RecycleViewHistoryAdapter(this, arrOfNodes);
        recyclerView.setAdapter(historyAdapter);
    }

    private void addArrOfNodes(String name, String ph, String kelembaban, String tanah, String udara, String waktu) {
        NodeSensor nodeSensor = new NodeSensor(name, ph, kelembaban, tanah, udara, waktu);
        arrOfNodes.add(nodeSensor);
    }
}
