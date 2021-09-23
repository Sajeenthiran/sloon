package com.example.hairdo.FirebaseDBHandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDBHandler {
    private DatabaseReference mDatabase;

    public FirebaseDBHandler(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getAllSaloon() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DATA",snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        };
        mDatabase.addValueEventListener(postListener);
    }


}
