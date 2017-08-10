package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.cnm.bootcamp.david.medmanager.R;

public class SchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        final ToggleButton toggle = (ToggleButton) findViewById(R.id.alarmToggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                TextView text = (TextView) findViewById(R.id.alarmText);
                Intent intent = new Intent(SchedulerActivity.this, Receiver.class);
                PendingIntent pending = PendingIntent.getBroadcast(SchedulerActivity.this, 0, intent, 0);
                if (toggle.isChecked()) {
                    TimePicker picker = (TimePicker) findViewById(R.id.alarmTimePicker);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    calendar.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, picker.getCurrentMinute());
                    manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                    text.setText(format.format(calendar.getTime()));
                } else {
                    manager.cancel(pending);
                    text.setText("Alarm Off");
                }
            }
        });
    }
    public void onToggleClicked(View view) {
    }
}
