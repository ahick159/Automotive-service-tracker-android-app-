package com.example.automotivetracker.database;

import android.app.Application;

import com.example.automotivetracker.dao.ServiceRecordDAO;
import com.example.automotivetracker.dao.VehicleDAO;
import com.example.automotivetracker.entities.ServiceRecord;
import com.example.automotivetracker.entities.Vehicle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private VehicleDAO mVehicleDAO;
    private ServiceRecordDAO mServiceRecordDAO;
    private List<Vehicle> mAllVehicles;
    private List<ServiceRecord> mAllServiceRecords;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutors =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public Repository(Application application) {
        VehicleDatabaseBuilder db=VehicleDatabaseBuilder.getDatabase(application);
        mVehicleDAO=db.vehicleDAO();
        mServiceRecordDAO=db.serviceRecordDAO();
    }

    public List<Vehicle> getAllVehicles() {
        databaseExecutors.execute(() -> {
            mAllVehicles =mVehicleDAO.getAllVehicles();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVehicles;
    }
    public List<Vehicle> searchVehicles(String searchText) {
        databaseExecutors.execute(() -> {
            mAllVehicles=mVehicleDAO.searchVehicles(searchText);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVehicles;
    }
    public List<ServiceRecord> getAllServiceRecords() {
        databaseExecutors.execute(() -> {
            mAllServiceRecords= mServiceRecordDAO.getAllServiceRecords();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllServiceRecords;
    }
    public List<ServiceRecord> getServiceRecordsForVehicle(int vehicleID) {
        databaseExecutors.execute(() -> {
            mAllServiceRecords=mServiceRecordDAO.getServiceRecordsForVehicle(vehicleID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllServiceRecords;
    }

    public void insert(Vehicle vehicle) {
        databaseExecutors.execute(() -> {
            mVehicleDAO.insert(vehicle);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vehicle vehicle) {
        databaseExecutors.execute(() -> {
            mVehicleDAO.update(vehicle);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(Vehicle vehicle) {
        databaseExecutors.execute(() -> {
            mVehicleDAO.delete(vehicle);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void insert(ServiceRecord serviceRecord) {
        databaseExecutors.execute(() -> {
            mServiceRecordDAO.insert(serviceRecord);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(ServiceRecord serviceRecord) {
        databaseExecutors.execute(()-> {
            mServiceRecordDAO.update(serviceRecord);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(ServiceRecord serviceRecord) {
        databaseExecutors.execute(() -> {
            mServiceRecordDAO.delete(serviceRecord);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle getVehicleById(int vehicleID) {
        List<Vehicle> allVehicles = getAllVehicles();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getVehicleID()== vehicleID) {
                return vehicle;
            }
        }
        return null;
    }
}