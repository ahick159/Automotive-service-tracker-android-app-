package com.example.automotivetracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automotivetracker.R;
import com.example.automotivetracker.database.Repository;
import com.example.automotivetracker.entities.Vehicle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VehicleList extends AppCompatActivity {

    private Repository repository;
    private Vehicle_adapter vehicleAdapter;
    private RecyclerView recyclerView;
    private EditText searchVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(VehicleList.this, VehicleDetails.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        searchVehicle=findViewById(R.id.searchVehicle);
        repository=new Repository(getApplication());
        List<Vehicle> allVehicles = repository.getAllVehicles();
        vehicleAdapter = new Vehicle_adapter(this);
        recyclerView.setAdapter(vehicleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)
        );
        loadVehicles();

        searchVehicle.setOnEditorActionListener((v, actionId, event) -> {
            String searchText =searchVehicle.getText().toString();
            if (searchText.isEmpty()) {
                loadVehicles();

            } else {
                List<Vehicle>vehicles=repository.searchVehicles(searchText);
                vehicleAdapter.setVehicle(vehicles);
            }
            return false;
        });
    }

    private void loadVehicles() {
        List<Vehicle>allVehicles=repository.getAllVehicles();
        vehicleAdapter.setVehicle(allVehicles);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(repository != null&&vehicleAdapter != null) {
            loadVehicles();
        }
    }
}