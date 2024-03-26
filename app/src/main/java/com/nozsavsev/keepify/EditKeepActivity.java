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

import com.google.android.material.textfield.TextInputEditText;

/**
 * This activity is used to edit an existing Keep in the application.
 * It provides the user with a form to modify the title and content of the Keep.
 * The user can also change the Keep's favorite status.
 */
public class EditKeepActivity extends AppCompatActivity {

    // The ID of the Keep being edited.
    private String id;
    // The favorite status of the Keep being edited.
    private boolean isFavorite;

    /**
     * This method is called when the activity is first created.
     * It sets up the user interface and initializes the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_keep);

        // Get the Keep to be edited from the intent extras.
        Keep keep = getIntent().getParcelableExtra("KEEP");

        // Set the initial values of the title and content input fields to the Keep's current values.
        ((TextInputEditText) findViewById(R.id.title)).setText(keep.title);
        ((TextInputEditText) findViewById(R.id.content)).setText(keep.content);

        // Set the initial favorite status and ID.
        isFavorite = keep.isFavorite;
        id = keep.id;

        // Set up the UI based on the Keep's favorite status.
        if (isFavorite) {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
        } else {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
        }

        // Set up the click listener for the favorite button.
        findViewById(R.id.favoriteButtonFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite status.
                isFavorite = !isFavorite;

                // Update the UI based on the new favorite status.
                if (isFavorite) {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
                } else {
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
                isFavorite = !isFavorite;

                // Update the UI based on the new favorite status.
                if (isFavorite) {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
                }
            }
        });

        // Set up the click listener for the save button.
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Keep with the updated values.
                Keep keep = new Keep();

                // Set the Keep's ID, title, content, and favorite status.
                keep.id = id;
                keep.title = ((TextInputEditText) findViewById(R.id.title)).getText().toString();
                keep.content = ((TextInputEditText) findViewById(R.id.content)).getText().toString();
                keep.isFavorite = isFavorite;

                // Update the Keep in the KeepManager.
                KeepManager.getInstance().updateKeep(keep);

                // Finish the activity and go back to the previous screen.
                finish();
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
    }
}