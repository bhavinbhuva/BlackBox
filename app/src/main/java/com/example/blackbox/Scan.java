package com.example.blackbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

public class Scan extends AppCompatActivity {
    ImageView imageView;
    Button btn_capture;

    Uri img_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        imageView = findViewById(R.id.img_scan);
        btn_capture = findViewById(R.id.btn_capture);


    }
    public void onChoosefile(View v){
        CropImage.activity().start(Scan.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (requestCode == RESULT_OK){
                img_uri = result.getUri();
                Toast.makeText(this,img_uri.toString(),Toast.LENGTH_LONG).show();
                imageView.setImageURI(img_uri);
                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
            }else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(this,"Possible error is : "+e,Toast.LENGTH_LONG).show();
            }
        }
    }
}