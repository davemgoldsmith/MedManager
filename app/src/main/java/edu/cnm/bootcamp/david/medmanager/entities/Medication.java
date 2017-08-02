package edu.cnm.bootcamp.david.medmanager.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by davem on 7/26/2017.
 */


@DatabaseTable(tableName = "MEDICATION")
public class Medication {

    @DatabaseField(columnName = "MEDICATION_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName= "NAME", width = 200)
    private String name;

    @DatabaseField(columnName= "FREQUENCY")
    private int frequency;

    @DatabaseField(columnName= "DOSAGE")
    private float dosage;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Schedule> scheds;

    public Medication(){

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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public float getDosage() {
        return dosage;
    }

    public void setDosage(float dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        return getName();
    }
}
