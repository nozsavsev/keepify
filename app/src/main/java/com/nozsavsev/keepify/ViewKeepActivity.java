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

public class ViewKeepActivity extends AppCompatActivity {

    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_keep);

        Keep keep = getIntent().getParcelableExtra("KEEP");

        ((TextView) findViewById(R.id.title)).setText(keep.title);
        ((TextView) findViewById(R.id.content)).setText(keep.content);

        isFavorite = keep.isFavorite;

        if (isFavorite) {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
        } else {
            findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
            findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.favoriteButtonFav).setOnClickListener(v -> {

            isFavorite = !isFavorite;

            if (isFavorite) {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
            } else {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
            }

            keep.isFavorite = isFavorite;
            KeepManager.getInstance().updateKeep(keep);
        });

        findViewById(R.id.favoriteButtonNotFav).setOnClickListener(v -> {

            isFavorite = !isFavorite;

            if (isFavorite) {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.VISIBLE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.GONE);
            } else {
                findViewById(R.id.favoriteButtonFav).setVisibility(View.GONE);
                findViewById(R.id.favoriteButtonNotFav).setVisibility(View.VISIBLE);
            }

            keep.isFavorite = isFavorite;
            KeepManager.getInstance().updateKeep(keep);
        });


        findViewById(R.id.delete).setOnClickListener(v -> {
            KeepManager.getInstance().removeKeep(keep);
            finish();
        });

        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });

    }
}