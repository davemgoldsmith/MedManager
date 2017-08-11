package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.entities.Medication;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;
import edu.cnm.bootcamp.david.medmanager.helpers.AndroidDatabaseManager;
import edu.cnm.bootcamp.david.medmanager.helpers.OrmHelper;
import edu.cnm.bootcamp.david.medmanager.helpers.ScheduleAdapter;

//Author Dave Goldsmith
//Deep Dive Bootcamp
//Med Manager application that sets alarms for medications.
//
//Main activity of application.  From this activity, a current list of medications is displayed.
//From this activity, user is able to select a medication in order to either delete or adjust it.
//The user is also able to add a medication including dosage and time.
//The time defined will trigger the alarm.


public class MedListActivity extends AppCompatActivity {

    public static final String MED_MANAGER_SCHEDULE_ID = "medManager.scheduleId";
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
    protected void onStart(){
        super.onStart();
        getHelper();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseHelper();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHelper().getWritableDatabase().close();
        setContentView(R.layout.activity_medlist);

        final ListView list = ((ListView) findViewById(R.id.queryList));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<? > parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) == currentSched) {
                    currentSched = null;
                    view.setSelected(false);
                    view.setActivated(false);

                    list.clearChoices();
                    list.requestLayout();
                    //disable buttons del and adjust
                    ((Button) findViewById(R.id.buttonDeleteMed)).setEnabled(false);

                    Button buttonAddNew = (Button) findViewById(R.id.buttonAddNew);
                    buttonAddNew.setText(R.string.add_med);
                }else {
                    currentSched = (Schedule) parent.getItemAtPosition(position);
                    view.setSelected(true);
                    view.setActivated(true);
                    //enable buttons del
                    ((Button) findViewById(R.id.buttonDeleteMed)).setEnabled(true);

                    Button buttonAddNew = (Button) findViewById(R.id.buttonAddNew);
                    buttonAddNew.setText(R.string.adjust_med);

                }
            }
        });

        //takes to MedEditActivity to add or adjust a medication
        //this is determined by whether a medication has been selected or not
        //if a medication is selected, then the pop up screen will allow for an update
        //otherwise, if no medication is selected, then the pop up screen will allow for
        //the addition of a new medication
        Button buttonAddNew = (Button) findViewById(R.id.buttonAddNew);
        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medEditActivity = new Intent(MedListActivity.this, MedEditActivity.class);
                medEditActivity.putExtra(MED_MANAGER_SCHEDULE_ID, (currentSched == null ) ? 0 : currentSched.getId());
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



//


    //Original loading of db schedule
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

    //Rebuilds schedule upon resumption of this activity
    @Override
    protected void onResume() {
        super.onResume();
        loadSchedules();
    }

    private void unScheduleAlarm(int id) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, Receiver.class);


        //get intent from schedule.id
        //input filter
        //implement filter method
        //
        PendingIntent pending = PendingIntent.getBroadcast(this, id, intent, 0);


        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.cancel(pending);


    }
}
