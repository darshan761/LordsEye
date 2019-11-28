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

public class LoginPO extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,pass;
    private Button login, register;
    private static final String TAG = "Login";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_po);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.login);
        pass=(EditText)findViewById(R.id.Pass);
        login = (Button)findViewById(R.id.SignIn);
        register=(Button)findViewById(R.id.Register);
        progressDialog = new ProgressDialog(LoginPO.this);
        progressDialog.setMessage("Please wait..."); // Setting Message
        progressDialog.setTitle("Logging In"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPO.this,RegisterPO.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(LoginPO.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent i = new Intent(LoginPO.this,Home_PO.class);
                                    startActivity(i);
                                    progressDialog.dismiss();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginPO.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    // updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }
}
