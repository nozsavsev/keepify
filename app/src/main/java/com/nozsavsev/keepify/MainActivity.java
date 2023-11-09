package com.nozsavsev.keepify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new HomeFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (R.id.home == item.getItemId())
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new HomeFragment()).commit();

            if (R.id.favorites == item.getItemId())
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new FavoritesFragment()).commit();

            if (R.id.account == item.getItemId())
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new AccountFragment()).commit();

            return true;
        });
    }
}