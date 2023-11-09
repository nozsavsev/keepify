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
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.register).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

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

        findViewById(R.id.login).setOnClickListener(v -> {

            String email = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.email)).getText().toString();
            String password = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.password)).getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                ((TextView) findViewById(R.id.errorText)).setText("Please enter a valid email and password.");
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

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