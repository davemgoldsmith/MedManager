package edu.cnm.bootcamp.david.medmanager.activities;

import android.content.Intent;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.entities.Medication;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;
import edu.cnm.bootcamp.david.medmanager.helpers.AndroidDatabaseManager;
import edu.cnm.bootcamp.david.medmanager.helpers.OrmHelper;
import edu.cnm.bootcamp.david.medmanager.helpers.ScheduleAdapter;

public class MedListActivity extends AppCompatActivity {

    private OrmHelper dbHelper = null;
    private Schedule currentSched = null;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHelper().getWritableDatabase().close();
        setContentView(R.layout.activity_medlist);

        final ListView list = ((ListView) findViewById(R.id.queryList));

        timeSpinner();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<? > parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) == currentSched) {
                    currentSched = null;
                    view.setSelected(false);
                    list.clearChoices();
                    list.requestLayout();
                    //disable buttons del and adjust
                    ((Button) findViewById(R.id.buttonAdjustMed)).setEnabled(false);
                    ((Button) findViewById(R.id.buttonschedadjust)).setEnabled(false);
                    ((Button) findViewById(R.id.buttonDeleteMed)).setEnabled(false);
                }else {
                    currentSched = (Schedule) parent.getItemAtPosition(position);
                    view.setSelected(true);
                    //enable buttons del and adjust
                    ((Button) findViewById(R.id.buttonAdjustMed)).setEnabled(true);
                    ((Button) findViewById(R.id.buttonschedadjust)).setEnabled(true);
                    ((Button) findViewById(R.id.buttonDeleteMed)).setEnabled(true);
                }
            }
        });

                //takes to MedEditActivity to add a row to db
        Button buttonAddNew = (Button) findViewById(R.id.buttonAddNew);
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medEditActivity = new Intent(MedListActivity.this, MedEditActivity.class);
                startActivity(medEditActivity);
            }
        });

        //takes to AndroidDatabaseManager to test if database was updated appropriately
        Button button = (Button) findViewById(R.id.databaseButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent (MedListActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        // takes to SchedulerAcitvity to allow setting of alarm
        Button buttonSchedAdjust = (Button) findViewById(R.id.buttonschedadjust);
        buttonSchedAdjust.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent scheduler = new Intent (MedListActivity.this, SchedulerActivity.class);
                startActivity(scheduler);
            }
        });

        // deletes current(highlighted) med from the database
        Button buttonDeleteMed = (Button) findViewById(R.id.buttonDeleteMed);
        buttonDeleteMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getHelper().getScheduleDao().delete(currentSched);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                loadSchedules();
            }
        });


   }

    protected void timeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] times = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_dropdown, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

//    final ToggleButton toggle = (ToggleButton) findViewById(R.id.alarmToggle);
//        toggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                TextView text = (TextView) findViewById(R.id.alarmText);
//                Intent intent = new Intent(SchedulerActivity.this, Receiver.class);
//                //get intent from schedule.id
//                //input filter
//                //implement filter method
//                //
//                PendingIntent pending = PendingIntent.getBroadcast(SchedulerActivity.this, 0, intent, 0);
//                if (toggle.isChecked()) {
//                    TimePicker picker = (TimePicker) findViewById(R.id.alarmTimePicker);
//                    Calendar calendar = Calendar.getInstance();
//                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//                    calendar.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
//                    calendar.set(Calendar.MINUTE, picker.getCurrentMinute());
//                    manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
//                    text.setText(format.format(calendar.getTime()));
//                } else {
//                    manager.cancel(pending);
//                    text.setText("Alarm Off");
//                }
//            }
//        });



    @NonNull
    private void loadSchedules() {
        try {
            Dao<Schedule, Integer> dao = getHelper().getScheduleDao();
            QueryBuilder<Schedule, Integer> query = dao.queryBuilder();
            query.orderBy("NAME", true).orderBy("TIME", true);
            List<Schedule> schedules = dao.query(query.prepare());
            ScheduleAdapter adapter = new ScheduleAdapter (this, schedules);
            ListView list = ((ListView) findViewById(R.id.queryList));
            list.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException (e);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        loadSchedules();
    }
}