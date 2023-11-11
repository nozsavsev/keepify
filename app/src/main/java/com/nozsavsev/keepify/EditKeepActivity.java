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

public class EditKeepActivity extends AppCompatActivity {

    private String id;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_keep);

        Keep keep = getIntent().getParcelableExtra("KEEP");

        ((TextInputEditText) findViewById(R.id.title)).setText(keep.title);
        ((TextInputEditText) findViewById(R.id.content)).setText(keep.content);

        isFavorite = keep.isFavorite;
        id = keep.id;


        if (isFavorite) {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
        } else {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
        }


        findViewById(R.id.favoriteButtonFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;

                if (isFavorite) {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.favoriteButtonNotFav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;

                if (isFavorite) {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                    findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
                }
            }
        });




        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Keep keep = new Keep();

                keep.id = id;

                keep.title = ((TextInputEditText) findViewById(R.id.title)).getText().toString();
                keep.content = ((TextInputEditText) findViewById(R.id.content)).getText().toString();
                keep.isFavorite = isFavorite;

                KeepManager.getInstance().updateKeep(keep);

                finish();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}