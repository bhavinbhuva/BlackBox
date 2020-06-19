package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setting_manageprofile extends AppCompatActivity {


    FirebaseUser fuser;
    FirebaseAuth fauth;
    DatabaseReference reff;

    String UserID,nm,mobno,pnno,adhrno,eml,pass;
    EditText edtnm,edtmobno,edtpanno,edtadharno,edteml,edtpass;
    Button btneditprofile,btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_manageprofile);

        fauth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference("regidtration");
        fuser = fauth.getCurrentUser();
        UserID = fuser.getUid();

        Toast.makeText(this,"userid:"+UserID,Toast.LENGTH_LONG).show();

        edtnm =findViewById(R.id.ep_nm);
        edtmobno = findViewById(R.id.ep_mobno);
        edtpanno = findViewById(R.id.ep_panno);
        edtadharno = findViewById(R.id.ep_adharno);
        edteml = findViewById(R.id.ep_eml);
        edtpass = findViewById(R.id.ep_pass);
        btneditprofile = findViewById(R.id.ep_btnedit);
        btnlogout = findViewById(R.id.btnlogout);

        reff.child("lLVo0Zorc9WFX0VV4iyRL4FALmu1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nm = dataSnapshot.child("name").getValue(String.class);
                mobno=dataSnapshot.child("mobileno").getValue(String.class);
                pnno = dataSnapshot.child("panno").getValue(String.class);
                adhrno =dataSnapshot.child("adharno").getValue(String.class);
                eml = dataSnapshot.child("email").getValue(String.class);
                pass = dataSnapshot.child("pass").getValue(String.class);

                Toast.makeText(setting_manageprofile.this, UserID, Toast.LENGTH_LONG).show();

                Toast.makeText(setting_manageprofile.this, "Name :" + nm, Toast.LENGTH_SHORT).show();
                Toast.makeText(setting_manageprofile.this, "Mobno :" + mobno, Toast.LENGTH_SHORT).show();
                Toast.makeText(setting_manageprofile.this, "pnno :" + pnno, Toast.LENGTH_SHORT).show();

                edtnm.setText(nm);
                edtmobno.setText(mobno);
                edtpanno.setText(pnno);
                edtadharno.setText(adhrno);
                edteml.setText(eml);
                edtpass.setText(pass);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reff.child(UserID).child("name").setValue(edtnm.getText().toString());
                reff.child(UserID).child("mobile").setValue(edtmobno.getText().toString());
                reff.child(UserID).child("panno").setValue(edtpanno.getText().toString());
                reff.child(UserID).child("adharno").setValue(edtadharno.getText().toString());
                reff.child(UserID).child("email").setValue(edteml.getText().toString());
                reff.child(UserID).child("pass").setValue(edtpass.getText().toString());

            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fauth.getInstance().signOut();
                finish();
                startActivity(new Intent(setting_manageprofile.this, login.class));

            }
        });
    }
}