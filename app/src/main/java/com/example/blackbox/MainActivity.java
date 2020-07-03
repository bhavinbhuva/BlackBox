package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Userkey;

public class MainActivity extends AppCompatActivity {

    LinearLayout chat,scan,ledger,payment,metting,setting,aboutus,services;
    FrameLayout frameLayout;
    LinearLayout layout_disconnected;
    ScrollView layout_connected;
    TextView username;
    String UserID, usnm,mobno,pass,userdispurl;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.txtmain_name);

        chat = findViewById(R.id.menu_chatting);
        scan = findViewById(R.id.menu_scanning);
        ledger = findViewById(R.id.menu_ledger);
        payment = findViewById(R.id.menu_payment);
        metting = findViewById(R.id.menu_meeting);
        setting = findViewById(R.id.menu_setting);
        aboutus = findViewById(R.id.menu_aboutus);
        services= findViewById(R.id.menu_services);

        frameLayout = findViewById(R.id.frame_dashboard);
        layout_disconnected = findViewById(R.id.layout_disconnected);
        layout_connected = findViewById(R.id.layout_connected);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String UserID = preferences.getString(Userkey,"");

        StringRequest stringRequest=new StringRequest(Request.Method.POST,userdispurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("User Not Found"))
                {

                    Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                }
                else
                {
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
                parms.put("uid", UserID);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        if(ConnectionManager.checkConnection(getBaseContext())){
            Snackbar.make(frameLayout,"Connected",Snackbar.LENGTH_LONG).show();

            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, chatting.class));

                }
            });
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               //     startActivity(new Intent(MainActivity.this, Scan.class));
                }
            });
            payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, payment.class));
                }
            });
            metting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, metting.class));
                }
            });
        }
        else
        {
            Snackbar.make(frameLayout,"Not Connected",Snackbar.LENGTH_LONG).show();
        }
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, editprofile.class);
                startActivity(i);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, aboutus.class));
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, services.class));
            }
        });


    }
}
