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

public class editprofile extends AppCompatActivity {

    String UserID,nm,mobno,pnno,adhrno,eml,pass;
    EditText edtnm,edtmobno,edtpanno,edtadharno,edteml,edtpass;
    Button btneditprofile,btnlogout;

    private DatabaseReference reff;
    private FirebaseUser mUser;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        fauth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("registration");
        mUser = fauth.getCurrentUser();
        UserID = mUser.getUid();

        edtnm =findViewById(R.id.ep_nm);
        edtmobno = findViewById(R.id.ep_mobno);
        edtpanno = findViewById(R.id.ep_panno);
        edtadharno = findViewById(R.id.ep_adharno);
        edteml = findViewById(R.id.ep_eml);
        edtpass = findViewById(R.id.ep_pass);
        btneditprofile = findViewById(R.id.ep_btnedit);
        btnlogout = findViewById(R.id.btnlogout);

        reff.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nm = dataSnapshot.child("name").getValue(String.class);
                mobno = dataSnapshot.child("mobileno").getValue(String.class);
                pnno = dataSnapshot.child("panno").getValue(String.class);
                adhrno = dataSnapshot.child("adharno").getValue(String.class);
                eml = dataSnapshot.child("email").getValue(String.class);
                pass = dataSnapshot.child("pass").getValue(String.class);
    //            Toast.makeText(getApplicationContext(),"Name : "+nm+" mobile no : " +mobno+"panno : "+pnno+"Adharno : "+adhrno +"Email : "+eml+" password : "+pass,Toast.LENGTH_LONG).show();

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

                Toast.makeText(getApplicationContext(),"Update Successfully",Toast.LENGTH_LONG).show();
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fauth.getInstance().signOut();
                Intent i = new Intent(editprofile.this, login.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
    }
}