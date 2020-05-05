package com.example.fix_it_app.Utili;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {

    private Context context;
    public Connection(Context context){
        this.context=context;
    }

    public boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile =  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            mobile.isConnectedOrConnecting();
            wifi.isConnectedOrConnecting();
            return true;
        }
        return false;
    }
}
