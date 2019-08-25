package com.example.lordseye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterPO extends AppCompatActivity {
    private EditText name,email,pass,phone;
    private Button register;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_po);
        mAuth = FirebaseAuth.getInstance();
        phone=(EditText)findViewById(R.id.mobile);
        pass=(EditText)findViewById(R.id.Pass);
        name=(EditText)findViewById(R.id.Name);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.mobile);
        register=(Button)findViewById(R.id.register);

        progressDialog = new ProgressDialog(RegisterPO.this);
        progressDialog.setMessage("Please wait..."); // Setting Message
        progressDialog.setTitle("Signing Up"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(RegisterPO.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegisterPO.this, "Authentication Successfull.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference();
                                    HashMap<String,String> op = new HashMap();
                                    op.put("Name",name.getText().toString());
                                    op.put("email",user.getEmail());
                                    op.put("mobile",phone.getText().toString());
                                    myRef.child("Operators").child(user.getUid()).push().setValue(op);
                                    Intent i = new Intent(RegisterPO.this,LoginPO.class);
                                    startActivity(i);
                                    progressDialog.dismiss();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterPO.this, "Authentication failed.",
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
