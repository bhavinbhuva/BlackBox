package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
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

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Useremail;

public class chatting extends AppCompatActivity {
    WebView webView_services;
    String UserEMAIL;
    RequestQueue requestQueue;
    String updateUrl = "http://192.168.0.118:90/final_blackbox/blackbox/distribution/updateSeenApp.php";
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        getSupportActionBar().setTitle("Chat With Admin");
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserEMAIL = preferences.getString(Useremail,"");
        Toast.makeText(getApplicationContext(),UserEMAIL,Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(this);
        updateStatus();

        webView_services = findViewById(R.id.web_chat);
        webView_services.getSettings().setJavaScriptEnabled(true);
        webView_services.loadUrl("http://192.168.0.118:90/final_blackbox/blackbox/distribution/chatDisplayApp.php?email="+UserEMAIL);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                updateStatus();
                mHandler.postDelayed(this,2000);
            }
        };
        mRunnable.run();

    }

    public void updateStatus()
    {
        StringRequest request = new StringRequest(Request.Method.POST, updateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email", UserEMAIL);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        finish();
    }
}