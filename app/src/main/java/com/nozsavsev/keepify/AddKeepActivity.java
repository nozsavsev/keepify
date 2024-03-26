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

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * This activity is used to add a new Keep to the application.
 * It provides the user with a form to input the title and content of the Keep.
 * The user can also mark the Keep as a favorite.
 */
public class AddKeepActivity extends AppCompatActivity {
    // This variable is used to track whether the Keep should be marked as a favorite by default.
    boolean isDefaultFavorite = false;

    /**
     * This method is called when the activity is first created.
     * It sets up the user interface and initializes the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keep);

        // Get the default favorite status from the intent extras.
        isDefaultFavorite = getIntent().getBooleanExtra("isDefaultFavorite", false);

        // Set up the UI based on the default favorite status.
        if (isDefaultFavorite) {
            findViewById(R.id.add).setVisibility(View.GONE);
            findViewById(R.id.add_favorite).setVisibility(View.VISIBLE);

            findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
        } else {
            findViewById(R.id.add).setVisibility(View.VISIBLE);
            findViewById(R.id.add_favorite).setVisibility(View.GONE);

            findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
        }

        // Set up the click listener for the favorite button.
        findViewById(R.id.favoriteButtonFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite status.
                isDefaultFavorite = !isDefaultFavorite;

                // Update the UI based on the new favorite status.
                if (isDefaultFavorite) {
                    findViewById(R.id.add).setVisibility(View.GONE);
                    findViewById(R.id.add_favorite).setVisibility(View.VISIBLE);

                    findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.add).setVisibility(View.VISIBLE);
                    findViewById(R.id.add_favorite).setVisibility(View.GONE);

                    findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
                }
            }
        });

        // Set up the click listener for the not favorite button.
        findViewById(R.id.favoriteButtonNotFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite status.
                isDefaultFavorite = !isDefaultFavorite;

                // Update the UI based on the new favorite status.
                if (isDefaultFavorite) {
                    findViewById(R.id.add).setVisibility(View.GONE);
                    findViewById(R.id.add_favorite).setVisibility(View.VISIBLE);

                    findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.add).setVisibility(View.VISIBLE);
                    findViewById(R.id.add_favorite).setVisibility(View.GONE);

                    findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
                }
            }
        });

        // Set up the click listener for the back button.
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity and go back to the previous screen.
                finish();
            }
        });

        // Set up the click listener for the add button.
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Keep.
                Keep keep = new Keep();

                // Get the title and content from the input fields.
                keep.title = ((TextInputEditText) findViewById(R.id.title)).getText().toString();
                keep.content = ((TextInputEditText) findViewById(R.id.content)).getText().toString();
                // Set the favorite status.
                keep.isFavorite = isDefaultFavorite;

                // Add the Keep to the KeepManager.
                KeepManager.getInstance().addKeep(keep);

                // Finish the activity and go back to the previous screen.
                finish();
            }
        });

        // Set up the click listener for the add favorite button.
        findViewById(R.id.add_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Keep.
                Keep keep = new Keep();

                // Get the title and content from the input fields.
                keep.title = ((TextInputEditText) findViewById(R.id.title)).getText().toString();
                keep.content = ((TextInputEditText) findViewById(R.id.content)).getText().toString();
                // Set the favorite status.
                keep.isFavorite = isDefaultFavorite;

                // Add the Keep to the KeepManager.
                KeepManager.getInstance().addKeep(keep);

                // Finish the activity and go back to the previous screen.
                finish();
            }
        });
    }
}