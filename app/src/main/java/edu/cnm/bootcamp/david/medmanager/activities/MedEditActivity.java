package edu.cnm.bootcamp.david.medmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.entities.Medication;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;
import edu.cnm.bootcamp.david.medmanager.helpers.OrmHelper;

//Activity that adds a new medication to the database.
public class MedEditActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_med_edit);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Schedule, Integer> dao = getHelper().getScheduleDao();
                    Schedule schedule = new Schedule();

                    EditText editText = (EditText) findViewById(R.id.editText);
                    schedule.setName(editText.getText().toString());

                    EditText editText2 = (EditText) findViewById(R.id.editText2);
                    schedule.setDosage(editText2.getText().toString());

                    EditText medTime = (EditText) findViewById(R.id.medTime);
                    schedule.setTime(medTime.getText().toString());


                    dao.create(schedule);
//schedule.getId() -intent for alarm
                    //add intent? to go back to MedListActivity
                    //should repopulate db upon revisiting activity
//                    Intent backToMedList = new Intent (MedEditActivity.this, MedListActivity.class);
//                    startActivity(backToMedList);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                finish();


            }
        });


    }
}
