package com.example.lordseye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.PersonViewHolder>{

    Context context;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView VehicleName;
        TextView Location;
        ImageView IMage;
        Button echallan;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            VehicleName = (TextView)itemView.findViewById(R.id.vehicle_name);
            Location = (TextView)itemView.findViewById(R.id.location);
            IMage = (ImageView)itemView.findViewById(R.id.veh_image);
            echallan = (Button)itemView.findViewById(R.id.echallan);
        }
    }

    List<Vehicle> vehicles;

    CustomAdapter(List<Vehicle> vehicles, Context context){
        this.vehicles = vehicles;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.VehicleName.setText(vehicles.get(position).name);
        holder.Location.setText(vehicles.get(position).location);
        holder.IMage.setImageResource(vehicles.get(position).photoId);
        holder.echallan.setTag(position);
        holder.echallan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,echallan.class);
                i.addFlags(FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}