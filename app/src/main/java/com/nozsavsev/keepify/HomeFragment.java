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
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //destructor
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (keepUpdatedEventListner != null)
            KeepManager.getInstance().deleteKeepListner(keepUpdatedEventListner);
    }


    private View rootView;
    private FragmentManager fragmentManager;
    private ValueEventListener keepUpdatedEventListner;

    private List<Keep> keeps;
    private boolean viewUpdatePending = false;
    private boolean blockUpdate = false;

    @Override
    public void onResume() {
        super.onResume();
        blockUpdate = false;
            updateKeepsView();
    }

    @Override
    public void onPause() {
        super.onPause();
        blockUpdate = true;
    }

    private void updateKeepsView() {
        if (viewUpdatePending == false || blockUpdate == true)
            return;

        viewUpdatePending = false;

        ScrollView keepsScrollView = rootView.findViewById(R.id.keepsScrollView);
        LinearLayout linearLayout = rootView.findViewById(R.id.keepsScrollViewContainer);
        LinearLayout placeholder = rootView.findViewById(R.id.placeholder);
        if (keeps.size() > 0) {

            if (keepsScrollView.getVisibility() != View.VISIBLE)
                keepsScrollView.setVisibility(View.VISIBLE);

            if (placeholder.getVisibility() != View.GONE)
                placeholder.setVisibility(View.GONE);


            while (keeps.size() < linearLayout.getChildCount()) {
                linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
            }

            int currentChildIndex = 0;
            int originalChildCount = linearLayout.getChildCount();

            for (Keep keep : keeps) {

                if (currentChildIndex < originalChildCount) {
                    KeepFragment keepFragment = (KeepFragment) fragmentManager.findFragmentById(linearLayout.getChildAt(currentChildIndex).getId());
                    keepFragment.UpdateView(keep);
                    currentChildIndex++;
                    continue;
                }

                KeepFragment keepFragment = KeepFragment.newInstance(keep);
                FragmentContainerView fragmentContainerView = new FragmentContainerView(rootView.getContext());

                fragmentContainerView.setId(View.generateViewId());
                fragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragmentContainerView.getId(), keepFragment);
                fragmentTransaction.commit();

                linearLayout.addView(fragmentContainerView);
            }

            linearLayout.invalidate();

        } else {
            linearLayout.removeAllViews();
            linearLayout.invalidate();

            if (keepsScrollView.getVisibility() != View.GONE)
                keepsScrollView.setVisibility(View.GONE);

            if (placeholder.getVisibility() != View.VISIBLE)
                placeholder.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        fragmentManager = ((MainActivity) rootView.getContext()).getSupportFragmentManager();
        keepUpdatedEventListner = KeepManager.getInstance().addKeepsListner(new KeepListListener() {
            @Override
            public void onListUpdated(List<Keep> _keeps) {
                keeps = _keeps;

                viewUpdatePending = true;
                updateKeepsView();
            }
        });
        rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(rootView.getContext(), AddKeepActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}