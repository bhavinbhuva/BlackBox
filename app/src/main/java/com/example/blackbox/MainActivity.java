package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import static com.example.blackbox.login.Useremail;
import static com.example.blackbox.login.Userkey;

public class MainActivity extends AppCompatActivity {

    LinearLayout chat,scan,ledger,payment,metting,setting,aboutus,services;
    FrameLayout frameLayout;
    LinearLayout layout_disconnected;
    ScrollView layout_connected;
    TextView username,txtCount;;
    String UserID, usnm,mobno,pass,userdispurl,UserEMAIL,updateSeenUrl;
    private  int STORAGE_PERMISSION_CODE = 1;
    RequestQueue requestQueue;

    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.txtmain_name);
        txtCount = findViewById(R.id.txtCount);

        chat = findViewById(R.id.menu_chatting);
        scan = findViewById(R.id.menu_scanning);
        ledger = findViewById(R.id.menu_ledger);
        payment = findViewById(R.id.menu_payment);
        metting = findViewById(R.id.menu_meeting);
        setting = findViewById(R.id.menu_setting);
        aboutus = findViewById(R.id.menu_aboutus);
        services= findViewById(R.id.menu_services);

        requestQueue = Volley.newRequestQueue(this);

        userdispurl = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/registration_disp.php";
        updateSeenUrl = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/updateSeen.php";

        frameLayout = findViewById(R.id.frame_dashboard);
        layout_disconnected = findViewById(R.id.layout_disconnected);
        layout_connected = findViewById(R.id.layout_connected);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        final String UserID = preferences.getString(Userkey,"");
        UserEMAIL = preferences.getString(Useremail,"");
        updateSeen();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                updateSeen();
                mHandler.postDelayed(this,2000);
            }
        };
        mRunnable.run();

     //   Toast.makeText(getApplicationContext(),UserID,Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,userdispurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("user");
                    JSONObject data = jsonarray.getJSONObject(0);

                    String firstName = data.getString("uname");
                    username.setText(firstName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong",Toast.LENGTH_LONG).show();
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
                    startActivity(new Intent(MainActivity.this, Scanner.class));
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
                finish();
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

    public void updateSeen()
    {
        StringRequest request = new StringRequest(Request.Method.POST, updateSeenUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                txtCount.setText(response);
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
                parameters.put("message_receiver", UserEMAIL);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                updateSeen();
                mHandler.postDelayed(this,2000);
            }
        };
        mRunnable.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }
}

