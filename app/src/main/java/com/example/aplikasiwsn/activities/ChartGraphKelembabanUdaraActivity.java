package com.example.aplikasiwsn.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.LineChartXAxisValueFormatter;
import com.example.aplikasiwsn.models.NodeSensorStatus;
import com.example.aplikasiwsn.models.Petak;
import com.example.aplikasiwsn.models.SenseKeasaman;
import com.example.aplikasiwsn.models.SenseKelembabanUdara;
import com.example.aplikasiwsn.services.ChartMakerService;
import com.example.aplikasiwsn.services.NodeService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ChartGraphKelembabanUdaraActivity extends AppCompatActivity {
    private LineChart mChart;
    ArrayList<SenseKelembabanUdara> arrKelembabanUdara = new ArrayList<>();
    List<LineDataSet> arrLineDataSet = new ArrayList<>();
    List<Integer> arrColors = new ArrayList<>();
    LineData lineData;
    ImageView btn_back;
    String isiSpinner;
    CredentialSharedPreferences cred;
    Timer timer;
    TimerTask task;
    int count = 0;
    LineDataSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_graph);

        TextView toolbar = findViewById(R.id.tv_toolbar_name);
        toolbar.setText("Kelembaban Udara");
        cred = new CredentialSharedPreferences(this);
        mChart = (LineChart) findViewById(R.id.linechart_graph);
        mChart.getXAxis().setEnabled(false);
        mChart.animateX(3000);
        Description desc = new Description();
        desc.setText("Kelembaban Udara");
        mChart.setDescription(desc);
        lineData = new LineData();

        this.btn_back = findViewById(R.id.btn_back);
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        isiSpinner = intent.getStringExtra("isiSpinner");

        for (int i = 0; i < Integer.parseInt(cred.loadJumlahNode()); i++) {
            arrKelembabanUdara.add(new SenseKelembabanUdara());
            ArrayList<Entry> node = new ArrayList<>();
            node.add(new Entry());
            arrLineDataSet.add(new LineDataSet(node, "Node " + (i+1)));
            Random rnd = new Random();
            int thisColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            arrColors.add(thisColor);
            arrLineDataSet.get(i).setColor(thisColor);
            lineData.addDataSet(arrLineDataSet.get(i));
        }

        for (int i = 0; i < arrLineDataSet.size(); i++) {
            arrLineDataSet.get(i).setLineWidth(5f);
            arrLineDataSet.get(i).setDrawCircles(false);
            arrLineDataSet.get(i).setMode(LineDataSet.Mode.CUBIC_BEZIER);
            arrLineDataSet.get(i).setCubicIntensity(0.2f);
            arrLineDataSet.get(i).setDrawValues(false);
            arrLineDataSet.get(i).setCircleHoleRadius(10f);
//            arrLineDataSet.get(i).setDrawFilled(true);
//            arrLineDataSet.get(i).setFillColor(arrColors.get(i));
//            arrLineDataSet.get(i).setFillAlpha(80);
        }

        mChart.setData(lineData);
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
                            ChartGraphAsyncTask chartGraphAsyncTask = new ChartGraphAsyncTask();
                            chartGraphAsyncTask.execute();
                        } catch (Exception e) {
                            Toast.makeText(ChartGraphKelembabanUdaraActivity.this, "AsyncTask failed", Toast.LENGTH_LONG).show();
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

    private class ChartGraphAsyncTask extends AsyncTask<String, Void, ArrayList<SenseKelembabanUdara>> {
        @Override
        protected ArrayList<SenseKelembabanUdara> doInBackground(String... strings) {
            ChartMakerService chartMakerService = AppAPI.getRetrofit().create(ChartMakerService.class);
            try {
                ArrayList<SenseKelembabanUdara> arrTemp = chartMakerService.getKelembabanUdaraChartData(isiSpinner).execute().body();
                for (int i = 0; i < arrTemp.size(); i++) {
                    arrKelembabanUdara.get(i).setKode_petak(arrTemp.get(i).getKode_petak());
                    arrKelembabanUdara.get(i).setKelembaban_udara(arrTemp.get(i).getKelembaban_udara());
                    arrKelembabanUdara.get(i).setWaktu_sensing(arrTemp.get(i).getWaktu_sensing());
                    addEntry(i);
                }
                count++;
                return arrKelembabanUdara;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(ChartGraphKelembabanUdaraActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
            }
            return arrKelembabanUdara;
        }

        @Override
        protected void onPostExecute(ArrayList<SenseKelembabanUdara> result) {
            mChart.invalidate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    private void addEntry(int index) {
        LineData data = mChart.getLineData();
        if(data != null) {
            set = (LineDataSet) data.getDataSetByIndex(index);
            set.addEntry(new Entry(count,Float.parseFloat(arrKelembabanUdara.get(index).getKelembaban_udara())));
            data.notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
    }
}
