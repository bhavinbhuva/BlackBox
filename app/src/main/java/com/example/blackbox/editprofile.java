package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Userkey;

public class editprofile extends AppCompatActivity {

    String nm,mobno,pnno,adhrno,eml,pass;
    EditText edtnm,edtmobno,edtpanno,edtadharno,edteml,edtpass;
    RequestQueue requestQueue;
    Button btneditprofile,btnlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        edtnm = findViewById(R.id.ep_nm);
        edtmobno = findViewById(R.id.ep_mobno);
        edtpanno = findViewById(R.id.ep_panno);
        edtadharno = findViewById(R.id.ep_adharno);
        edteml = findViewById(R.id.ep_eml);
        edtpass = findViewById(R.id.ep_pass);
        btneditprofile = findViewById(R.id.ep_btnedit);
        btnlogout = findViewById(R.id.btnlogout);

        requestQueue = Volley.newRequestQueue(this);

        String dispurl = "http://192.168.43.122/webappdb/disp.php";

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(Userkey);
                editor.commit();

                finish();

                Intent intent = new Intent(editprofile.this, login.class);
                startActivity(intent);
            }
        });

        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                dispurl,null, new Response.Listener<JSONObject>() {    @Override
        public void onResponse(JSONObject response) {

            Toast.makeText(getApplicationContext(),"before try",Toast.LENGTH_LONG).show();
            try {

                Toast.makeText(getApplicationContext(),"try click",Toast.LENGTH_LONG).show();
                JSONArray students = response.getJSONArray("user_info");
                for (int i = 0 ; i < students.length();i++)
                {
                    JSONObject student = new JSONObject(String.valueOf(i));
                    String nm = student.getString("name");
                    String unm = student.getString("user_name");
                    String upass = student.getString("user_pass");
                    edtnm.append(nm);
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
        requestQueue.add(jsonObjectRequest);
*/
    }

}