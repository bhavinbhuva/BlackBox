package com.example.blackbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class metting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TextView dispdate,sel_time,disptime;
    Button sel_date,send_query;
    EditText query;
    int hour,min;

    String date,time,qvery,UserID,reply = "",flag = "0";



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


        if(date == null ||time == null)
        {
            Toast.makeText(getApplicationContext(), " Date And Time Required ", Toast.LENGTH_LONG).show();
        }
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
                                try {
                                    Date date = f24hour.parse(time);
                                    SimpleDateFormat f12hour = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    disptime.setText(f12hour.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });

        send_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qvery = query.getText().toString().trim();
                mettingclass mettingdata = new mettingclass(date, time, qvery, UserID, reply, flag);

            }
        });

        };

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
         date = dayOfMonth+"/"+month+"/"+year;
         dispdate.setText(date);
    }
}