package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    LinearLayout chat,scan,ledger,payment,metting,help,aboutus,services;
    FrameLayout frameLayout;
    LinearLayout layout_disconnected;
    ScrollView layout_connected;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat = findViewById(R.id.menu_chatting);
        scan = findViewById(R.id.menu_scanning);
        ledger = findViewById(R.id.menu_ledger);
        payment = findViewById(R.id.menu_payment);
        metting = findViewById(R.id.menu_meeting);
        help = findViewById(R.id.menu_help);
        aboutus = findViewById(R.id.menu_aboutus);
        services= findViewById(R.id.menu_services);

        frameLayout = findViewById(R.id.frame_dashboard);
        layout_disconnected = findViewById(R.id.layout_disconnected);
        layout_connected = findViewById(R.id.layout_connected);
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
                    startActivity(new Intent(MainActivity.this, Scan.class));
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

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, help.class));
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
