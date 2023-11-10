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

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeepManager {

    static KeepManager instance;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://keepify-b3b94-default-rtdb.europe-west1.firebasedatabase.app");


    private KeepManager() {

    }

    public static KeepManager getInstance() {
        if (instance == null) {
            instance = new KeepManager();
        }
        return instance;
    }

    public void addKeep(Keep keep) {
        DatabaseReference mRef = database.getReference(mAuth.getUid() + "/keeps/" + keep.id);
        keep.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
        mRef.setValue(keep);
    }

    public void removeKeep(Keep keep) {
        removeKeep(keep.id);
    }

    public void removeKeep(String keepId) {
        DatabaseReference mRef = database.getReference(mAuth.getUid() + "/keeps/" + keepId);
        mRef.removeValue();
    }

    public void updateKeep(Keep keep) {
        DatabaseReference mRef = database.getReference(mAuth.getUid() + "/keeps/" + keep.id);
        mRef.setValue(keep);
    }

    public void deleteKeepListner(ValueEventListener listner) {
        DatabaseReference mRef = database.getReference(mAuth.getUid() + "/keeps");
        mRef.removeEventListener(listner);
    }

    public ValueEventListener addKeepsListner(KeepListListener listner) {
        DatabaseReference mRef = database.getReference(mAuth.getUid() + "/keeps");
       return mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Keep>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Keep>>() {
                };
                if (dataSnapshot.getValue(genericTypeIndicator) != null){
                    List<Keep> keeps = new ArrayList<>(dataSnapshot.getValue(genericTypeIndicator).values());
                    keeps.sort((o1, o2) -> o2.addedAtTimestamp - o1.addedAtTimestamp);
                    listner.onListUpdated(keeps);
                }
                else
                    listner.onListUpdated(new ArrayList<>());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("KeepManager", "Failed to read value.", error.toException());
            }
        });


    }

}
