package edu.cnm.bootcamp.david.medmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.cnm.bootcamp.david.medmanager.R;

public class SchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.set(Calendar.HOUR, hourOfDay);

                long timeInMillis = calendar.getTimeInMillis();
            }

            //set intent for activity to fire alarm and notification
        });
    }
}
