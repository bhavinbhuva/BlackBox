package com.example.blackbox;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login<global> extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Userkey= "UserKey";

    EditText txt_mobno, txt_pass;
    Button btnsingup, btnsignin;
    String dispurl,loginurl,Userid = "";
    RequestQueue requestQueue;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(preferences.contains(Userkey))
        {
            startActivity(new Intent(login.this, MainActivity.class));
        }
        txt_mobno = findViewById(R.id.edtlogin_email);
        txt_pass = findViewById(R.id.edtlogin_pass);
        btnsingup = findViewById(R.id.btnsignup);
        btnsignin = findViewById(R.id.btnsingin);
        loginurl = "http://192.168.43.122/BlackBox/api/userlogin.php";
        dispurl = "http://192.168.43.122/BlackBox/api/registration_disp.php";

        requestQueue = Volley.newRequestQueue(this);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mobno = txt_mobno.getText().toString().trim();
                final String pass = txt_pass.getText().toString().trim();

                if (TextUtils.isEmpty(mobno)) {
                    txt_mobno.setError("Mobile Number is Required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    txt_pass.setError("password is required");
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password Too Short Enter 6 or More!", Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest=new StringRequest(Request.Method.POST, loginurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("User Not Found"))
                        {

                            Toast.makeText(login.this,response,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            Userid = response;
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Userkey, Userid);
                            editor.commit();
                            Toast.makeText(login.this,"Login Successfully",Toast.LENGTH_LONG).show();

                            Intent i=new Intent(login.this,MainActivity.class);
                            i.putExtra("userid",Userid);
                            startActivity(i);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        parms.put("umobno",mobno);
                        parms.put("upass",pass);
                        return parms;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
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

