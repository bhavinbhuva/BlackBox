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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    RequestQueue requestQueue;

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

        userdispurl = "http://192.168.43.122/BlackBox/api/registration_disp.php";
        frameLayout = findViewById(R.id.frame_dashboard);
        layout_disconnected = findViewById(R.id.layout_disconnected);
        layout_connected = findViewById(R.id.layout_connected);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String UserID = preferences.getString(Userkey,"");
        //Toast.makeText(getApplicationContext(),UserID,Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, userdispurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("user");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject emp = jsonArray.getJSONObject(i);
                                String name = emp.getString("uname");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

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
