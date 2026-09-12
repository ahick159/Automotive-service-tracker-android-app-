package com.example.automotivetracker.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.automotivetracker.entities.Vehicle;

import java.util.List;

@Dao
public interface VehicleDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert( Vehicle vehicle);
    @Update
    void update(Vehicle vehicle);
    @Delete
    void delete(Vehicle vehicle);
    @Query("SELECT*FROM vehicles ORDER BY vehicleID ASC")
    List<Vehicle>getAllVehicles();
    @Query("SELECT*FROM vehicles WHERE make LIKE'%'||:searchText||'%' OR model LIKE '%'||:searchText||'%'ORDER BY vehicleID ASC")
    List<Vehicle>searchVehicles(String searchText);

}
