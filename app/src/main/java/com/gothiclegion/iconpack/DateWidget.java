package com.gothiclegion.iconpack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class DateWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        RemoteViews rv = new RemoteViews(c.getPackageName(), R.layout.widget_date);
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.calendar/time/"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        rv.setOnClickPendingIntent(R.id.root,
                PendingIntent.getActivity(c, 1, i, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT));
        for (int id : ids) m.updateAppWidget(id, rv);
    }
}
