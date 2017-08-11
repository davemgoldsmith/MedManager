package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import edu.cnm.bootcamp.david.medmanager.R;

/**
 * Created by Nicholas Bennett on 2017-08-08.
 */

public class Service extends IntentService {

    private static final String TAG = "Service";
    private static final String TITLE = "Medication Reminder";
    private static final String MESSAGE = "Please remember to take your medication.";

    public Service() {
        super(TAG);
        Log.i(TAG, "Starting service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }


        Log.i(TAG, String.format("Notification: %s", intent));
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent reopen = new Intent(this, MedListActivity.class);
        PendingIntent pending = PendingIntent.getActivity(this, intent.getIntExtra(MedEditActivity.SCHEDULE_ID_KEY, 0), reopen, 0);
        Builder builder = new Builder(this).setContentTitle(intent.getStringExtra(MedEditActivity.ALARM_TITLE_KEY))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setStyle(new BigTextStyle().bigText(MESSAGE))
                //  .setContentText(MESSAGE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(false)
                .setSound(alarmUri)
                .setContentIntent(pending);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_INSISTENT;

        manager.notify(intent.getIntExtra(MedEditActivity.SCHEDULE_ID_KEY, 0), notification);
    }

}
