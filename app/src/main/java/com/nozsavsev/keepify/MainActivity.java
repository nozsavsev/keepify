/*
Keepify
Copyright (C) 2023  Ilia Nozdrachev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.nozsavsev.keepify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Main activity for the Keepify application.
 * This activity is responsible for managing the user authentication,
 * starting the Keepify service, and managing the bottom navigation view.
 */
public class MainActivity extends AppCompatActivity {

    // Bottom navigation view
    BottomNavigationView bottomNavigationView;

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Initialize Firebase Auth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // Create notification channel
        createNotificationChannel();

        // Start Keepify service
        Intent serviceIntent = new Intent(this, KeepifyService.class);
        startService(serviceIntent);

        // If the user is not logged in, start the RegisterActivity
        if (user == null) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Network change receiver
    private NetworkChangeReceiver networkChangeReceiver;

    /**
     * Creates a notification channel for the application.
     */
    private void createNotificationChannel() {

        {
            CharSequence name = "startedNotifyChannel";
            String description = "startedNotifyChannel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("startedNotifyChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Called before the activity is destroyed.
     * This is the final call that the activity will receive.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize network change receiver
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        // Start music player thread
        MusicPlayerThread musicPlayerThread = new MusicPlayerThread(this, R.raw.music);
        musicPlayerThread.start();

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Replace fragments in the activity layout
        getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new HomeFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFavorites, new FavoritesFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.flAccount, new AccountFragment()).commit();

        // Set item selected listener for the bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(item -> {

            // Show the corresponding fragment based on the selected item
            if (R.id.home == item.getItemId())
            {
                findViewById(R.id.flHome).setVisibility(View.VISIBLE);
                findViewById(R.id.flFavorites).setVisibility(View.GONE);
                findViewById(R.id.flAccount).setVisibility(View.GONE);
            }

            if (R.id.favorites == item.getItemId())
            {
                findViewById(R.id.flHome).setVisibility(View.GONE);
                findViewById(R.id.flFavorites).setVisibility(View.VISIBLE);
                findViewById(R.id.flAccount).setVisibility(View.GONE);
            }
            if (R.id.account == item.getItemId())
            {
                findViewById(R.id.flHome).setVisibility(View.GONE);
                findViewById(R.id.flFavorites).setVisibility(View.GONE);
                findViewById(R.id.flAccount).setVisibility(View.VISIBLE);
            }
            return true;
        });
    }
}