package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    String UserID;
    EditText txt_mobno,txt_pass;
    Button btnsingup,btnsignin;

    FirebaseAuth fauth;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_mobno = findViewById(R.id.edtlogin_usnm);
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

                String mob = txt_mobno.getText().toString().trim();
                String pass = txt_pass.getText().toString().trim();

                if (TextUtils.isEmpty(mob)) {

                    txt_mobno.setError("Mobile Number is Required");
                    //Toast.makeText(getApplicationContext(), "Select Blood Group!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    txt_pass.setError("password is required");
                    // Toast.makeText(getApplicationContext(),"Password is Required",Toast.LENGTH_LONG).show();
                    return;

                }
                if (pass.length() < 6 ) {
                    Toast.makeText(getApplicationContext(), "Password Too Short Enter 6 or More!", Toast.LENGTH_LONG).show();
                    return;
                }
                 fuser = fauth.getCurrentUser();
                 UserID = fuser.getUid();

                 Intent i = new Intent(getApplicationContext(), otpverify.class);
                 i.putExtra("phonenumber", "+91" + txt_mobno.getText().toString());
                 startActivity(i);
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
