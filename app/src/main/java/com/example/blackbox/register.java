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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText input_nm,input_mobno,input_panno,input_adharno,input_email,input_pass;
    RequestQueue requestQueue;
    String insurl = "http://192.168.43.122/BlackBox/api/registration_insert.php";

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

        requestQueue = Volley.newRequestQueue(this);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nm = input_nm.getText().toString();
                final String mobno = input_mobno.getText().toString();
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


                StringRequest request = new StringRequest(Request.Method.POST, insurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("Mobile number or email exist"))
                        {
                            Toast.makeText(getApplicationContext(),"Mobile number or email exist ",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Registration Successfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(register.this,login.class);
                            startActivity(i);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("uname", nm);
                        parameters.put("umobno", mobno);
                        parameters.put("upanno", panno);
                        parameters.put("uadharno", adharno);
                        parameters.put("uemail", email);
                        parameters.put("upass", pass);
                        return parameters;
                    }
                };
                requestQueue.add(request);

            }
        });
    }
}


