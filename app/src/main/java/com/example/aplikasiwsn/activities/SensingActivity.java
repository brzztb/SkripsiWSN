package com.example.aplikasiwsn.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.adapters.RecycleViewSensingAdapter;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.Tanah;
import com.example.aplikasiwsn.services.SensingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SensingActivity extends AppCompatActivity {

    RecycleViewSensingAdapter sensingAdapter;
    ImageView btn_back;
    TextView toolbarName;
    private ArrayList<Tanah> sensingArrayListData  = new ArrayList<>();
    Timer timer;
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensing);

        RecyclerView recyclerView = findViewById(R.id.rvSensing);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensingAdapter = new RecycleViewSensingAdapter(this, sensingArrayListData);
        recyclerView.setAdapter(sensingAdapter);

        AppAPI.getRetrofit();

        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Sensing");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setRepeatingAsyncTask();
    }

    private void setRepeatingAsyncTask() {
        final Handler handler = new Handler();
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            SensingAsyncTask sensingAsyncTask = new SensingAsyncTask();
                            sensingAsyncTask.execute();
                        } catch (Exception e) {
                            Toast.makeText(SensingActivity.this, "AsyncTask failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.schedule(task, 0, 1*1000);  // interval of one minute
    }

    private class SensingAsyncTask extends AsyncTask<String, Void, ArrayList<Tanah>> {
        @Override
        protected ArrayList<Tanah> doInBackground(String... strings) {
            SensingService sensingService = AppAPI.getRetrofit().create(SensingService.class);
            try {
                return sensingService.getSensing().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(SensingActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
            }
            return new ArrayList<Tanah>();
        }

        @Override
        protected void onPostExecute(ArrayList<Tanah> result) {
            sensingAdapter.changeData(result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
