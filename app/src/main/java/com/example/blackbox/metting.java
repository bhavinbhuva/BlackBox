package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Userkey;

public class metting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TextView dispdate,sel_time,disptime;
    Button sel_date,send_query,send_history;
    EditText query;
    int hour,min;
    RequestQueue requestQueue;

    String date,time,qvery,tdate,ttime,UserID,reply = "";
    String meeting_insurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metting);

        dispdate = findViewById(R.id.disp_date);
        sel_date = findViewById(R.id.btn_sel_date);
        disptime = findViewById(R.id.disp_time);
        sel_time = findViewById(R.id.btn_sel_time);
        query = findViewById(R.id.edt_send_query);

        send_query = findViewById(R.id.btn_place_req);
        send_history = findViewById(R.id.btn_history);

        meeting_insurl = "http://192.168.0.118:90/final_blackbox/BlackBox/distribution/api/app/metting_insert.php";

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String UserID = preferences.getString(Userkey,"");

        /*if(date == null ||time == null)
        {
            Toast.makeText(getApplicationContext(), " Date And Time Required ", Toast.LENGTH_LONG).show();
        }*/
        sel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
            }
        });
        sel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        metting.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;

                                time = hour + ":" + min;

                                SimpleDateFormat f24hour = new SimpleDateFormat(
                                  "HH:mm"
                                );
                                Date date = null;
                                try {
                                    date = f24hour.parse(time);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                disptime.setText(f24hour.format(date));
                            }
                        },24,0,true
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        send_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(metting.this, meetinghistory.class));
            }
        });
        send_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                qvery = query.getText().toString().trim();
                tdate = sel_date.getText().toString().trim();
                ttime = sel_time.getText().toString().trim();

                if (TextUtils.isEmpty(qvery)) {
                    query.setError("Query is Required");
                    return;
                }

                StringRequest request = new StringRequest(Request.Method.POST, meeting_insurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Data Insertion Success"))
                        {
                                Toast.makeText(getApplicationContext(),"Metting Request Send Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Metting Request Not Send Try Again..",Toast.LENGTH_LONG).show();
                        }

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
                        parameters.put("uid", UserID);
                        parameters.put("query",qvery);
                        parameters.put("reply",reply);
                        parameters.put("date",date);
                        parameters.put("time",time);

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }
        });
    }
    private void showDatePickerDailog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog= new DatePickerDialog(
                metting.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                this,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
         date = dayOfMonth+"-"+month+"-"+year;
         dispdate.setText(date);
    }
}