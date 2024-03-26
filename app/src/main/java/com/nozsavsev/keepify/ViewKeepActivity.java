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
import android.widget.TextView;

/**
 * Activity for viewing a single Keep.
 * This activity displays the details of a Keep and provides options to mark it as favorite, unfavorite it, or delete it.
 */
public class ViewKeepActivity extends AppCompatActivity {

    // Flag to track if the current Keep is marked as favorite
    boolean isFavorite = false;

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_keep);

        // Get the Keep object passed from the previous activity
        Keep keep = getIntent().getParcelableExtra("KEEP");

        // Set the title and content of the Keep
        ((TextView) findViewById(R.id.title)).setText(keep.title);
        ((TextView) findViewById(R.id.content)).setText(keep.content);

        // Check if the Keep is marked as favorite
        isFavorite = keep.isFavorite;

        // Update the visibility of the favorite buttons based on the favorite status
        if (isFavorite) {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
        } else {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
        }

        // Set click listener for the favorite button when the Keep is already marked as favorite
        findViewById(R.id.favoriteButtonFav).setOnClickListener(v -> {

            // Toggle the favorite status
            isFavorite = !isFavorite;

            // Update the visibility of the favorite buttons
            if (isFavorite) {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
            } else {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
            }

            // Update the favorite status of the Keep and save it
            keep.isFavorite = isFavorite;
            KeepManager.getInstance().updateKeep(keep);
        });

        // Set click listener for the favorite button when the Keep is not marked as favorite
        findViewById(R.id.favoriteButtonNotFav).setOnClickListener(v -> {

            // Toggle the favorite status
            isFavorite = !isFavorite;

            // Update the visibility of the favorite buttons
            if (isFavorite) {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
            } else {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
            }

            // Update the favorite status of the Keep and save it
            keep.isFavorite = isFavorite;
            KeepManager.getInstance().updateKeep(keep);
        });

        // Set click listener for the delete button
        findViewById(R.id.delete).setOnClickListener(v -> {
            // Remove the Keep and finish the activity
            KeepManager.getInstance().removeKeep(keep);
            finish();
        });

        // Set click listener for the back button
        findViewById(R.id.back).setOnClickListener(v -> {
            // Finish the activity
            finish();
        });

    }
}