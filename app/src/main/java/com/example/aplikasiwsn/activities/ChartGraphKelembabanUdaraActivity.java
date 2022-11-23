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
import com.example.aplikasiwsn.models.SenseKelembabanUdara;
import com.example.aplikasiwsn.services.ChartMakerService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartGraphKelembabanUdaraActivity extends AppCompatActivity {
    private LineChart mChart;
    ArrayList<SenseKelembabanUdara> arrKelembabanUdara = new ArrayList<>();
    List<LineDataSet> arrLineDataSet = new ArrayList<>();
    List<Integer> arrColors = new ArrayList<>();
    SimpleDateFormat sdf;
    LineData lineData;
    ImageView btn_back;
    String isiSpinner;
    CredentialSharedPreferences cred;
    Timer timer;
    TimerTask task;
    LineDataSet set;
    boolean done = true;
    HashMap<String, String> sinceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_graph);

        sinceList = new HashMap<>();
        TextView toolbar = findViewById(R.id.tv_toolbar_name);
        toolbar.setText("Kelembaban Udara");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        cred = new CredentialSharedPreferences(this);
        mChart = (LineChart) findViewById(R.id.linechart_graph);
        mChart.getXAxis().setEnabled(true);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setValueFormatter(new LineChartXAxisValueFormatter());
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

        ChartMakerService chartMakerService = AppAPI.getRetrofit().create(ChartMakerService.class);
        chartMakerService.getKelembabanUdaraChartData(isiSpinner, sinceList).enqueue(new Callback<ArrayList<SenseKelembabanUdara>>() {
            @Override
            public void onResponse(Call<ArrayList<SenseKelembabanUdara>> call, Response<ArrayList<SenseKelembabanUdara>> response) {
                ArrayList<SenseKelembabanUdara> arrayList = response.body();
                for (int i = 0; i < Integer.parseInt(cred.loadJumlahNode()); i++) {
                    arrKelembabanUdara.add(new SenseKelembabanUdara(arrayList.get(i).getKode_petak(), arrayList.get(i).getKelembaban_udara(), arrayList.get(i).getWaktu_sensing()));
                    ArrayList<Entry> node = new ArrayList<>();
                    String secsStr = arrayList.get(i).getWaktu_sensing();
                    node.add(new Entry(getSecond(secsStr), Float.parseFloat(arrayList.get(i).getKelembaban_udara())));
                    arrLineDataSet.add(new LineDataSet(node, "Node " + (i + 1)));
                    Random rnd = new Random();
                    int thisColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    arrColors.add(thisColor);
                    arrLineDataSet.get(i).setColor(thisColor);
                    lineData.addDataSet(arrLineDataSet.get(i));
                    sinceList.put(String.format("since[%d]", Integer.parseInt(arrayList.get(i).getKode_petak())), arrayList.get(i).getWaktu_sensing());
                }
                for (int i = 0; i < arrLineDataSet.size(); i++) {
                    arrLineDataSet.get(i).setLineWidth(3f);
//                    arrLineDataSet.get(i).setDrawCircles(false);
                    arrLineDataSet.get(i).setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    arrLineDataSet.get(i).setCubicIntensity(0.2f);
//                    arrLineDataSet.get(i).setDrawValues(false);
                    arrLineDataSet.get(i).setCircleHoleRadius(10f);
                }
                mChart.setData(lineData);
                setRepeatingAsyncTask();
            }

            @Override
            public void onFailure(Call<ArrayList<SenseKelembabanUdara>> call, Throwable t) {

            }
        });
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
                            if (done) {
                                done = false;
                                ChartGraphKelembabanUdaraActivity.ChartGraphAsyncTask chartGraphAsyncTask = new ChartGraphAsyncTask();
                                chartGraphAsyncTask.execute();
                            }
                        } catch (Exception e) {
                            Toast.makeText(ChartGraphKelembabanUdaraActivity.this, "AsyncTask Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1 * 5000);  // interval of one minute
    }

    private class ChartGraphAsyncTask extends AsyncTask<String, Void, ArrayList<SenseKelembabanUdara>> {
        @Override
        protected ArrayList<SenseKelembabanUdara> doInBackground(String... strings) {
            ChartMakerService chartMakerService = AppAPI.getRetrofit().create(ChartMakerService.class);
            try {
                ArrayList<SenseKelembabanUdara> arrTemp = chartMakerService.getKelembabanUdaraChartData(isiSpinner, sinceList).execute().body();
                for (int i = 0; i < arrTemp.size(); i++) {
                    int kodePetak = Integer.parseInt(arrTemp.get(i).getKode_petak());

                    arrKelembabanUdara.get(kodePetak - 1).setKode_petak(arrTemp.get(i).getKode_petak());
                    arrKelembabanUdara.get(kodePetak - 1).setKelembaban_udara(arrTemp.get(i).getKelembaban_udara());
                    arrKelembabanUdara.get(kodePetak - 1).setWaktu_sensing(arrTemp.get(i).getWaktu_sensing());

                    if (sdf.parse(arrKelembabanUdara.get(kodePetak - 1).getWaktu_sensing()).after(sdf.parse(sinceList.get(String.format("since[%d]", kodePetak))))) {
                        sinceList.put(String.format("since[%d]", kodePetak), arrKelembabanUdara.get(kodePetak - 1).getWaktu_sensing());
                    }
                    LineData data = mChart.getLineData();
                    set = (LineDataSet) data.getDataSetByIndex(kodePetak - 1);
                    set.addEntry(new Entry(getSecond(arrKelembabanUdara.get(kodePetak - 1).getWaktu_sensing()), Float.parseFloat(arrKelembabanUdara.get(kodePetak - 1).getKelembaban_udara())));
                    data.notifyDataChanged();
                    mChart.notifyDataSetChanged();
                }
                return arrKelembabanUdara;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                Toast.makeText(ChartGraphKelembabanUdaraActivity.this, "Load data failed", Toast.LENGTH_LONG).show();
            }
            return arrKelembabanUdara;
        }

        @Override
        protected void onPostExecute(ArrayList<SenseKelembabanUdara> result) {
            mChart.invalidate();
            done = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    public long getSecond(String s) {
        long selisih = 0;
        try {
            Date dateNow = new Date();
            Calendar today = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);

            Date d = sdf.parse(s);
            selisih = d.getTime() - today.getTime().getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return selisih;
    }
}
