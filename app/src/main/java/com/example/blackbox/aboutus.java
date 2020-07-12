package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class aboutus extends AppCompatActivity {


    WebView webView_aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        webView_aboutus = findViewById(R.id.web_aboutus);
        webView_aboutus.getSettings().setJavaScriptEnabled(true);
        webView_aboutus.loadUrl("https://blackbox2.000webhostapp.com/aboutus.html");

    }
}