package com.example.automotivetracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.automotivetracker.entities.ServiceRecord;

import java.util.List;

@Dao
public interface ServiceRecordDAO {
    @Insert(onConflict=OnConflictStrategy.IGNORE)
    void insert(ServiceRecord serviceRecord);
    @Update
    void update(ServiceRecord serviceRecord);
    @Delete
    void delete(ServiceRecord serviceRecord);

    @Query("SELECT*FROM service_records ORDER BY serviceID ASC")
    List<ServiceRecord> getAllServiceRecords();
    @Query("SELECT*FROM service_records WHERE vehicleID = :vehicleID ORDER BY serviceID ASC")
    List<ServiceRecord> getServiceRecordsForVehicle(int vehicleID);

    @Query("SELECT*FROM service_records WHERE serviceType LIKE'%' || :searchText ||'%'OR notes LIKE '%'||:searchText||'%'ORDER BY serviceID ASC")
    List<ServiceRecord> searchServiceRecords(String searchText);
}


