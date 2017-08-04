package edu.cnm.bootcamp.david.medmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.helpers.AndroidDatabaseManager;
import edu.cnm.bootcamp.david.medmanager.helpers.OrmHelper;

public class MedListActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_medlist);

        int scheduleId = getIntent().getIntExtra("medmanager.test", 0);

       // ((TextView) findViewById(R.id.textView2)).setText(Integer.toString(scheduleId));
        try {
            ((TextView) findViewById(R.id.textView2)).setText(getHelper()
                    .getScheduleDao().queryForId(scheduleId).getMedication().getName());
        } catch (SQLException ex) {

        }




        Button button = (Button) findViewById(R.id.databaseButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent (MedListActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });


    }
}