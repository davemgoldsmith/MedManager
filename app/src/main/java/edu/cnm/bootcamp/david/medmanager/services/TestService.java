package edu.cnm.bootcamp.david.medmanager.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by davem on 8/7/2017.
 */

public class TestService extends IntentService {

    private static final String TAG = "MyService";
    private static final long INTERVAL = TimeUnit.MINUTES.toMillis(1);

    public TestService(){
        super(TAG);
    }
    public static Intent newIntent(Context context){
        return new Intent(context, TestService.class);
    }

    public static void setServiceAlarm(Context context, boolean enabled){
        Intent intent = TestService.newIntent(context);
        PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager manager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (enabled) {

            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), INTERVAL, pending);
        }   else{
            manager.cancel(pending);
            pending.cancel();
        }
    }

    public static void setServiceAlarm(Context context, long time, boolean enabled){
        Intent intent = TestService.newIntent(context);
        PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager manager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (enabled) {
            manager.setAlarmClock(new AlarmManager.AlarmClockInfo(time, pending), pending);

//            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//                    SystemClock.elapsedRealtime(), INTERVAL, pending);
        }   else{
            manager.cancel(pending);
            pending.cancel();
        }
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Received an intent: " + intent);
    }
}
