package com.example.automotivetracker.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automotivetracker.R;
import com.example.automotivetracker.database.Repository;
import com.example.automotivetracker.entities.ServiceRecord;
import com.example.automotivetracker.entities.Vehicle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Service_details extends AppCompatActivity {

    String ServiceType;
    String ServiceDate;
    String ServiceNotes;
    double ServiceCost;
    int ServiceMileage;
    int ServiceID;
    int VehicleID;

    EditText editServiceType;
    EditText editServiceCost;
    EditText editServiceMileage;
    EditText editServiceNotes;
    Button ServiceDateButton;

    final Calendar serviceCalendar = Calendar.getInstance();

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_details);

        repository = new Repository(getApplication());

        editServiceType = findViewById(R.id.serviceTypeEdit);
        editServiceCost = findViewById(R.id.serviceCostEdit);
        editServiceMileage = findViewById(R.id.serviceMileageEdit);
        editServiceNotes = findViewById(R.id.serviceNotesEdit);
        ServiceDateButton = findViewById(R.id.serviceDateButton);
        ServiceID = getIntent().getIntExtra("id", -1);

        VehicleID = getIntent().getIntExtra("vehicleID", -1);

        ServiceType = getIntent().getStringExtra("serviceType");
        ServiceDate = getIntent().getStringExtra("serviceDate");
        ServiceCost = getIntent().getDoubleExtra("cost", 0.0);
        ServiceMileage = getIntent().getIntExtra("mileage", 0);
        ServiceNotes = getIntent().getStringExtra("notes");

        editServiceType.setText(ServiceType);
        editServiceCost.setText(Double.toString(ServiceCost));
        if (ServiceMileage != 0) {
            editServiceMileage.setText(Integer.toString(ServiceMileage));
        }

        editServiceNotes.setText(ServiceNotes);

        if (ServiceDate == null || ServiceDate.isEmpty()) {

            ServiceDate = new SimpleDateFormat(
                    "MM/dd/yyyy",
                    Locale.US
            ).format(new Date());
        }
        ServiceDateButton.setText(ServiceDate);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        try {
            Date currentDate = sdf.parse(ServiceDate);

            if (currentDate != null) {
                serviceCalendar.setTime(currentDate);
            }

        } catch (ParseException e) {

            e.printStackTrace();
        }

        ServiceDateButton.setOnClickListener(v -> {

            new DatePickerDialog(
                    Service_details.this,
                    (view, year, month, day) -> {

                        serviceCalendar.set(Calendar.YEAR, year);
                        serviceCalendar.set(Calendar.MONTH, month);
                        serviceCalendar.set(Calendar.DAY_OF_MONTH, day);
                        updateLabel();
                    },
                    serviceCalendar.get(Calendar.YEAR),
                    serviceCalendar.get(Calendar.MONTH),
                    serviceCalendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.menu_service_details,
                menu
        );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();

            return true;
        }

        if (item.getItemId() == R.id.serviceSave) {
            ServiceMileage = 0;

            if (!editServiceMileage.getText().toString().isEmpty()) {
                try {
                    ServiceMileage = Integer.parseInt(
                            editServiceMileage.getText().toString()
                    );
                } catch (NumberFormatException e) {
                    Toast.makeText(
                            Service_details.this,
                            "Mileage must be a number",
                            Toast.LENGTH_SHORT
                    ).show();
                    return true;
                }
            }

            ServiceNotes = editServiceNotes.getText().toString();

            if (editServiceType.getText().toString().isEmpty() ||
                    editServiceCost.getText().toString().isEmpty()) {

                Toast.makeText(Service_details.this,
                        "Please enter the service information", Toast.LENGTH_SHORT
                ).show();
                return true;
            }
            double serviceCost;
            try {
                serviceCost = Double.parseDouble(
                        editServiceCost.getText().toString()
                );
            } catch (NumberFormatException e) {
                Toast.makeText(
                        Service_details.this,
                        "Cost must be a number",
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }

            if (serviceCost < 0) {
                Toast.makeText(
                        Service_details.this,
                        "Cost cannot be negative",
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }

            if (ServiceMileage < 0) {
                Toast.makeText(
                        Service_details.this,
                        "Mileage cannot be negative",
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }

            ServiceCost = serviceCost;
            ServiceRecord serviceRecord;

            if (ServiceID == -1) {
                ServiceID = 1;
                List<ServiceRecord> allServiceRecords =
                        repository.getAllServiceRecords();

                if (!allServiceRecords.isEmpty()) {
                    ServiceID=allServiceRecords.get(
                            allServiceRecords.size() - 1
                    ).getServiceID() + 1;
                }

                serviceRecord = new ServiceRecord(
                        ServiceID,
                        VehicleID,
                        editServiceType.getText().toString(),
                        ServiceDate,
                        ServiceMileage,
                        ServiceCost,
                        ServiceNotes
                );

                repository.insert(serviceRecord);
                finish();

            } else {
                serviceRecord = new ServiceRecord(ServiceID,
                        VehicleID,
                        editServiceType.getText().toString(),
                        ServiceDate,
                        ServiceMileage,
                        ServiceCost,
                        ServiceNotes
                );
                repository.update(serviceRecord);
                finish();
            }
        }


        if (item.getItemId() == R.id.servicedelete) {
            if (ServiceID == -1) {
                finish();
            } else {

                ServiceRecord serviceRecord=new ServiceRecord(ServiceID, VehicleID, editServiceType.getText().toString(),
                                ServiceDate,
                                ServiceMileage,
                                Double.parseDouble(editServiceCost.getText().toString()),
                                ServiceNotes);

                repository.delete(serviceRecord);

                finish();
            }
        }

        if (item.getItemId() == R.id.serviceAlert) {
            Calendar reminderCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(Service_details.this, (view, year, month, dayOfMonth) -> {
                reminderCalendar.set(Calendar.YEAR,year);
                reminderCalendar.set(Calendar.MONTH,month);
                reminderCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                reminderCalendar.set(Calendar.HOUR_OF_DAY,9);

                Long trigger =reminderCalendar.getTimeInMillis();
                Intent intent=new Intent(Service_details.this, ServiceReceiver.class);
                intent.setAction("service action");
                Vehicle vehicle= repository.getVehicleById(VehicleID);
                String vehicleName="";
                if (vehicle !=null) {
                    vehicleName=vehicle.getYear()+ " "+vehicle.getMake()+" " +vehicle.getModel();
                }
                intent.putExtra("key",vehicleName);
                PendingIntent sender = PendingIntent.getBroadcast(Service_details.this, ServiceID+1000,
                        intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager =
                        (AlarmManager) getSystemService(
                                Context.ALARM_SERVICE
                        );
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(Service_details.this, "Service reminder set", Toast.LENGTH_SHORT).show();
                },
                    reminderCalendar.get(Calendar.YEAR),
                    reminderCalendar.get(Calendar.MONTH),
                    reminderCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
            return true;
        }
        return true;
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ServiceDate = sdf.format(serviceCalendar.getTime());
        ServiceDateButton.setText(ServiceDate);
    }
}