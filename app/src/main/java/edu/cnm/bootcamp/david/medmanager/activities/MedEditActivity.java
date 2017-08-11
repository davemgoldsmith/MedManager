package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.entities.Medication;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;
import edu.cnm.bootcamp.david.medmanager.helpers.OrmHelper;

//Activity that adds a new medication to the database or
//or allows for the adjustment of a current medication


public class MedEditActivity extends AppCompatActivity {

    private OrmHelper dbHelper = null;
    private Schedule currentSched = null;

    public static final String SCHEDULE_ID_KEY = MedEditActivity.class.getName() + ".scheduleId";
    public static final String ALARM_TITLE_KEY = MedEditActivity.class.getName() + ".alarmTitle";


    private synchronized OrmHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, OrmHelper.class);
        }
        return dbHelper;
    }

    private synchronized void releaseHelper() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        getHelper();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseHelper();
    }

    protected void timeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] times = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_dropdown, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_edit);
        timeSpinner();

        Intent intent = getIntent();
        int scheduleId = intent.getIntExtra(MedListActivity.MED_MANAGER_SCHEDULE_ID, 0);
        if (scheduleId != 0) {
            try {
                Dao<Schedule, Integer> dao = getHelper().getScheduleDao();
                currentSched = dao.queryForId(scheduleId);
                EditText editText = (EditText) findViewById(R.id.editText);
                editText.setText(currentSched.getName());

                EditText editText2 = (EditText) findViewById(R.id.editText2);
                editText2.setText(currentSched.getDosage());

                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                for (int i = 0; i < spinner.getCount(); i++) {
                    if (spinner.getItemAtPosition(i).equals(currentSched.getTime())) {
                        spinner.setSelection(i);
                        break;
                    }
                }
                Button button = (Button) findViewById(R.id.button);
                button.setText(R.string.update_sched);
                TextView textview = (TextView) findViewById(R.id.textView3);
                textview.setText(R.string.update_med);

            } catch (SQLException ex) {
                ex.printStackTrace();
                scheduleId = 0;
                currentSched = null;
            }
        }
        if (scheduleId == 0) {
            EditText editText = (EditText) findViewById(R.id.editText);
            editText.setText("");

            EditText editText2 = (EditText) findViewById(R.id.editText2);
            editText2.setText("");

            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setSelection(8);

            Button button = (Button) findViewById(R.id.button);
            button.setText(R.string.create_sched);

            TextView textview = (TextView) findViewById(R.id.textView3);
            textview.setText(R.string.create_med);
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Schedule, Integer> dao = getHelper().getScheduleDao();
                    Schedule schedule = (currentSched == null) ? new Schedule() : currentSched;

                    EditText editText = (EditText) findViewById(R.id.editText);
                    schedule.setName(editText.getText().toString());

                    EditText editText2 = (EditText) findViewById(R.id.editText2);
                    schedule.setDosage(editText2.getText().toString());

                    Spinner spinner = (Spinner) findViewById(R.id.spinner);

                    schedule.setTime(spinner.getSelectedItem().toString());

                    if (currentSched == null) {
                        dao.create(schedule);
                    } else {
                        dao.update(schedule);
                    }
                scheduleAlarm(schedule.getId(), schedule.getName());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                finish();
            }
        });
    }


    private void scheduleAlarm(int id, String title) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        TextView text = (TextView) findViewById(R.id.alarmText);
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra(SCHEDULE_ID_KEY, id);
        intent.putExtra(ALARM_TITLE_KEY, title);

        //get intent from schedule.id
        //input filter
        //implement filter method
        //
        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, 0);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Calendar calendar = Calendar.getInstance();

        // regex to parse hour and minute from spinner id

        String[] parts = ((String) spinner.getSelectedItem()).split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);


        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pending);


    }
}


