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

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private NetworkChangeReceiver networkChangeReceiver;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Intent serviceIntent = new Intent(this, KeepifyService.class);
        startService(serviceIntent);

        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        MusicPlayerThread musicPlayerThread = new MusicPlayerThread(this, R.raw.music);
        musicPlayerThread.start();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.flHome, new HomeFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFavorites, new FavoritesFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.flAccount, new AccountFragment()).commit();


        bottomNavigationView.setOnItemSelectedListener(item -> {

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