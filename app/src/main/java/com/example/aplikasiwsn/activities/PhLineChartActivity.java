package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.SenseKeasaman;
import com.example.aplikasiwsn.services.KeasamanService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhLineChartActivity extends AppCompatActivity {
    private LineChart mChart;
    ArrayList<Entry> node1 = new ArrayList<>();
    ArrayList<Entry> node2 = new ArrayList<>();
    ArrayList<Entry> node3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph_chart);
        final ProgressDialog progressDialog = new ProgressDialog(PhLineChartActivity.this);

        mChart = (LineChart) findViewById(R.id.linechart);
        Description desc = new Description();
        desc.setText("Keasaman Tanah (pH)");
        mChart.setDescription(desc);
        mChart.setNoDataText("Please wait...");
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setEnabled(false);

        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        KeasamanService keasamanService = AppAPI.getRetrofit().create(KeasamanService.class);
        keasamanService.getKeasaman().enqueue(new Callback<ArrayList<SenseKeasaman>>() {
            @Override
            public void onResponse(Call<ArrayList<SenseKeasaman>> call, Response<ArrayList<SenseKeasaman>> response) {
                ArrayList<SenseKeasaman> datas = response.body();
                makeLineChart(datas.size(), datas);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<SenseKeasaman>> call, Throwable t) {
                System.out.println("Error loading data.");
            }
        });
    }

    private void makeLineChart(int size, ArrayList<SenseKeasaman> array) {
        for (int i = 0; i < size; i++) {
//            String dateString = array.get(i).getWaktu_sensing();

            //DAPETIN MILISEKON DARI WAKTU SENSING
//            long dateMilis = LocalDateTime.parse(dateString.replace(" ", "T")).atZone(ZoneId.of("Asia/Jakarta")).toInstant().toEpochMilli();

            if (array.get(i).getKode_petak().equals("1")) {
                node1.add(new Entry(i, Float.parseFloat(array.get(i).getPh_tanah())));
            } else if (array.get(i).getKode_petak().equals("2")) {
                node2.add(new Entry(i, Float.parseFloat(array.get(i).getPh_tanah())));
            } else if (array.get(i).getKode_petak().equals("3")) {
                node3.add(new Entry(i, Float.parseFloat(array.get(i).getPh_tanah())));
            }
        }

        LineDataSet set1 = new LineDataSet(node1, "Node 1");
        set1.setColor(Color.RED);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawCircles(false);
        set1.setDrawCircleHole(false);
        LineDataSet set2 = new LineDataSet(node2, "Node 2");
        set2.setColor(Color.GREEN);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setDrawCircles(false);
        set2.setDrawCircleHole(false);
        LineDataSet set3 = new LineDataSet(node3, "Node 3");
        set3.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set3.setDrawCircles(false);
        set3.setDrawCircleHole(false);

        LineData data = new LineData(set1, set2, set3);
        mChart.invalidate();
        mChart.setData(data);
    }
}