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

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * KeepFragment is a Fragment that displays a single Keep object.
 * It manages the display and interaction of a Keep object.
 */
public class KeepFragment extends Fragment {

    private static final String ARG_PARAM1 = "KEEP_PARAM";
    private Keep keep;

    /**
     * Required empty public constructor
     */
    public KeepFragment() {}

    /**
     * Factory method to create a new instance of this fragment.
     * @param keep The Keep object to be displayed.
     * @return A new instance of KeepFragment.
     */
    public static KeepFragment newInstance(Keep keep) {
        KeepFragment fragment = new KeepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, keep);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is first created.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keep = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    /**
     * Sets the event listeners for the view elements and updates the view with the Keep data.
     */
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
        description.setText(keep.content.substring(0, Math.min(keep.content.length(), 150)) + ((keep.content.length() > 200 ? "..." : "") ));

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


        rootView.findViewById(R.id.keepLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewKeepActivity.class);
                intent.putExtra("KEEP", keep);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.keepLayout).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getContext(), EditKeepActivity.class);
                intent.putExtra("KEEP", keep);
                startActivity(intent);
                return false;
            }
        });
    }

    /**
     * Updates the view with the new Keep data.
     * @param newKeep The updated Keep object.
     */
    public void UpdateView(Keep newKeep) {
        keep = newKeep;
        setEventListenersAndHydrateView();
    }

    View rootView;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_keep, container, false);
        setEventListenersAndHydrateView();
        return rootView;
    }
}