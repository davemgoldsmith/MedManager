package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
  private static final String TITLE = "Alarm";
  private static final String MESSAGE = "Wake the hell up!";

  public Service() {
    super(TAG);
    Log.i(TAG, "Starting service");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    Log.i(TAG, String.format("Notification: %s", intent));
    NotificationManager manager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    Intent reopen = new Intent(this, MainActivity.class);
    PendingIntent pending = PendingIntent.getActivity(this, 0, reopen, 0);
    Builder builder = new Builder(this).setContentTitle(TITLE)
                                       .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                       .setStyle(new BigTextStyle().bigText(MESSAGE))
                                       .setContentText(MESSAGE)
                                       .setAutoCancel(true)
                                       .setContentIntent(pending);
    manager.notify(1, builder.build());
  }

}
