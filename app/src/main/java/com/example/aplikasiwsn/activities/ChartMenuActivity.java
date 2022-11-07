package com.example.aplikasiwsn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiwsn.R;


public class ChartMenuActivity extends AppCompatActivity {
    ImageView btn_back;
    Button btnEnter;
    private long mLastClickTime = 0;
    private int posisiSpinner = 0;
    Spinner spinner;
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
        setContentView(R.layout.activity_chart_main_menu);

        TextView toolbar = findViewById(R.id.tv_toolbar_name);
        toolbar.setText("Chart Menu");
        this.btn_back = findViewById(R.id.btn_back);
        this.btnEnter = findViewById(R.id.btn_enter);

        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                switch (posisiSpinner) {
                    case 0:
                        Intent intentPh = new Intent(ChartMenuActivity.this, ChartGraphPhActivity.class);
                        intentPh.putExtra("isiSpinner", spinnerMenuId[posisiSpinner]);
                        startActivity(intentPh);
                        break;
                    case 1:
                        Intent intentSuhuTanah = new Intent(ChartMenuActivity.this, ChartGraphSuhuTanahActivity.class);
                        intentSuhuTanah.putExtra("isiSpinner", spinnerMenuId[posisiSpinner]);
                        startActivity(intentSuhuTanah);
                        break;
                    case 2:
                        Intent intentKelembabanTanah = new Intent(ChartMenuActivity.this, ChartGraphKelembabanTanahActivity.class);
                        intentKelembabanTanah.putExtra("isiSpinner", spinnerMenuId[posisiSpinner]);
                        startActivity(intentKelembabanTanah);
                        break;
                    case 3:
                        Intent intentSuhuUdara = new Intent(ChartMenuActivity.this, ChartGraphSuhuUdaraActivity.class);
                        intentSuhuUdara.putExtra("isiSpinner", spinnerMenuId[posisiSpinner]);
                        startActivity(intentSuhuUdara);
                        break;
                    case 4:
                        Intent intentKelembabanUdara = new Intent(ChartMenuActivity.this, ChartGraphKelembabanUdaraActivity.class);
                        intentKelembabanUdara.putExtra("isiSpinner", spinnerMenuId[posisiSpinner]);
                        startActivity(intentKelembabanUdara);
                        break;
                }
            }
        });

        spinner = findViewById(R.id.chart_menu_spinner);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        posisiSpinner = position;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.spinner_list,spinnerMenu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}
