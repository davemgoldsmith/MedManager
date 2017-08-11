package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;



public class Receiver extends WakefulBroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {

      ComponentName comp =
              new ComponentName(context.getPackageName(),
        Service.class.getName());

    startWakefulService(context, (intent.setComponent(comp)));
    setResultCode(Activity.RESULT_OK);
  }

}
