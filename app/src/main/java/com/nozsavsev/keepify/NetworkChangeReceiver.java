package com.nozsavsev.keepify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (checkInternet(context)) {
            Toast.makeText(context, "Network is connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Network is disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
