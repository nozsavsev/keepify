package com.nozsavsev.keepify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * A BroadcastReceiver that listens for changes in network connectivity.
 * It checks the network status and displays a Toast message indicating whether the network is connected or disconnected.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * It checks the network status and displays a Toast message.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent received.
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (checkInternet(context)) {
            Toast.makeText(context, "Network is connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Network is disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks the network connectivity status.
     *
     * @param context The Context in which the connectivity service is running.
     * @return true if the network is connected, false otherwise.
     */
    boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}