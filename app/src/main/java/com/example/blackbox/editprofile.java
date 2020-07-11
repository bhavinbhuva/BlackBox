package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Useremail;
import static com.example.blackbox.login.Userkey;

public class editprofile extends AppCompatActivity {

    String nm,mobno,panno,adhrno,eml,pass,userdispurl,userupdateurl;
    EditText edtnm,edtmobno,edtpanno,edtadharno,edteml,edtpass;
    RequestQueue requestQueue,requestQueueupd;
    Button btneditprofile,btnlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        edtnm = findViewById(R.id.ep_nm);
        edtmobno = findViewById(R.id.ep_mobno);
        edtpanno = findViewById(R.id.ep_panno);
        edtadharno = findViewById(R.id.ep_adharno);
        edteml = findViewById(R.id.ep_eml);
        edtpass = findViewById(R.id.ep_pass);
        btneditprofile = findViewById(R.id.ep_btnedit);
        btnlogout = findViewById(R.id.btnlogout);

        requestQueue = Volley.newRequestQueue(this);
        requestQueueupd = Volley.newRequestQueue(this);



        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String UserID = preferences.getString(Userkey,"");

        userdispurl = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/registration_disp.php";
        userupdateurl = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/registration_update.php";

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(Userkey);
                editor.remove(Useremail);
                editor.commit();

                finish();

                Intent intent = new Intent(editprofile.this, login.class);
                startActivity(intent);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,userdispurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("user");
                    JSONObject data = jsonarray.getJSONObject(0);

                    nm = data.getString("uname");
                    mobno = data.getString("umobno");
                    panno = data.getString("upanno");
                    adhrno = data.getString("uadharno");
                    eml = data.getString("uemail");
                    pass = data.getString("upass");

                    edtnm.setText(nm);
                    edtmobno.setEnabled(false);
                    edtmobno.setText(mobno);
                    edtpanno.setText(panno);
                    edtadharno.setText(adhrno);
                    edteml.setEnabled(false);
                    edteml.setText(eml);
                    edtpass.setText(pass);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editprofile.this, "Something went wrong",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("uid",UserID);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);

        btneditprofile.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             StringRequest requestupd = new StringRequest(Request.Method.POST, userupdateurl, new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {

                     if(response.equals("Update Successfully"))
                     {
                         Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
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
                     parameters.put("uid",UserID);
                     parameters.put("uname",edtnm.getText().toString());
                     parameters.put("upanno", edtmobno.getText().toString());
                     parameters.put("uadharno", edtpanno.getText().toString());
                     parameters.put("upass", edtpass.getText().toString());
                     return parameters;
                 }
             };
             requestQueueupd.add(requestupd);
         }
     });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(editprofile.this,MainActivity.class));
        finish();
    }
}
