package com.example.automotivetracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.automotivetracker.R;
import com.example.automotivetracker.database.Repository;
import com.example.automotivetracker.entities.Vehicle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class VehicleDetails extends AppCompatActivity {

    String VehicleMake;
    String VehicleModel;
    int VehicleYear;
    int VehicleMileage;
    int VehicleID;

    EditText editVehicleMake;
    EditText editVehicleModel;
    EditText editVehicleYear;
    EditText editVehicleMileage;

    Repository repository;

    RecyclerView serviceRecyclerView;
    Service_adapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle_details);

        repository=new Repository(getApplication());

        editVehicleMake = findViewById(R.id.vehicleMakeId);
        editVehicleModel = findViewById(R.id.vehicleModelId);
        editVehicleYear=findViewById(R.id.vehicleYearId);
        editVehicleMileage = findViewById(R.id.vehicleMileageId);

        VehicleMake = getIntent().getStringExtra("make");
        VehicleModel = getIntent().getStringExtra("model");
        VehicleYear = getIntent().getIntExtra("year", 0);
        VehicleMileage = getIntent().getIntExtra("mileage", 0);
        VehicleID=getIntent().getIntExtra("id", -1);

        editVehicleMake.setText(VehicleMake);
        editVehicleModel.setText(VehicleModel);

        if (VehicleYear != 0) {
            editVehicleYear.setText(Integer.toString(VehicleYear));
        }
        if (VehicleMileage != 0) {
            editVehicleMileage.setText(Integer.toString(VehicleMileage));
        }

        serviceRecyclerView = findViewById(R.id.serviceRecyclerView);

        serviceAdapter = new Service_adapter(this);

        serviceRecyclerView.setAdapter(serviceAdapter);

        serviceRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadServiceRecords();

        FloatingActionButton fab=findViewById(R .id.floatingActionButton2);

        fab.setOnClickListener(v-> {
            Intent intent=new Intent(VehicleDetails.this, Service_details.class);
            intent.putExtra("vehicleID", VehicleID);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (repository != null && serviceAdapter != null) {
            loadServiceRecords();
        }
    }

    private void loadServiceRecords() {

        List<com.example.automotivetracker.entities.ServiceRecord> serviceRecords =
                repository.getServiceRecordsForVehicle(VehicleID);

        serviceAdapter.setServiceRecord(serviceRecords);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_vehicle_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.vehiclesave) {

            if (editVehicleMake.getText().toString().isEmpty() || editVehicleModel.getText().toString().isEmpty() ||
                    editVehicleYear.getText().toString().isEmpty() ||
                    editVehicleMileage.getText().toString().isEmpty()) {

                Toast.makeText(VehicleDetails.this, "Please enter all vehicle information", Toast.LENGTH_SHORT).show();
                return true;
            }
            int vehicleYear = Integer.parseInt(editVehicleYear.getText().toString()
            );
            int vehicleMileage = Integer.parseInt(editVehicleMileage.getText().toString());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (vehicleYear < 1900 || vehicleYear > currentYear) {
                Toast.makeText(VehicleDetails.this, "Please enter a valid vehicle year", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (vehicleMileage < 0) {
                Toast.makeText(VehicleDetails.this, "Mileage cannot be negative", Toast.LENGTH_SHORT).show();
                return true;
            }

            Vehicle vehicle;

            if (VehicleID == -1) {

                VehicleID = 1;

                List<Vehicle> allVehicles=repository .getAllVehicles();
                if (!allVehicles.isEmpty()) {
                    VehicleID = allVehicles.get(allVehicles.size() - 1).getVehicleID() + 1;
                }

                vehicle = new Vehicle(VehicleID,editVehicleMake.getText().toString(),
                        editVehicleModel.getText().toString(),
                        Integer.parseInt(editVehicleYear.getText().toString()),
                        Integer.parseInt(editVehicleMileage.getText().toString()));
                repository.insert(vehicle);
                this.finish();
            } else {
                vehicle =new Vehicle(VehicleID , editVehicleMake.getText().toString(), editVehicleModel.getText().toString(),
                        Integer.parseInt(editVehicleYear.getText().toString()),
                        Integer.parseInt(editVehicleMileage.getText().toString()));
                repository.update(vehicle);
                this.finish();
            }
        }

        if (item.getItemId() == R.id.vehicledelete) {
            if (VehicleID == -1) {
                this.finish();
            } else {

                Vehicle vehicle = new Vehicle(VehicleID, editVehicleMake.getText().toString(), editVehicleModel.getText().toString(),
                        Integer.parseInt(editVehicleYear.getText().toString()),
                        Integer.parseInt(editVehicleMileage.getText().toString()));
                repository.delete(vehicle);
                this.finish();
            }
            return true;
        }
            if (item.getItemId() == R.id.servicereport) {
                Intent intent = new Intent(VehicleDetails.this, ServiceReport.class);
                intent.putExtra("vehicleID", VehicleID);
                startActivity(intent);
                return true;
            }
            return true;
        }
    }
