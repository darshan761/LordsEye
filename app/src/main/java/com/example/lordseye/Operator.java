package com.example.lordseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Operator extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Operators");
    StorageReference mImageRef =
            FirebaseStorage.getInstance().getReference("image/test.jpg");
    private TextView name, car, bike, Address, car_p, bike_p;
    private Button getdirection, book, status;
    private ImageView qrImage, parking_img;
    String inputValue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private final String TAG = "Darshan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        Intent intent = getIntent();
        final Double lat = intent.getExtras().getDouble("Lat");
        final Double longg = intent.getExtras().getDouble("Long");
        name =findViewById(R.id.name);
        car = findViewById(R.id.car);
        car_p = findViewById(R.id.car_p);
        Address = findViewById(R.id.address);
        bike = findViewById(R.id.bike);
        bike_p = findViewById(R.id.bike_p);
        getdirection = findViewById(R.id.getdir);
        book = findViewById(R.id.book);
        qrImage = findViewById(R.id.QR);
        parking_img = findViewById(R.id.parking_pic);
        status = findViewById(R.id.status);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    Toast.makeText(getApplicationContext(),ds.child("Lat").getValue().toString(),Toast.LENGTH_LONG).show();
                    if(ds.child("Lat").getValue().toString().equals(Double.toString(lat)) &&
                            ds.child("Long").getValue().toString().equals(Double.toString(longg))){
                        inputValue = ds.getKey();
                       name.setText(ds.child("Name").getValue().toString());
                       Address.setText(ds.child("Address").getValue().toString());
                       car.setText(ds.child("Car").getValue().toString());
                       bike.setText(ds.child("Bike").getValue().toString());
                       car_p.setText(ds.child("Bike_p").getValue().toString());
                       bike_p.setText(ds.child("Car_p").getValue().toString());
                       String url= ds.child("Img").getValue().toString();//Retrieved url as mentioned above
                        Glide.with(getApplicationContext()).load(url).into(parking_img);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DatabaseReference myRef = database.getReference();
                Toast.makeText(getApplicationContext(),"No. of Cars Detected : 2 ",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"No. of Parking Slots Available : 1 ",Toast.LENGTH_LONG).show();
            }
        });


        getdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+longg);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                inputValue = "BHAVYA";
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {

                }

        }
        });

    }
}
