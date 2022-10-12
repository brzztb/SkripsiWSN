package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
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
import com.example.aplikasiwsn.adapters.RecycleViewStatusAdapter;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.NodeSensorStatus;
import com.example.aplikasiwsn.services.NodeService;

import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {

    RecycleViewStatusAdapter statusAdapter;
    ImageView btn_back;
    TextView toolbarName;
    private ArrayList<NodeSensorStatus> nodeArrayListStatusData = new ArrayList<>();
    Timer timer;
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        RecyclerView recyclerView = findViewById(R.id.rvStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        statusAdapter = new RecycleViewStatusAdapter(this, nodeArrayListStatusData);
        recyclerView.setAdapter(statusAdapter);

        AppAPI.getRetrofit();

        this.btn_back = findViewById(R.id.btn_back);
        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("Status Node");

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        NodeService nodeService = AppAPI.getRetrofit().create(NodeService.class);
//
//        final ProgressDialog progressDialog = new ProgressDialog(StatusActivity.this);
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage("Please Wait"); // set message
//        progressDialog.show(); // show progress dialog
//
//        nodeService.getNodeSensor().enqueue(new Callback<ArrayList<NodeSensorStatus>>() {
//            @Override
//            public void onResponse(Call<ArrayList<NodeSensorStatus>> call, Response<ArrayList<NodeSensorStatus>> response) {
//                progressDialog.dismiss(); //dismiss progress dialog
//                statusAdapter.changeData(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<NodeSensorStatus>> call, Throwable t) {
//                Toast.makeText(StatusActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
//                progressDialog.dismiss(); //dismiss progress dialog
//            }
//        });
        setRepeatingAsyncTask();
    }

    private void setRepeatingAsyncTask() {
        final Handler handler = new Handler();
        timer =  new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            StatusAsyncTask statusAsyncTask = new StatusAsyncTask();
                            statusAsyncTask.execute();
                        } catch (Exception e) {
                            // error, do something
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

    private class StatusAsyncTask extends AsyncTask<String, Void, ArrayList<NodeSensorStatus>> {
        @Override
        protected ArrayList<NodeSensorStatus> doInBackground(String... strings) {
            NodeService nodeService = AppAPI.getRetrofit().create(NodeService.class);
            try {
                return nodeService.getNodeSensor().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<NodeSensorStatus>();
        }

        @Override
        protected void onPostExecute(ArrayList<NodeSensorStatus> result) {
            statusAdapter.changeData(result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
