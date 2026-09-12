package com.example.automotivetracker.ui;

import com.example.automotivetracker.R;
import com.example.automotivetracker.entities.Vehicle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Vehicle_adapter extends RecyclerView.Adapter<Vehicle_adapter.VehicleViewHolder> {
    private List<Vehicle> mVehicle;
    private final Context context;
    private final LayoutInflater mInflater;

    public Vehicle_adapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {
        private final TextView VehicleItemView;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            VehicleItemView=itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        final Vehicle current=mVehicle.get(position);

                        Intent intent=new Intent(context,VehicleDetails.class);

                        intent.putExtra("id",current.getVehicleID());
                        intent.putExtra("make",current.getMake());
                        intent.putExtra("model",current.getModel());
                        intent.putExtra("year",current.getYear());
                        intent.putExtra("mileage",current.getMileage());

                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Vehicle_adapter.VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item,parent,false);
        return new VehicleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Vehicle_adapter.VehicleViewHolder holder, int position) {
        if(mVehicle!=null) {
            Vehicle current = mVehicle.get(position);

            String name = current.getYear() + " " + current.getMake() + " " + current.getModel();

            holder.VehicleItemView.setText(name);
        }
        else{
            holder.VehicleItemView.setText("No vehicle");
        }
    }
    @Override
    public int getItemCount() {
        if(mVehicle!=null) {
            return mVehicle.size();
        }
        else return 0;
    }

    public void setVehicle(List<Vehicle> vehicles){
        mVehicle = vehicles;
        notifyDataSetChanged();
    }
}