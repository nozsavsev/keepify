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

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link DialogFragment} subclass.
 * This fragment is used to display a greeting modal to the user.
 * Use the {@link GreetingsModalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GreetingsModalFragment extends DialogFragment {

    // Empty public constructor required for the fragment
    public GreetingsModalFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GreetingsModalFragment.
     */
    public static GreetingsModalFragment newInstance(String param1, String param2) {
        GreetingsModalFragment fragment = new GreetingsModalFragment();
        return fragment;
    }

    // Called when the fragment is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Root view of the fragment
    private View rootView;

    /**
     * Called to create the view hierarchy associated with the fragment
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_greetings_modal, container, false);

        return rootView;
    }
}