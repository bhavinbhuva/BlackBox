package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText txt_mobno,txt_pass;
    Button btnsingup,btnsignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_mobno = findViewById(R.id.edtlogin_usnm);
        txt_pass =  findViewById(R.id.edtlogin_pass);
        btnsingup = findViewById(R.id.btnsignup);
        btnsignin = findViewById(R.id.btnsingin);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txt_mobno.getText().toString().trim();
                String pass = txt_pass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

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
                startActivity(new Intent(login.this, MainActivity.class));
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
