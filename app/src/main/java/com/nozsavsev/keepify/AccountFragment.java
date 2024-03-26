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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass representing the account section of the app.
 * This fragment handles user authentication and account management.
 * Use the {@link AccountFragment#newInstance} factory method to create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    /**
     * Required empty public constructor
     */
    public AccountFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment AccountFragment.
     */
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     * It can be used to do final initialization once these pieces are in place, such as retrieving views or restoring state.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View rootView;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null.
     * This will be called between onCreate(Bundle) and onActivityCreated(Bundle).
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            /**
             * Called when the authentication state changes.
             *
             * @param firebaseAuth The passed firebaseAuth parameter corresponds to the FirebaseAuth instance.
             */
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                if (auth.getCurrentUser() != null) {

                    // Set the greeting text
                    ((TextView) rootView.findViewById(R.id.greetingsText)).setText("Hello, " + auth.getCurrentUser().getEmail().substring(0, auth.getCurrentUser().getEmail().indexOf('@')) + "!");

                    // Set the logout button click listener
                    ((Button) rootView.findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
                        /**
                         * Called when the logout button is clicked.
                         *
                         * @param v The view that was clicked.
                         */
                        @Override
                        public void onClick(View v) {
                            auth.signOut();

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });

                    // Set the info button click listener
                    rootView.findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
                        /**
                         * Called when the info button is clicked.
                         *
                         * @param v The view that was clicked.
                         */
                        @Override
                        public void onClick(View v) {
                            GreetingsModalFragment dialogFragment = new GreetingsModalFragment();
                            dialogFragment.show(getActivity().getSupportFragmentManager(), "GreetingsModalFragment");
                        }
                    });

                    // Set the delete account button click listener
                    ((Button) rootView.findViewById(R.id.deleteAccount)).setOnClickListener(new View.OnClickListener() {
                        /**
                         * Called when the delete account button is clicked.
                         *
                         * @param v The view that was clicked.
                         */
                        @Override
                        public void onClick(View v) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://keepify-b3b94-default-rtdb.europe-west1.firebasedatabase.app");

                            // Delete the user's data from the database
                            database.getReference(auth.getCurrentUser().getUid()).removeValue();

                            // Delete the user's account
                            auth.getCurrentUser().delete();

                            Intent intent = new Intent(getActivity(), RegisterActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });

                }
            }
        });

        return rootView;
    }
}