package com.example.hairdo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hairdo.model.Offer;
import com.example.hairdo.model.Salon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class offer extends AppCompatActivity {
    EditText textView8;
    EditText textView10;
    Button pickDate;
    Button btn;
    private DatabaseReference mDatabase;
    private DatabaseReference childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        childReference = mDatabase.child("offers");

        textView8 = findViewById(R.id.textView8);
        textView10 = findViewById(R.id.textView10);
        pickDate = findViewById(R.id.button2);
        btn = findViewById(R.id.button3);

        getAllOffers();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("OFFERS", Context.MODE_PRIVATE);
                int offerId = sharedpreferences.getInt("offer_id",1);

                String title = textView8.getText().toString();
                String desc = textView8.getText().toString();
                String validUntil = "2021-09-15 02:15:25";

                writeNewOffer(String.valueOf(offerId), title,desc,validUntil);

                getAllOffers();
                getOfferById(offerId);
                updateNewOffers(String.valueOf(offerId),"Hair Cut","Cutting hair","2021-10-15 02:15:25");

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("offer_id", offerId++);
                editor.commit();

            }
        });


    }

    public void writeNewOffer(String offerId, String title, String description, String validUntil) {
        Offer offer = new Offer(offerId, title, description,validUntil);

        childReference.child(offerId).setValue(offer);
    }

    public void getAllOffers() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot!=null){
                    if(snapshot.getValue()!=null){
                        Log.d("DATA",snapshot.getValue().toString());
                    }

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        };
        childReference.addValueEventListener(postListener);
    }

    public void getOfferById(int offerId){
        childReference.child(String.valueOf(offerId)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void updateNewOffers(String offerId, String title, String desc, String validUntil) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = childReference.push().getKey();
        Offer post = new Offer(offerId, title, desc, validUntil);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/1/" + key, postValues);

        childReference.updateChildren(childUpdates);
    }


}