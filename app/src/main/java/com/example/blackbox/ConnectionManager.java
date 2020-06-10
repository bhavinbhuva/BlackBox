package com.example.blackbox;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectionManager {
    public static boolean checkConnection(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager!=null){

            NetworkInfo[] infos = manager.getAllNetworkInfo();
            if(infos!=null)
            {
                for(int i =0; i<infos.length; i++)
                {
                    if(infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
