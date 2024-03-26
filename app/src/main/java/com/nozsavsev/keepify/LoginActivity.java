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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

/**
 * LoginActivity is an activity that handles user login.
 * It provides fields for email and password input, and handles authentication using Firebase.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the register button to start the RegisterActivity
        findViewById(R.id.register).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        // Set up a text watcher for the email field to clear the error text when the email is changed
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

        // Set up a text watcher for the password field to clear the error text when the password is changed
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

        // Set up the login button to authenticate the user when clicked
        findViewById(R.id.login).setOnClickListener(v -> {

            // Get the email and password from the input fields
            String email = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.email)).getText().toString();
            String password = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.password)).getText().toString();

            // Check if the email or password is empty and display an error if they are
            if (email.isEmpty() || password.isEmpty()) {
                ((TextView) findViewById(R.id.errorText)).setText("Please enter a valid email and password.");
                return;
            }

            // Get an instance of FirebaseAuth
            FirebaseAuth auth = FirebaseAuth.getInstance();

            // Attempt to sign in with the provided email and password
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // If the sign in was successful, start the MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If the sign in was not successful, display an error message based on the exception
                    Exception exception = task.getException();

                    if (exception instanceof FirebaseAuthInvalidUserException) {
                        ((TextView) findViewById(R.id.errorText)).setText("Incorrect email or password.");
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        ((TextView) findViewById(R.id.errorText)).setText("Invalid email or password.");
                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                        ((TextView) findViewById(R.id.errorText)).setText("Incorrect email or password.");
                    } else {
                        ((TextView) findViewById(R.id.errorText)).setText("Incorrect email or password.");
                    }
                }
            });

        });

    }
}