package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    LinearLayout layout_disconnected;
    ScrollView layout_connected;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame_dashboard);
        layout_disconnected = findViewById(R.id.layout_disconnected);
        layout_connected = findViewById(R.id.layout_connected);

        if(ConnectionManager.checkConnection(getBaseContext())){
            Snackbar.make(frameLayout,"Connected",Snackbar.LENGTH_LONG).show();
        }
        else
        {
            Snackbar.make(frameLayout,"Not Connected",Snackbar.LENGTH_LONG).show();
        }
   }

}
