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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.login).setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

        findViewById(R.id.register).setOnClickListener(v -> {

            String email = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.email)).getText().toString();
            String password = ((androidx.appcompat.widget.AppCompatEditText) findViewById(R.id.password)).getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                ((TextView) findViewById(R.id.errorText)).setText("Please enter a valid email and password.");
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Exception exception = task.getException();

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