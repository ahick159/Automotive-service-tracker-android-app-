package com.example.automotivetracker.ui;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automotivetracker.R;
import com.example.automotivetracker.database.Repository;
import com.example.automotivetracker.entities.ServiceRecord;
import com.example.automotivetracker.entities.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceReport extends AppCompatActivity {

    Repository repository;

    TextView reportDate;
    TextView vehicleName;
    TableLayout reportTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_report);
        repository=new Repository(getApplication());

        reportDate=findViewById(R.id.reportDate);
        vehicleName=findViewById(R.id.vehicleName);
        reportTable=findViewById(R.id.reportTable);

        int vehicleID=getIntent().getIntExtra("vehicleID",-1);

        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy",Locale.US);

        reportDate.setText("Generated: "+sdf.format(new Date()));
        Vehicle vehicle=repository.getVehicleById(vehicleID);
        if(vehicle!=null) {

            vehicleName.setText(
                    vehicle.getYear()+" "+
                            vehicle.getMake()+" "+
                            vehicle.getModel()
            );
        }

        List<ServiceRecord>serviceRecords=
                repository.getServiceRecordsForVehicle(vehicleID);

        for(ServiceRecord service:serviceRecords) {

            TableRow row=new TableRow(this);

            TextView serviceType=new TextView(this);
            serviceType.setText(service.getServiceType());
            serviceType.setPadding(5,8,8,8);
            TextView date=new TextView(this);
            date.setText(service.getServiceDate());
            date.setPadding(5,8,8,8);
            TextView mileage=new TextView(this);
            mileage.setText(Integer.toString(service.getMileage()));
            mileage.setPadding(5,8,8,8);
            TextView cost=new TextView(this);
            cost.setText("$"+service.getCost());
            cost.setPadding(5,8,8,8);
            TextView notes=new TextView(this);
            notes.setText(service.getNotes());
            notes.setPadding(5,8,8,8);

            row.addView(serviceType);
            row.addView(date);
            row.addView(mileage);
            row.addView(cost);
            row.addView(notes);

            reportTable.addView(row);
        }
    }
}