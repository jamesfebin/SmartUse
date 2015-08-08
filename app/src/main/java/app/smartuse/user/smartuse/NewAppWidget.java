package app.smartuse.user.smartuse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.util.Log;
import android.widget.RemoteViews;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences("smartUse",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        long unixTime = System.currentTimeMillis() / 1000L;
        long lastUsedTime;
        lastUsedTime = sharedPref.getLong("lastLockTime",0);
        editor.putLong("lastLockTime",unixTime);
        editor.commit();

        // Enter relevant functionality for when the first widget is created
        /*
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction("UPDATESECONDS");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 1000 * 1, 1000 , pi);
        */
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        /*
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction("UPDATESECONDS");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        */
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        SharedPreferences sharedPref = context.getSharedPreferences("smartUse",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        long unixTime = System.currentTimeMillis() / 1000L;
        long lastUsedTime;
        lastUsedTime = sharedPref.getLong("lastLockTime",0);


        CharSequence widgetText;


        if(lastUsedTime == 0)
        {
             widgetText = "SMART USE";
        } else
        {

            if(unixTime - lastUsedTime == 0)
            {
                widgetText = "SMART USE";
            }
            else if( unixTime - lastUsedTime < 60)
            {
                widgetText = (unixTime - lastUsedTime) + " seconds ago";

            }
             else {
                lastUsedTime = lastUsedTime * 1000;
                PrettyTime p = new PrettyTime();
                Date lastTime = new java.util.Date(lastUsedTime);
                widgetText = p.format(lastTime);
            }

        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), NewAppWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                onUpdate(context, appWidgetManager, appWidgetIds);
            }

            SharedPreferences sharedPref = context.getSharedPreferences("smartUse",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            long unixTime = System.currentTimeMillis() / 1000L;
            long lastUsedTime;
            lastUsedTime = sharedPref.getLong("lastLockTime",0);
            editor.putLong("lastLockTime",unixTime);
            editor.commit();

        }

        /*else if(intent.getAction().equals("UPDATESECONDS"))
        {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
            //Acquire the lock
            wl.acquire();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), NewAppWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                onUpdate(context, appWidgetManager, appWidgetIds);
            }

            wl.release();

        }*/


    }
}


