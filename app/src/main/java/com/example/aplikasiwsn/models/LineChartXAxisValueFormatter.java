package com.example.aplikasiwsn.models;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class LineChartXAxisValueFormatter extends IndexAxisValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        long jam = TimeUnit.MILLISECONDS.toHours((long) value);
        long menit = TimeUnit.MILLISECONDS.toMinutes((long)value - (jam*60*60*1000));
        long detik = TimeUnit.MILLISECONDS.toSeconds((long)value - (jam*60*60*1000) - (menit*60*1000));
        String waktu = jam + ":" + menit + ":" + detik;
        return waktu;
    }
}
