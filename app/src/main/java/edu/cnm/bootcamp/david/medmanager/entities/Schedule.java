package edu.cnm.bootcamp.david.medmanager.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Dave Goldsmith on 7/26/2017.
 *
 * Schedule Entity which holds the fields for the medications.
 * Simple three field, plus ID entity for maintaining information
 * about medication.
 *
 */


@DatabaseTable(tableName = "SCHEDULE")

public class Schedule {

    @DatabaseField(columnName = "SCHEDULE_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName= "NAME", width = 200, unique=true)
    private String name;

    @DatabaseField(columnName= "DOSAGE")
    private String dosage;

    @DatabaseField(columnName= "TIME")
    private String time;


    public Schedule (){

    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return getName() + "-" + getTime();
    }
}
