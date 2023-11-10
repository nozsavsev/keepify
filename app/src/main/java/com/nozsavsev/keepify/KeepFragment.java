/*        Keepify
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

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KeepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeepFragment extends Fragment {

    private static final String ARG_PARAM1 = "KEEP_PARAM";
    private Keep keep;

    public KeepFragment() {
        // Required empty public constructor
    }

    public static KeepFragment newInstance(Keep keep) {
        KeepFragment fragment = new KeepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, keep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keep = getArguments().getParcelable(ARG_PARAM1);


        }
    }

    public void setEventListenersAndHydrateView() {

        TextView title = rootView.findViewById(R.id.title);
        TextView description = rootView.findViewById(R.id.description);
        ImageButton favoriteButtonNotFav = rootView.findViewById(R.id.favoriteButtonNotFav);
        ImageButton favoriteButtonFav = rootView.findViewById(R.id.favoriteButtonFav);

        if (keep.isFavorite) {
            favoriteButtonNotFav.setVisibility(View.GONE);
            favoriteButtonFav.setVisibility(View.VISIBLE);
        } else {
            favoriteButtonNotFav.setVisibility(View.VISIBLE);
            favoriteButtonFav.setVisibility(View.GONE);
        }

        title.setText(keep.title);
        description.setText(keep.content);

        ((ImageButton) rootView.findViewById(R.id.deleteKeepButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeepManager.getInstance().removeKeep(keep);
            }
        });

        ((ImageButton) rootView.findViewById(R.id.favoriteButtonNotFav)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keep.isFavorite = !keep.isFavorite;
                KeepManager.getInstance().updateKeep(keep);
            }
        });

        ((ImageButton) rootView.findViewById(R.id.favoriteButtonFav)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keep.isFavorite = !keep.isFavorite;
                KeepManager.getInstance().updateKeep(keep);
            }
        });

    }

    public void UpdateView(Keep newKeep) {

        keep = newKeep;
        setEventListenersAndHydrateView();
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_keep, container, false);

        setEventListenersAndHydrateView();

        return rootView;
    }
}