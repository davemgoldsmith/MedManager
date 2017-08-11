package edu.cnm.bootcamp.david.medmanager.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import edu.cnm.bootcamp.david.medmanager.R;
import edu.cnm.bootcamp.david.medmanager.entities.Medication;
import edu.cnm.bootcamp.david.medmanager.entities.Schedule;

/**
 * Created by davem on 7/27/2017.
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medmanagerdb.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Medication, Integer> medicationDao = null;
    private Dao<Schedule, Integer> scheduleDao = null;


    private static ConnectionSource source;


    public OrmHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(OrmHelper.class.getName(), "onCreate");
            source = connectionSource;
            TableUtils.createTable(connectionSource, Medication.class);
            TableUtils.createTable(connectionSource, Schedule.class);
            populateDB();
        } catch (SQLException e) {
            Log.e(OrmHelper.class.getName(), "Can't create databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                int oldVersion, int newVersion) {

    }

    public synchronized Dao<Medication, Integer> getMedicationDao() throws SQLException {
        if (medicationDao == null) {
            medicationDao = getDao(Medication.class);
        }
        return medicationDao;
    }

    public synchronized Dao<Schedule, Integer> getScheduleDao() throws SQLException {
        if (scheduleDao == null) {
            scheduleDao = getDao(Schedule.class);
        }
        return scheduleDao;
    }

    /* seed data */
    private void populateDB() throws SQLException {

//        Medication med = new Medication ();
//        med.setName("Groovy Drug");
//        //med.setDosage("50");
//        //med.setFrequency("3");
//        getMedicationDao().create(med);

        Schedule sched = new Schedule();
        sched.setName("Groovy Drug");
        sched.setTime("0800");
//        sched.setMedication(med);
        sched.setDosage("50");
        getScheduleDao().create(sched);


//        med = new Medication ();
//        med.setName("Happy Drug");
//       // med.setDosage("10");
//        //med.setFrequency("2");
//        getMedicationDao().create(med);

        sched = new Schedule();
        sched.setTime("0900");
        sched.setName("Happy Drug");
      //  sched.setMedication(med);
        sched.setDosage("50");
        getScheduleDao().create (sched);




    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;

        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }


}

