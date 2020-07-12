package com.example.blackbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.blackbox.login.MyPREFERENCES;
import static com.example.blackbox.login.Userkey;

public class meetinghistory extends AppCompatActivity {

    private static final String metting_url = "https://blackbox2.000webhostapp.com/api/app/mettingdetail.php";
    RecyclerView recyclerView;
    TextView tv_nonehis;
    private myadapte adapter;
    private ArrayList<mettinglistitem> mresult;
    String disp_date, disp_time, disp_query, disp_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetinghistory);

        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String UserID = preferences.getString(Userkey,"");


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mresult = new ArrayList<>();
/*
        for (int i = 0 ;i<=10;i++)
        {
            mettinglistitem listitem = new mettinglistitem(
                    "2/8/2000",
                    "15:00",
                    "hii hello",
                    "ok will meet"
            );
            mresult.add(listitem);
        }
        adapter = new myadapte(mresult,meetinghistory.this);
        recyclerView.setAdapter(adapter);*/
//volley select data code
        StringRequest stringRequest = new StringRequest(Request.Method.POST, metting_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseData(response);
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

    private void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

                JSONArray dataArray = jsonObject.getJSONArray("meeting");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject data = dataArray.getJSONObject(i);

                    disp_date = data.getString("date");
                    disp_time = data.getString("time");
                    disp_query = data.getString("query");
                    disp_reply = data.getString("reply");

                    mresult.add(new mettinglistitem(disp_date, disp_time, disp_query, disp_reply));
                    adapter = new myadapte(mresult, meetinghistory.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}
