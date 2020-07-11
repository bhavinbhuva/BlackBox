package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.View.VISIBLE;
import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Userkey;

public class Scanner extends AppCompatActivity {
    String disp_date, disp_docnm;
    public String UserID;
    TextView tvdochis;
    ImageView img;
    Button btncam,uploadBn;
    Uri uri,imageuri;
    private Bitmap bitmap;
    EditText Name;

    private String UploadUrl = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/uploadinfo.php";
    private final String Scanning_url = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/scannerdetail.php";

    RecyclerView recycler_dochistory;
    private scanneradapter adapter;
    private ArrayList<scannerlistitem> mresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserID = preferences.getString(Userkey,"");

        tvdochis = findViewById(R.id.tv_dochis);
        img = findViewById(R.id.img_view);
        btncam = findViewById(R.id.btncamera);
        uploadBn = findViewById(R.id.uploadBn);
        Name = findViewById(R.id.edt_doc_name);

        recycler_dochistory = findViewById(R.id.recycler_dochistory);
        recycler_dochistory.setHasFixedSize(true);
        recycler_dochistory.setLayoutManager(new LinearLayoutManager(this));
        mresult = new ArrayList<>();

/*
        for (int i = 0 ;i < 10;i++)
        {
            scannerlistitem listitem = new scannerlistitem(
                    "2/8/2000",
                    "9_sjaxbjaasnsk"
            );
            mresult.add(listitem);
        }
        adapter = new scanneradapter(mresult,Scanner.this);
        recycler_dochistory.setAdapter(adapter);
*/
        //this method disply recycaler view to shoe scanner history
        parseData();


        btncam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"button click",Toast.LENGTH_LONG).show();
                CropImage.startPickImageActivity(Scanner.this);
            }
        });
        uploadBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Name.setError("Document Name is Required");
                    return;
                }
                else
                {
                    uploadImage();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            imageuri=CropImage.getPickImageResultUri(this,data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this,imageuri))
            {
                uri = imageuri;
            }
            else {
                startCrop(imageuri);
            }
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK)
            {
                //img.setImageURI(result.getUri());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    img.setImageBitmap(bitmap);
                    img.setVisibility(VISIBLE);
                    Name.setVisibility(VISIBLE);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                //Toast.makeText(this,"image update successfully",Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void startCrop(Uri imageuri1) {
        CropImage.activity(imageuri1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);

    }

    private void uploadImage()
    {
      //  Toast.makeText(getApplicationContext(),"method click",Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(getApplicationContext(),Response,Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());

                    img.setImageResource(0);
                    img.setVisibility(View.GONE);
                    Name.setText("");
                    Name.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                params.put("uid",UserID);
                params.put("imgnm",Name.getText().toString().trim());
                params.put("name",UserID+"_"+Name.getText().toString().trim());
                params.put("s_date",currentDate);
                params.put("image",imageToString(bitmap));
                return params;
            }
        };
        MySingleton.getInstance(Scanner.this).addToRequestQue(stringRequest);

    }

    //volley fetch data code
    private void parseData() {

        //volley select data code start
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Scanning_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
             //           Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                        try {
                            //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray dataArray = jsonObject.getJSONArray("imageinfo");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject data = dataArray.getJSONObject(i);

                                disp_date = data.getString("date");
                                disp_docnm = data.getString("imgnm");

                                mresult.add(new scannerlistitem(disp_date,disp_docnm));

                                adapter = new scanneradapter(mresult, Scanner.this);
                               // adapter.notifyDataSetChanged();
                                recycler_dochistory.setAdapter(adapter);
                            }
                        }
                        catch (JSONException jsonException) {
                            //   Toast.makeText(getApplicationContext(),jsonException.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("uid",UserID);
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
