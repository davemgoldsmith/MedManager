package edu.cnm.bootcamp.david.medmanager.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.entities.Medication;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;
import edu.cnm.bootcamp.david.medmanager.helpers.OrmHelper;

public class MainActivity extends AppCompatActivity {



    private OrmHelper dbHelper = null;

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

        OrmHelper ormHelper = new OrmHelper(getApplicationContext());
        ormHelper.getWritableDatabase().close();
        OpenHelperManager.releaseHelper();

        setContentView(R.layout.activity_main);

        try {
            Dao<Schedule, Integer> dao = getHelper().getScheduleDao();
           // List<Medication> medications = dao.queryForAll();
            QueryBuilder<Schedule, Integer> query = dao.queryBuilder();
            query.orderBy("TIME", true);
            List<Schedule> schedules = dao.query(query.prepare());

            ArrayAdapter<Schedule> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, schedules);
            ListView list = ((ListView) findViewById(R.id.queryList));
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<? > parent, View view, int position, long id) {

                    Intent medListPage = new Intent(MainActivity.this, MedListActivity.class);
                    Schedule selection = (Schedule) parent.getItemAtPosition(position);
                    medListPage.putExtra("medmanager.test", selection.getId());
                    startActivity(medListPage);
                }
            });
        } catch (SQLException ex) {
            // could throw an exception
        }



        Button medListButton = (Button) findViewById(R.id.medListButton);
        medListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent medListPage = new Intent(MainActivity.this, MedListActivity.class);
                startActivity(medListPage);
            }
        });

        Button buttonAdjust = (Button) findViewById(R.id.buttonAdjust);
        buttonAdjust.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent schedulerPage = new Intent(MainActivity.this, SchedulerActivity.class);
                startActivity(schedulerPage);
            }
        });



    }

   // @Override
   // protected void onListItemClick(ListView l, View v, int position, long id) {

   // }

    @Override
    protected void onDestroy() {
        releaseHelper();
        super.onDestroy();
    }




}
