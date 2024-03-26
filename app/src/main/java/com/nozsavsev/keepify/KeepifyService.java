package com.nozsavsev.keepify;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

/**
 * This class extends the Service class and is used to handle background tasks.
 * It sends notifications when the service starts and when it is destroyed.
 */
public class KeepifyService extends Service {

    /**
     * This method is called when the service is started.
     * It sends a notification indicating that the app has been opened.
     *
     * @param intent  The Intent that was used to bind to the service.
     * @param flags   Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return        The return value indicates what semantics the system should use for the service's current started state.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service started");
        sendNotification("Hi there", "The app has been opened.");
        return START_STICKY;
    }

    /**
     * This method is used to send a notification with a specified title and message.
     *
     * @param title   The title of the notification.
     * @param message The message of the notification.
     */
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

    /**
     * This method is called when the service is destroyed.
     * It sends a notification indicating that the app has been closed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        sendNotification("App Exited", "Your app has been closed.");
    }

    /**
     * This method is called when the service is bound to a component.
     *
     * @param intent The Intent that was used to bind to the service.
     * @return       Return the communication channel to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}