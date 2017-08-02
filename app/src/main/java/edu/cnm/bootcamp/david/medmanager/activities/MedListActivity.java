package edu.cnm.bootcamp.david.medmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.helpers.AndroidDatabaseManager;

public class MedListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist);

        int scheduleId = getIntent().getIntExtra("medmanager.test", 0);
        ((TextView) findViewById(R.id.textView2)).setText(Integer.toString(scheduleId));

        Button button = (Button) findViewById(R.id.databaseButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent (MedListActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });


    }
}