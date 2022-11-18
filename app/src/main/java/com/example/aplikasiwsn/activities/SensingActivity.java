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
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.Tanah;
import com.example.aplikasiwsn.services.SensingService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class SensingActivity extends AppCompatActivity {

    RecycleViewSensingAdapter sensingAdapter;
    SimpleDateFormat sdf;
    ImageView btn_back;
    TextView toolbarName;
    private ArrayList<Tanah> sensingArrayListData = new ArrayList<>();
    Timer timer;
    TimerTask task;
    HashMap<String, String> sinceList;
    CredentialSharedPreferences cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensing);

        sinceList = new HashMap<>();
        cd = new CredentialSharedPreferences(this);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
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
        timer.schedule(task, 0, 1 * 5000);  // interval of one minute
    }

    private class SensingAsyncTask extends AsyncTask<String, Void, ArrayList<Tanah>> {
        @Override
        protected ArrayList<Tanah> doInBackground(String... strings) {
            SensingService sensingService = AppAPI.getRetrofit().create(SensingService.class);
            try {
                ArrayList<Tanah> res = sensingService.getSensing(sinceList).execute().body();
                for (int i = 0; i < res.size(); i++) {
                    int kodePetak = Integer.parseInt(res.get(i).getKode_petak());

                    if (sinceList.containsKey(String.format("since[%d]", Integer.parseInt(res.get(i).getKode_petak()))) == false) {
                        sinceList.put(String.format("since[%d]", Integer.parseInt(res.get(i).getKode_petak())), res.get(i).getWaktu_sensing());
                        sensingArrayListData.add(res.get(i));
                    } else {
                        if (sdf.parse(res.get(i).getWaktu_sensing()).after(sdf.parse(sinceList.get(String.format("since[%d]", kodePetak))))) {
                            sinceList.put(String.format("since[%d]", kodePetak), res.get(i).getWaktu_sensing());
                            sensingArrayListData.get(kodePetak - 1).setId_tanah(res.get(i).getId_tanah());
                            sensingArrayListData.get(kodePetak - 1).setJenis_tanah(res.get(i).getJenis_tanah());
                            sensingArrayListData.get(kodePetak - 1).setPh_tanah(res.get(i).getPh_tanah());
                            sensingArrayListData.get(kodePetak - 1).setSuhu_tanah(res.get(i).getSuhu_tanah());
                            sensingArrayListData.get(kodePetak - 1).setKelembaban_tanah(res.get(i).getKelembaban_tanah());
                            sensingArrayListData.get(kodePetak - 1).setSuhu_udara(res.get(i).getSuhu_udara());
                            sensingArrayListData.get(kodePetak - 1).setKelembaban_udara(res.get(i).getKelembaban_udara());
                            sensingArrayListData.get(kodePetak - 1).setKode_petak(res.get(i).getKode_petak());
                            sensingArrayListData.get(kodePetak - 1).setWaktu_sensing(res.get(i).getWaktu_sensing());
                        }
                    }
                }
                return sensingArrayListData;
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(SensingActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new ArrayList<Tanah>();
        }

        @Override
        protected void onPostExecute(ArrayList<Tanah> result) {
            if (result.size() > 0) {
                sensingAdapter.changeData(result);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
