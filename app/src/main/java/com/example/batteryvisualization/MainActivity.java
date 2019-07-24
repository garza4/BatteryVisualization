package com.example.batteryvisualization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import android.graphics.drawable.Drawable;

import android.appwidget.AppWidgetHost;
import android.os.BatteryManager;

import java.util.ArrayList;
/*
Created by Bobby Garza using PhilJay MPAndroidChart

 */
public class MainActivity extends AppCompatActivity {
    //https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/BarChartActivity.java
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XAxis xAxis = chart.getXAxis();
        xAxis.mEntryCount = 0;
        //use google AppUsageStatistics: https://github.com/googlesamples/android-AppUsageStatistics to collect app usage data
        final Typeface normal = Typeface.DEFAULT;
        xAxis.setTypeface(normal);
        setContentView(R.layout.activity_main);
        chart = findViewById(R.id.barchart);
        graph();
    }

    public float getData(){

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        return batteryPct;
    }

    public void getStats(){
        UsageStatsManager mUsageStatsManager;
        //collect user data here...

    }

    public void graph(){//BarChart graph(){
        int i = 0;
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(i, getData(), getResources().getDrawable(R.drawable.ic_launcher_background))); //change icon
        BarDataSet batteryData = new BarDataSet(values, "Battery Levels");




        //from Philjay lines 193-201 in barchart example
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(batteryData);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(Typeface.DEFAULT);
        data.setBarWidth(0.9f);

        chart.setData(data);


    }
}
