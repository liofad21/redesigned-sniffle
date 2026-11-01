package com.gothiclegion.iconpack;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.RemoteViews;

public class BatteryWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        Intent b = c.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int pct = 0;
        boolean charging = false;
        if (b != null) {
            int level = b.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = b.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            if (level >= 0 && scale > 0) pct = level * 100 / scale;
            int st = b.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            charging = st == BatteryManager.BATTERY_STATUS_CHARGING || st == BatteryManager.BATTERY_STATUS_FULL;
        }
        RemoteViews rv = new RemoteViews(c.getPackageName(), R.layout.widget_battery);
        rv.setTextViewText(R.id.pct, pct + "%");
        rv.setProgressBar(R.id.bar, 100, pct, false);
        rv.setTextViewText(R.id.state, charging ? "Charging" : "Battery");
        for (int id : ids) m.updateAppWidget(id, rv);
    }
}
