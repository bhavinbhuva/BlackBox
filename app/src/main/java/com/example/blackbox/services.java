package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.webkit.WebView;

public class services extends AppCompatActivity {

    WebView webView_services;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        webView_services = findViewById(R.id.web_services);
        webView_services.getSettings().setJavaScriptEnabled(true);
        webView_services.loadUrl("http://192.168.43.13/blackbox/distribution/api/app/services.html");
    }
}