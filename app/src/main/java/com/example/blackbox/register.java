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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    String UserID="";
    EditText input_nm,input_mobno,input_panno,input_adharno,input_email,input_pass;
    Userclass userclass;

    DatabaseReference reff;
    FirebaseAuth fauth;
    FirebaseUser fuser;

    Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_nm = findViewById(R.id.edtreg_nm);
        input_mobno = findViewById(R.id.edtreg_mobno);
        input_panno = findViewById(R.id.edtreg_panno);
        input_adharno = findViewById(R.id.edtreg_adharno);
        input_email =  findViewById(R.id.edtreg_email);
        input_pass = findViewById(R.id.edtreg_pass);
        btnregister = findViewById(R.id.btnregsuccess);

        fauth = FirebaseAuth.getInstance();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nm = input_nm.getText().toString().trim();
                final String mobno = input_mobno.getText().toString().trim();
                final String panno = input_panno.getText().toString().trim();
                final String adharno = input_adharno.getText().toString().trim();
                final String email = input_email.getText().toString().trim();
                final String pass = input_pass.getText().toString().trim();

                if (TextUtils.isEmpty(nm)) {
                    input_nm.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(mobno)) {
                    input_mobno.setError("Mobile Number is Required");
                    return;
                }
                if (mobno.length() != 10) {
                    input_mobno.setError("Mobile Number is Must be 10 digit");
                    return;
                }
                if (TextUtils.isEmpty(panno)) {
                    input_panno.setError("Pancard Number is Required");
                    return;
                }
                if (panno.length() != 10) {
                    input_panno.setError("pancard Number is Must be 10 ");
                    return;
                }
                if (TextUtils.isEmpty(adharno)) {
                    input_adharno.setError("Adharcard Number is Required");
                    return;
                }
                if (adharno.length() != 12) {
                    input_adharno.setError("Adharcard Number is Must be 12 ");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    input_pass.setError("Password is Required");
                    return;
                }
                if (pass.length() < 6 && pass.length() > 20) {
                    input_pass.setError("Enter 6 to 20 characters or digit!");
                    return;
                }
                userclass = new Userclass();
                reff = FirebaseDatabase.getInstance().getReference("registration");
                //userclass.setName(nm);
                //reff.push().setValue(userclass);
                 Toast.makeText(register.this,"reffrence ::"+reff,Toast.LENGTH_LONG).show();
                 fauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()) {
                            fuser = fauth.getCurrentUser();
                            UserID = fuser.getUid();
                            Toast.makeText(register.this, UserID, Toast.LENGTH_SHORT).show();

                            Userclass userdata = new Userclass(nm, mobno, panno, adharno, email, pass, UserID);

                            reff.child(UserID).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(register.this, "Registration Complete ", Toast.LENGTH_SHORT).show();

                                    Toast.makeText(register.this,"userid"+ UserID , Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(register.this, MainActivity.class));
                                }
                            });
                        }
                        else {
                            Toast.makeText(register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

