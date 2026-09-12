package com.example.automotivetracker.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehicles")
public class Vehicle extends AutomotiveRecord {
    @PrimaryKey(autoGenerate = true)
    private int vehicleID;
    private String model;
    private String make;
    private int year;
    private int mileage;


    public Vehicle(int vehicleID, String make, String model, int year ,int mileage) {
        this.vehicleID = vehicleID;
        this.make =make;
        this.model=model;
        this.year=year;
        this.mileage=mileage;
    }
    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    @Override
    public String getDisplayName() {return year+" "+make+" "+model;}
}