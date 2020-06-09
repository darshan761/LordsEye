package com.example.lordseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class AddPark extends AppCompatActivity {
    private EditText name,address,lat,longt,mobile,car,bike;
    private Button add_parking;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_park);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Operators");
        name = findViewById(R.id.Name);
        address = findViewById(R.id.Address);
        mobile = findViewById(R.id.mobile);
        car = findViewById(R.id.car_count);
        bike = findViewById(R.id.bike_count);
        add_parking  = findViewById(R.id.add);

        add_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                HashMap<String,String> op = new HashMap();
                op.put("Name",name.getText().toString());
                op.put("email",address.getText().toString());
                op.put("mobile",mobile.getText().toString());
                op.put("Car",car.getText().toString());
                op.put("Bike",bike.getText().toString());
                myRef.child("Operators").push().setValue(op);
            }
        });



    }
}
