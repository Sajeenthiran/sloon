
package com.example.hairdo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hairdo.model.Appointment;
import com.example.hairdo.model.Salon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentSummary extends AppCompatActivity {
    private List<Appointment> saloonList = new ArrayList<Appointment>();
    AppointmentSummaryAdapter Adapter2;
    private DatabaseReference mDatabase;
    private DatabaseReference childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_summary);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        childReference = mDatabase.child("Appointment");


        RecyclerView recyclerView = findViewById(R.id.appointment_summary_recycleview2);
        Adapter2 = new AppointmentSummaryAdapter(saloonList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adapter2);

        getAllSaloonVisit();
    }

    public void getAllSaloonVisit() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DATA",snapshot.getValue().toString());
//                Map<String, Object> childUpdates = new HashMap<>();

                Log.d("saloon",snapshot.getValue().toString());

                HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                for (String key : dataMap.keySet()){

                    Object data = dataMap.get(key);

                    try{
                        HashMap<String, Object> userData = (HashMap<String, Object>) data;

                        Appointment appointment = new Appointment(userData.get("sname").toString(), userData.get("date").toString(), userData.get("time").toString());

                        saloonList.add(appointment);

                    }catch (ClassCastException cce){

                    }

                }

                Adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        };
        childReference.addValueEventListener(postListener);
    }
}