/*
package com.example.blackbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class otpverify extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText editTextCarrierNumber;


    private PhoneAuthProvider.ForceResendingToken resendcode;

    FirebaseAuth fauth;
    DatabaseReference reff;
    FirebaseUser fuser;
    Userclass userclass;

    private String verificationid;
    String nm,phnno,pnno,adharno,eml,pass,Userid;
    EditText input_otp;
    Button creteccount,sendcodeagain,sendcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        ccp = findViewById(R.id.ccp);
        editTextCarrierNumber = findViewById(R.id.edt_phn_number);
        sendcode = findViewById(R.id.btn_send_code);
        */
/*
        * put this two line in onclick function and that will
        * Return phone number with country code ex = +91 9773049694

            -> merge phone number with country code   /* ccp.registerCarrierNumberEditText(editTextCarrierNumber);
            -> Return full formated phone number      /* phnno = ccp.getFormattedFullNumber().toString();
        *//*

//        reff = FirebaseDatabase.getInstance().getReference().child("registration");
//        fauth = FirebaseAuth.getInstance();

        input_otp = findViewById(R.id.edtotpverify);
        creteccount=findViewById(R.id.btnotp_verify);
        sendcodeagain =findViewById(R.id.btnotp_resend);
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ccp.registerCarrierNumberEditText(editTextCarrierNumber);
                phnno = ccp.getFormattedFullNumber().toString();
                sendVerificationCode(phnno);
            }
        });


        nm = getIntent().getStringExtra("rname");
        pnno = getIntent().getStringExtra("rpanno");
        adharno = getIntent().getStringExtra("radharno");
        eml = getIntent().getStringExtra("remail");
        pass = getIntent().getStringExtra("rpass");

//        sendVerificationCode(phnno);

        sendcodeagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phnno,resendcode);
            }
        });
        creteccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = input_otp.getText().toString();
                if ((code.isEmpty() || code.length() < 6)){

                  //  codeid.setError("Enter code...");
      //              Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
                    input_otp.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code){
      //  Toast.makeText(getApplicationContext(),"verify code method ukkered",Toast.LENGTH_SHORT).show();

*/
/*
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
*//*

        input_otp.setText(verificationid);
//        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential)
    {
       // Toast.makeText(otpverify.this,Userid, Toast.LENGTH_LONG).show();

        fauth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            fauth.createUserWithEmailAndPassword(eml, pass).addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {
                                                fuser = fauth.getCurrentUser();
                                                Userid = fuser.getUid();
                                                Toast.makeText(otpverify.this, Userid, Toast.LENGTH_SHORT).show();
                                                Userclass userdata = new Userclass(nm, phnno, pnno, adharno, eml, pass, Userid);
                                                Toast.makeText(otpverify.this, "Name : "+nm+"phnno : "+phnno+"Panno :"+pnno+"adharno :"+adharno+"email"+eml+"pass"+pass +"User id :"+Userid, Toast.LENGTH_LONG).show();

                                                reff.child(Userid).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(otpverify.this, Userid, Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                    }
                                                });
                                            }
                                            else
                                            {
                                                Toast.makeText(otpverify.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        } });
                                        //Intent intent = new Intent(otpverify.this,MainActivity.class);
                                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        // startActivity(intent);
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(otpverify.this,"Invalid Code", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(otpverify.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number)
    {

    //    Toast.makeText(otpverify.this, "send verifiction method ukkeerd",Toast.LENGTH_LONG).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
          //  Toast.makeText(getApplicationContext(),"send code successfulll",Toast.LENGTH_SHORT).show();
            super.onCodeSent(s, forceResendingToken);
             verificationid = s;
             resendcode = forceResendingToken;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
            //    Toast.makeText(getApplicationContext(),"SEND CODE SUCCESSFULL",Toast.LENGTH_SHORT).show();
      //          progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
         //   Toast.makeText(getApplicationContext(),"verification failed",Toast.LENGTH_SHORT).show();
            Toast.makeText(otpverify.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
}*/
