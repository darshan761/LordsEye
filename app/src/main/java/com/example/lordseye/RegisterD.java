package com.example.lordseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterD extends AppCompatActivity {

    private EditText name,email,pass,vehicle_no,vehicle,phone;
    private Button register;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_d);
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.Name);
        pass=(EditText)findViewById(R.id.Pass);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.mobile);
        vehicle=(EditText)findViewById(R.id.vehicle);
        vehicle_no=(EditText)findViewById(R.id.vehicle_no);
        register=(Button)findViewById(R.id.register);
        final Spinner spinner = (Spinner) findViewById(R.id.veh);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicles, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        progressDialog = new ProgressDialog(RegisterD.this);
        progressDialog.setMessage("Please wait..."); // Setting Message
        progressDialog.setTitle("Signing up"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(RegisterD.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference();
                                    HashMap<String,String> op = new HashMap();
                                    op.put("Name",name.getText().toString());
                                    op.put("email",user.getEmail());
                                    op.put("mobile",phone.getText().toString());
                                    op.put("vehicle type",spinner.getSelectedItem().toString());
                                    op.put("vehicle",vehicle.getText().toString());
                                    op.put("Vehicle No",vehicle_no.getText().toString());
                                    myRef.child("Drivers").child(user.getUid()).push().setValue(op);
                                    Intent i = new Intent(RegisterD.this,LoginD.class);
                                    startActivity(i);
                                    progressDialog.dismiss();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterD.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                   // updateUI(null);
                                    progressDialog.dismiss();
                                }

                                // ...
                            }
                        });
            }
        });
    }
}
