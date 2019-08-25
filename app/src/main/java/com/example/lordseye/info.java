package com.example.lordseye;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;

public class info extends AppCompatActivity {

    private Button direction,qr;
    private static final String TAG = "GenerateQRCode";
    ImageView qrImage;
    String inputValue;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        qrImage = (ImageView)findViewById(R.id.code);
        direction = (Button) findViewById(R.id.direction);
        qr = (Button) findViewById(R.id.QR);
//        QRGEncoder qrgEncoder = new QRGEncoder(32, null, QRGContents.Type.TEXT,);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputValue = "hello";
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
//                    edtValue.setError("Required");
                }
            }

        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        String lt = intent.getStringExtra("lat");
                        String ln = intent.getStringExtra("long");
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+lt+","+ln);

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                        startActivity(mapIntent);
                    }
                }, 1000);
            }
        });
    }
}
