package com.example.aplikasiwsn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewMenuAdapter;

public class MainActivity extends AppCompatActivity implements RecycleViewMenuAdapter.ItemClickListener {

    RecycleViewMenuAdapter menuAdapter;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btn_back = findViewById(R.id.btn_back);
        this.btn_back.setVisibility(View.GONE);

        // data to populate the RecyclerView with
        String[] data = {"Cek Status", "Sensing", "History", "My Crop", "Logout"};

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuAdapter = new RecycleViewMenuAdapter(this, data);
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}