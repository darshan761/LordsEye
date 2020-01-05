package com.example.lordseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidmads.library.qrgenearator.QRGEncoder;

public class Operator_PO extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Operators");
    private TextView name, car, bike, Address, car_p, bike_p;
    private final String TAG = "Darshan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator__po);
        Intent intent = getIntent();
        final Double lat = intent.getExtras().getDouble("Lat");
        final Double longg = intent.getExtras().getDouble("Long");
        name =findViewById(R.id.name);
        car = findViewById(R.id.car);
        car_p = findViewById(R.id.car_p);
        Address = findViewById(R.id.address);
        bike = findViewById(R.id.bike);
        bike_p = findViewById(R.id.bike_p);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    Toast.makeText(getApplicationContext(),ds.child("Lat").getValue().toString(),Toast.LENGTH_LONG).show();
                    if(ds.child("Lat").getValue().toString().equals(Double.toString(lat)) &&
                            ds.child("Long").getValue().toString().equals(Double.toString(longg))){
                        name.setText(ds.child("Name").getValue().toString());
                        Address.setText(ds.child("Address").getValue().toString());
                        car.setText(ds.child("Car").getValue().toString());
                        bike.setText(ds.child("Bike").getValue().toString());
                        car_p.setText(ds.child("Bike_p").getValue().toString());
                        bike_p.setText(ds.child("Car_p").getValue().toString());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
