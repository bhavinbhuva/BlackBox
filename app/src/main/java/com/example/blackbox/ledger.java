package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Userkey;

public class ledger extends AppCompatActivity {
    WebView web_ledger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String UserID = preferences.getString(Userkey,"");
        String url = "https://blackbox2.000webhostapp.com/api/gen_ledger.php?id="+UserID;
        web_ledger = findViewById(R.id.web_ledger);
        web_ledger.getSettings().setJavaScriptEnabled(true);
        web_ledger.loadUrl(url.trim());
    }
}