package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class metting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TextView dispdate;
    Button sel_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metting);

        dispdate = findViewById(R.id.disp_date);
        sel_date = findViewById(R.id.btn_sel_date);

        sel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
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
        String date = "Date :"+dayOfMonth+"/"+month+"/"+year;
        dispdate.setText(date);
    }
}