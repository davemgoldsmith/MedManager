package edu.cnm.bootcamp.david.medmanager.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by davem on 7/26/2017.
 */


@DatabaseTable(tableName = "SCHEDULE")

public class Schedule {

    @DatabaseField(columnName = "SCHEDULE_ID", generatedId = true)
    private int id;

//    @DatabaseField(columnName = "MEDICATION_ID", foreign = true, foreignAutoRefresh = true)
//    private Medication medication;



    @DatabaseField(columnName= "NAME", width = 200, unique=true)
    private String name;

    //@DatabaseField(columnName= "FREQUENCY")
   //private String frequency;

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

//    public Medication getMedication() {
//        return medication;
//    }
//
//    public void setMedication(Medication medication) {
//        this.medication = medication;
//    }

//    public String getFrequency() {return frequency;    }
//
//    public void setFrequency(String frequency) {
//        this.frequency = frequency;
//    }
//
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
