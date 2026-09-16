package com.example.automotivetracker.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_records")
public class ServiceRecord extends AutomotiveRecord{

    @PrimaryKey(autoGenerate = true)
    private int serviceID;
    private int vehicleID;
    private String serviceType;
    private String serviceDate;
    private double cost;
    private int mileage;
    private String notes;

    public ServiceRecord(int serviceID, int vehicleID, String serviceType,
                         String serviceDate, int mileage, double cost, String notes) {
        this.serviceID=serviceID;
        this.vehicleID=vehicleID;
        this.serviceType=serviceType;
        this.serviceDate=serviceDate;
        this.mileage =mileage;
        this.cost = cost;
        this.notes = notes;
    }
    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID=serviceID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID=vehicleID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType= serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate=serviceDate;
    }

    public int getMileage() {
        return mileage;
    }
    public void setMileage(int mileage) {
        this.mileage=mileage;
    }
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost =cost;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes= notes;
    }
    @Override
    public String getDisplayName() {return serviceType+" - "+serviceDate;}
}