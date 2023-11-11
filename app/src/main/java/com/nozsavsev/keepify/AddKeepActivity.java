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

public class AddKeepActivity extends AppCompatActivity {
    boolean isDefaultFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keep);

        isDefaultFavorite = getIntent().getBooleanExtra("isDefaultFavorite", false);


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

        findViewById(R.id.favoriteButtonFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDefaultFavorite = !isDefaultFavorite;

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

        findViewById(R.id.favoriteButtonNotFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDefaultFavorite = !isDefaultFavorite;

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

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Keep keep = new Keep();

                keep.title = ((TextInputEditText) findViewById(R.id.title)).getText().toString();
                keep.content = ((TextInputEditText) findViewById(R.id.content)).getText().toString();
                keep.isFavorite = isDefaultFavorite;

                KeepManager.getInstance().addKeep(keep);

                finish();

            }
        });

        findViewById(R.id.add_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Keep keep = new Keep();

                keep.title = ((TextInputEditText) findViewById(R.id.title)).getText().toString();
                keep.content = ((TextInputEditText) findViewById(R.id.content)).getText().toString();
                keep.isFavorite = isDefaultFavorite;

                KeepManager.getInstance().addKeep(keep);

                finish();
            }
        });

    }
}