package com.example.batteryvisualization;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetReceiver extends AppWidgetProvider {
    //widget code found here: https://medium.com/android-bits/android-widgets-ad3d166458d3
    static StatHolder statHolder = new StatHolder(0,0);


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_receiver);

        views.setTextViewText(R.id.battLevel,Float.toString(getData()));
        getStats();
        views.setTextViewText(R.id.screenOn,Long.toString(statHolder.screenTime));
        //intent to udpate numbers...
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://erenutku.com"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
//        views.setOnClickPendingIntent(R.id.refreshButton, pendingIntent);
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_receiver);
        views.setTextViewText(R.id.battLevel, Float.toString(getData()));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static public float getData(){

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        Log.d("battery level ", Float.toString(batteryPct));
        return batteryPct * 100;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    static public void getStats(){
        //TODO - populate statholder to contain the battery level and on screen time

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long finish;
        long aggregateTime = 0;
        while(pm.isInteractive()){ //while the screen is on ->
            endTime = System.currentTimeMillis();
        }
        finish = endTime - startTime;
        aggregateTime += finish;
        statHolder.aggregateTime(statHolder,aggregateTime);
        statHolder.setBatteryLevel(statHolder,getData());


    }
}

