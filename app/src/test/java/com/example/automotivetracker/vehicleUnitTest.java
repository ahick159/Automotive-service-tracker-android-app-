package com.example.automotivetracker;

import org.junit.Test;
import com.example.automotivetracker.entities.Vehicle;
import com.example.automotivetracker.entities.ServiceRecord;
import static org.junit.Assert.*;
public class vehicleUnitTest {
    @Test
    public void vehicleNameTest(){
        Vehicle vehicle=new Vehicle(1, "Ford","Focus",2020,5000);
        assertEquals("2020 Ford Focus", vehicle.getDisplayName());
    }
    @Test
    public void vehicleGetSetTest(){
        Vehicle vehicle= new Vehicle(1,"Ford","Focus",2020,5000);
        vehicle.setMake("Honda");
        vehicle.setModel("Civic");
        vehicle.setYear(2000);
        vehicle.setMileage(100000);
        assertEquals("Honda",vehicle.getMake());
        assertEquals("Civic",vehicle.getModel() );
        assertEquals(2000, vehicle.getYear());
        assertEquals(100000,vehicle.getMileage ());
    }
    @Test
    public void serviceRecordNameTest() {
        ServiceRecord serviceRecord=new ServiceRecord(1, 1, "Oil Change",
                "09/13/2026",5000, 50.00,"Oil changed");
        assertEquals("Oil Change - 09/13/2026", serviceRecord.getDisplayName());
    }
    @Test
    public void serviceRecordGetSetTest(){
        ServiceRecord serviceRecord=new ServiceRecord(1,1,"Oil Change",
                "09/13/2026" ,
                5000,50.00,"Oil changed");
        serviceRecord.setServiceType("brake repair");
        serviceRecord.setServiceDate("09/14/2026");
        serviceRecord.setMileage(6000);
        serviceRecord.setCost(300.00);
        serviceRecord.setNotes("brake pads and rotors replaced");
        assertEquals("brake repair",serviceRecord.getServiceType());
        assertEquals("09/14/2026",serviceRecord.getServiceDate());
        assertEquals(6000,serviceRecord.getMileage());
        assertEquals(300.00,serviceRecord.getCost(),0.01);
        assertEquals("brake pads and rotors replaced",serviceRecord.getNotes());
    }
}
