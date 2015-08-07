package app.smartuse.user.smartuse;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by user on 07/08/15.
 */
public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {

        /*Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */

         if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

             SharedPreferences sharedPref = arg0.getSharedPreferences("smartUse",Context.MODE_PRIVATE);
             SharedPreferences.Editor editor = sharedPref.edit();
             long unixTime = System.currentTimeMillis() / 1000L;
             long lastUsedTime;
             lastUsedTime = sharedPref.getLong("lastLockTime",0);



             if(lastUsedTime == 0)
             {

             } else
             {

             }
             editor.putLong("lastLockTime",unixTime);
             editor.commit();

        }


    }

}