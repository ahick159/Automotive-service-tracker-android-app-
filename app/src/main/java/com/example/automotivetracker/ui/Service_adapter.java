package com.example.automotivetracker.ui;

import com.example.automotivetracker.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automotivetracker.entities.ServiceRecord;

import java.util.List;

public class Service_adapter extends RecyclerView.Adapter<Service_adapter.ServiceViewHolder> {
    private List<ServiceRecord> mServiceRecord;
    private final Context context;
    private final LayoutInflater mInflater;

    public Service_adapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final TextView serviceItemView;
        private final TextView serviceItemView2;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceItemView = itemView.findViewById(R.id.textView2);
            serviceItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {

                        final ServiceRecord current =
                                mServiceRecord.get(position);

                        Intent intent =
                                new Intent(context, Service_details.class);

                        intent.putExtra("id", current.getServiceID());
                        intent.putExtra("vehicleID", current.getVehicleID());
                        intent.putExtra("serviceType", current.getServiceType());
                        intent.putExtra("serviceDate", current.getServiceDate());
                        intent.putExtra("cost", current.getCost());
                        intent.putExtra("mileage", current.getMileage());
                        intent.putExtra("notes", current.getNotes());

                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Service_adapter.ServiceViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View itemView =
                mInflater.inflate(
                        R.layout.service_list_item,
                        parent,
                        false
                );

        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(
            @NonNull Service_adapter.ServiceViewHolder holder,
            int position) {
        if (mServiceRecord != null) {

            ServiceRecord current =
                    mServiceRecord.get(position);

            holder.serviceItemView.setText(
                    current.getServiceType()
            );
            holder.serviceItemView2.setText(
                    current.getServiceDate()
            );
        } else {
            holder.serviceItemView.setText("No service");
            holder.serviceItemView2.setText("No date");
        }
    }

    public void setServiceRecord(List<ServiceRecord> serviceRecords) {
        mServiceRecord = serviceRecords;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {

        if (mServiceRecord != null) {
            return mServiceRecord.size();
        } else {
            return 0;
        }
    }
}