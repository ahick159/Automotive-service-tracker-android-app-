package com.example.automotivetracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.automotivetracker.dao.ServiceRecordDAO;
import com.example.automotivetracker.dao.VehicleDAO;
import com.example.automotivetracker.entities.ServiceRecord;
import com.example.automotivetracker.entities.Vehicle;

@Database(entities={Vehicle.class, ServiceRecord.class},version=1, exportSchema = false)
public abstract class VehicleDatabaseBuilder extends RoomDatabase{
    public abstract VehicleDAO vehicleDAO();
    public abstract ServiceRecordDAO serviceRecordDAO();
    private static volatile VehicleDatabaseBuilder INSTANCE;
    static VehicleDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VehicleDatabaseBuilder.class) {
                if (INSTANCE==null) {
                    INSTANCE=Room.databaseBuilder(
                            context.getApplicationContext(), VehicleDatabaseBuilder.class, "vehicleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}