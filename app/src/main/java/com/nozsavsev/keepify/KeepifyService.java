package com.nozsavsev.keepify;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class KeepifyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        System.out.println("Service started");
        sendNotification("Hi there", "The app has been opened.");
        return START_STICKY;
    }

    private void sendNotification(String title, String message) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, "startedNotifyChannel")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.home)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendNotification("App Exited", "Your app has been closed.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
