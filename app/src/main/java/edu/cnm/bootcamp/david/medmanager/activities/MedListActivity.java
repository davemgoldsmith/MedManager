package edu.cnm.bootcamp.david.medmanager.activities;

import android.content.Intent;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

public class MedListActivity extends AppCompatActivity {

    private OrmHelper dbHelper = null;
    private Medication currentMed = null;


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
        setContentView(R.layout.activity_medlist);

        try {

            //take the following - create a new method after delete - add from editactivity
            Dao<Medication, Integer> dao = getHelper().getMedicationDao();
            QueryBuilder<Medication, Integer> query = dao.queryBuilder();
            query.orderBy("NAME", true);
            List<Medication> medications = dao.query(query.prepare());

            ArrayAdapter<Medication> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, medications);
            final ListView list = ((ListView) findViewById(R.id.queryList));
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<? > parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position) == currentMed) {
                        currentMed = null;
                        view.setSelected(false);
                        list.clearChoices();
                        list.requestLayout();
                        //disable buttons del and adjust
                        ((Button) findViewById(R.id.buttonAdjustMed)).setEnabled(false);
                        ((Button) findViewById(R.id.buttonschedadjust)).setEnabled(false);
                        ((Button) findViewById(R.id.buttonDeleteMed)).setEnabled(false);
                    }else {
                        currentMed = (Medication) parent.getItemAtPosition(position);
                        view.setSelected(true);
                        //enable del and adjust
                        ((Button) findViewById(R.id.buttonAdjustMed)).setEnabled(true);
                        ((Button) findViewById(R.id.buttonschedadjust)).setEnabled(true);
                        ((Button) findViewById(R.id.buttonDeleteMed)).setEnabled(true);

                    }

                }
            });
        } catch (SQLException ex) {
            // could throw an exception
        }

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

        // takes deletes current(highlighted) med from the database
        Button buttonDeleteMed = (Button) findViewById(R.id.buttonDeleteMed);
        buttonDeleteMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getHelper().getMedicationDao().delete(currentMed);
                    reloadDatabase();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
   }

    public void reloadDatabase(){

        try {
            Dao<Medication, Integer> dao = getHelper().getMedicationDao();
            QueryBuilder<Medication, Integer> query = dao.queryBuilder();
            query.orderBy("NAME", true);
            List<Medication> medications = dao.query(query.prepare());
            ArrayAdapter<Medication> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, medications);
            final ListView list = ((ListView) findViewById(R.id.queryList));
            list.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}