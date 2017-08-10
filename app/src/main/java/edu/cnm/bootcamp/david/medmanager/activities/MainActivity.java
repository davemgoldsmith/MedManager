package edu.cnm.bootcamp.david.medmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import edu.cnm.bootcamp.david.medmanager.R;
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



        Button medListButton = (Button) findViewById(R.id.medListButton);
        medListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent medListPage = new Intent(MainActivity.this, MedListActivity.class);
                startActivity(medListPage);
            }
        });

   }

    @Override
    protected void onDestroy() {
        releaseHelper();
        super.onDestroy();
    }

}
