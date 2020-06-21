package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {


    EditText txt_email,txt_pass;
    Button btnsingup,btnsignin;

    FirebaseAuth fauth;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = findViewById(R.id.edtlogin_email);
        txt_pass =  findViewById(R.id.edtlogin_pass);
        btnsingup = findViewById(R.id.btnsignup);
        btnsignin = findViewById(R.id.btnsingin);

        fauth = FirebaseAuth.getInstance();

        if (fauth.getCurrentUser() != null) {
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txt_email.getText().toString().trim();
                String pass = txt_pass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    txt_email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    txt_pass.setError("password is required");
                    return;
                }
                if (pass.length() < 6 ) {
                    Toast.makeText(getApplicationContext(), "Password Too Short Enter 6 or More!", Toast.LENGTH_LONG).show();
                    return;
                }
                fauth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(login.this, MainActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login Fail or user name not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btnsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
            }
        });
    }
}
