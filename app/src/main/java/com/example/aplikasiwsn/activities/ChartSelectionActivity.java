package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.SenseKeasaman;
import com.example.aplikasiwsn.models.SenseKelembabanTanah;
import com.example.aplikasiwsn.models.SenseKelembabanUdara;
import com.example.aplikasiwsn.models.SenseSuhuTanah;
import com.example.aplikasiwsn.models.SenseSuhuUdara;
import com.example.aplikasiwsn.models.Tanah;
import com.example.aplikasiwsn.services.ChartMakerService;
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

public class ChartSelectionActivity extends AppCompatActivity {
    private LineChart mChart;
    TextView toolbarName;
    ImageView btn_back;
    private int posisiSpinner = 0;
    ChartMakerService cmService;
    String[] spinnerMenu = {"Ph Tanah",
            "Suhu Tanah",
            "Kelembaban Tanah",
            "Suhu Udara",
            "Kelembaban Udara"};

    String[] spinnerMenuId = {"ph_tanah",
            "suhu_tanah",
            "kelembaban_tanah",
            "suhu_udara",
            "kelembaban_udara"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_selection);

        this.toolbarName = findViewById(R.id.tv_toolbar_name);
        this.toolbarName.setText("My Chart");

        this.btn_back = findViewById(R.id.btn_back);

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mChart = (LineChart) findViewById(R.id.linechart_selection);
        mChart.setNoDataText("Please wait...");
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setEnabled(false);

        Spinner spinner = findViewById(R.id.chart_selection_spinner);
        cmService = AppAPI.getRetrofit().create(ChartMakerService.class);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        posisiSpinner = position;
                        String isiSpinner = spinnerMenuId[posisiSpinner];
                        callChartMakerService(isiSpinner);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.spinner_list,spinnerMenu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        String isiSpinner = spinnerMenuId[posisiSpinner];

        callChartMakerService(isiSpinner);
    }

    public void callChartMakerService(String isiSpinner) {
        final ProgressDialog progressDialog = new ProgressDialog(ChartSelectionActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        switch (posisiSpinner) {
            case 0:
                Description desc = new Description();
                desc.setText("Keasaman Tanah (pH)");
                mChart.setDescription(desc);
                cmService.getChartData(isiSpinner).enqueue(new Callback<ArrayList<SenseKeasaman>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SenseKeasaman>> call, Response<ArrayList<SenseKeasaman>> response) {
                        ArrayList<SenseKeasaman> datas = response.body();
                        makeLineChart(datas.size(), datas);
                        mChart.invalidate();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SenseKeasaman>> call, Throwable t) {
                        System.out.println("Error loading data.");
                    }
                });
                break;
            case 1:
                Description descSuhuTanah = new Description();
                descSuhuTanah.setText("Suhu Tanah (°C)");
                mChart.setDescription(descSuhuTanah);
                cmService.getSuhuTanahChartData(isiSpinner).enqueue(new Callback<ArrayList<SenseSuhuTanah>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SenseSuhuTanah>> call, Response<ArrayList<SenseSuhuTanah>> response) {
                        ArrayList<SenseSuhuTanah> datas = response.body();
                        makeSuhuTanahChart(datas.size(), datas);
                        mChart.invalidate();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SenseSuhuTanah>> call, Throwable t) {

                    }
                });
                break;
            case 2:
                Description descKelembabanTanah = new Description();
                descKelembabanTanah.setText("Kelembaban Tanah");
                mChart.setDescription(descKelembabanTanah);
                cmService.getKelembabanTanahChartData(isiSpinner).enqueue(new Callback<ArrayList<SenseKelembabanTanah>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SenseKelembabanTanah>> call, Response<ArrayList<SenseKelembabanTanah>> response) {
                        ArrayList<SenseKelembabanTanah> datas = response.body();
                        makeKelembabanTanahChart(datas.size(), datas);
                        mChart.invalidate();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SenseKelembabanTanah>> call, Throwable t) {

                    }
                });
                break;
            case 3:
                Description descSuhuUdara = new Description();
                descSuhuUdara.setText("Suhu Udara (°C)");
                mChart.setDescription(descSuhuUdara);
                cmService.getSuhuUdaraChartData(isiSpinner).enqueue(new Callback<ArrayList<SenseSuhuUdara>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SenseSuhuUdara>> call, Response<ArrayList<SenseSuhuUdara>> response) {
                        ArrayList<SenseSuhuUdara> datas = response.body();
                        makeSuhuUdaraChart(datas.size(), datas);
                        mChart.invalidate();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SenseSuhuUdara>> call, Throwable t) {

                    }
                });
                break;
            case 4:
                Description descKelembabanUdara = new Description();
                descKelembabanUdara.setText("Kelembaban Udara");
                mChart.setDescription(descKelembabanUdara);
                cmService.getKelembabanUdaraChartData(isiSpinner).enqueue(new Callback<ArrayList<SenseKelembabanUdara>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SenseKelembabanUdara>> call, Response<ArrayList<SenseKelembabanUdara>> response) {
                        ArrayList<SenseKelembabanUdara> datas = response.body();
                        makeKelembabanUdaraChart(datas.size(), datas);
                        mChart.invalidate();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SenseKelembabanUdara>> call, Throwable t) {

                    }
                });
                break;
        }
    }
    private void makeLineChart(int size, ArrayList<SenseKeasaman> array) {
        ArrayList<Entry> node1 = new ArrayList<>();
        ArrayList<Entry> node2 = new ArrayList<>();
        ArrayList<Entry> node3 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (array.get(i).getKode_petak().equals("1")) {
                node1.add(new Entry(i, Float.parseFloat(array.get(i).getPh_tanah())));
            } else if (array.get(i).getKode_petak().equals("2")) {
                node2.add(new Entry(i, Float.parseFloat(array.get(i).getPh_tanah())));
            } else if (array.get(i).getKode_petak().equals("3")) {
                node3.add(new Entry(i, Float.parseFloat(array.get(i).getPh_tanah())));
            }
        }

        setChartData(node1, node2, node3);
    }

    private void makeSuhuTanahChart(int size, ArrayList<SenseSuhuTanah> array) {
        ArrayList<Entry> node1 = new ArrayList<>();
        ArrayList<Entry> node2 = new ArrayList<>();
        ArrayList<Entry> node3 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (array.get(i).getKode_petak().equals("1")) {
                node1.add(new Entry(i, Float.parseFloat(array.get(i).getSuhu_Tanah())));
            } else if (array.get(i).getKode_petak().equals("2")) {
                node2.add(new Entry(i, Float.parseFloat(array.get(i).getSuhu_Tanah())));
            } else if (array.get(i).getKode_petak().equals("3")) {
                node3.add(new Entry(i, Float.parseFloat(array.get(i).getSuhu_Tanah())));
            }
        }

        setChartData(node1, node2, node3);
    }

    private void makeKelembabanTanahChart(int size, ArrayList<SenseKelembabanTanah> array) {
        ArrayList<Entry> node1 = new ArrayList<>();
        ArrayList<Entry> node2 = new ArrayList<>();
        ArrayList<Entry> node3 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (array.get(i).getKode_petak().equals("1")) {
                node1.add(new Entry(i, Float.parseFloat(array.get(i).getKelembaban_tanah())));
            } else if (array.get(i).getKode_petak().equals("2")) {
                node2.add(new Entry(i, Float.parseFloat(array.get(i).getKelembaban_tanah())));
            } else if (array.get(i).getKode_petak().equals("3")) {
                node3.add(new Entry(i, Float.parseFloat(array.get(i).getKelembaban_tanah())));
            }
        }

        setChartData(node1, node2, node3);
    }

    private void makeSuhuUdaraChart(int size, ArrayList<SenseSuhuUdara> array) {
        ArrayList<Entry> node1 = new ArrayList<>();
        ArrayList<Entry> node2 = new ArrayList<>();
        ArrayList<Entry> node3 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (array.get(i).getKode_petak().equals("1")) {
                node1.add(new Entry(i, Float.parseFloat(array.get(i).getSuhu_udara())));
            } else if (array.get(i).getKode_petak().equals("2")) {
                node2.add(new Entry(i, Float.parseFloat(array.get(i).getSuhu_udara())));
            } else if (array.get(i).getKode_petak().equals("3")) {
                node3.add(new Entry(i, Float.parseFloat(array.get(i).getSuhu_udara())));
            }
        }

        setChartData(node1, node2, node3);
    }

    private void makeKelembabanUdaraChart(int size, ArrayList<SenseKelembabanUdara> array) {
        ArrayList<Entry> node1 = new ArrayList<>();
        ArrayList<Entry> node2 = new ArrayList<>();
        ArrayList<Entry> node3 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (array.get(i).getKode_petak().equals("1")) {
                node1.add(new Entry(i, Float.parseFloat(array.get(i).getKelembaban_udara())));
            } else if (array.get(i).getKode_petak().equals("2")) {
                node2.add(new Entry(i, Float.parseFloat(array.get(i).getKelembaban_udara())));
            } else if (array.get(i).getKode_petak().equals("3")) {
                node3.add(new Entry(i, Float.parseFloat(array.get(i).getKelembaban_udara())));
            }
        }
        setChartData(node1, node2, node3);
    }

    private void setChartData(ArrayList<Entry> node1, ArrayList<Entry> node2, ArrayList<Entry> node3) {
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
        mChart.setData(data);
    }
}
