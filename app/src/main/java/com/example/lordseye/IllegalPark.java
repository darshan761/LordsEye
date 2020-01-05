package com.example.lordseye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class IllegalPark extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illegal_park);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        List<Vehicle> vehicles = new ArrayList<>();

        vehicles.add(new Vehicle("Activa ", "HP Petrol Pump, Munshi nagar, Matunga(W)", R.drawable.activa));
        vehicles.add(new Vehicle("Swift", "KEM Hospital, Dadar", R.drawable.swift));
        vehicles.add(new Vehicle("Toyota", "SPIT, Andheri", R.drawable.toyota));

        CustomAdapter adapter = new CustomAdapter(vehicles,getApplicationContext());
        rv.setAdapter(adapter);
    }
}
