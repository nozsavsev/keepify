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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/**
 * Activity for registering a new user.
 * This activity provides a form for user registration and handles the registration process.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set click listener for the login button
        findViewById(R.id.login).setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Set click listener for the terms button
        findViewById(R.id.terms).setOnClickListener(v -> {
            Uri uri = Uri.parse("https://nozsavsev.github.io/keepify");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // Set text change listener for the email field
        ((TextInputEditText) findViewById(R.id.email)).addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((TextView) findViewById(R.id.errorText)).setText("");
            }
        });

        // Set text change listener for the password field
        ((TextInputEditText) findViewById(R.id.password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((TextView) findViewById(R.id.errorText)).setText("");
            }
        });

        // Set click listener for the register button
        findViewById(R.id.register).setOnClickListener(v -> {

            String email = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.email)).getText().toString();
            String password = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.password)).getText().toString();

            // Check if the email or password is empty
            if (email.isEmpty() || password.isEmpty()) {
                ((TextView) findViewById(R.id.errorText)).setText("Please enter a valid email and password.");
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();

            // Attempt to create a new user
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Exception exception = task.getException();

                    // Handle different types of exceptions
                    if (exception instanceof FirebaseAuthInvalidUserException) {
                        ((TextView) findViewById(R.id.errorText)).setText("Incorrect email or password.");
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        ((TextView) findViewById(R.id.errorText)).setText("Invalid email or password.");
                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                        ((TextView) findViewById(R.id.errorText)).setText("User with this Email already exists.");
                    } else {
                        ((TextView) findViewById(R.id.errorText)).setText("Incorrect email or password. other");
                    }
                }
            });

        });

    }
}