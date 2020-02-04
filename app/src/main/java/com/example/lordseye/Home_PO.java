package com.example.lordseye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_PO extends AppCompatActivity {
    private Button add_park, show_park, map_imageupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__po);
        add_park = findViewById(R.id.add_parking);
        show_park = findViewById(R.id.show_parking);
        map_imageupload = findViewById(R.id.map_image_parking);


        add_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_PO.this, AddPark.class));
            }
        });

        show_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_PO.this, ShowPark.class));
            }
        });

        map_imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_PO.this, Parking_Map_ImageUpload.class));
            }
        });
    }

}
