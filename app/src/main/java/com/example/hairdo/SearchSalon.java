package com.example.hairdo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hairdo.Home.UserHomeSaloonHorizontalViewAdapter;
import com.example.hairdo.Home.UserHomeSaloonVerticalListViewAdapter;
import com.example.hairdo.model.Salon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchSalon extends AppCompatActivity {
    private EditText searchSaloon;
    private ImageButton searchBtn;
    private UserHomeSaloonVerticalListViewAdapter mAdapter;
    private List<Salon> saloonList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference childReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_salon);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        childReference = mDatabase.child("Salon");

        searchSaloon = findViewById(R.id.searchSaloon);
        searchBtn = findViewById(R.id.searchBtn);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new UserHomeSaloonVerticalListViewAdapter(saloonList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Log.d("DATA",snapshot.getValue().toString());

                        if(snapshot.exists()){
                            saloonList.clear();
                            HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                            for (String key : dataMap.keySet()){

                                Object data = dataMap.get(key);

                                try{
                                    HashMap<String, Object> userData = (HashMap<String, Object>) data;

                                    Log.d("Saloon",userData.get("name").toString());
                                    Log.d("Saloon",userData.get("address").toString());
                                    Log.d("Saloon",userData.get("contact").toString());

                                    Salon salon = new Salon(userData.get("name").toString(), userData.get("email").toString(),
                                            userData.get("address").toString(), userData.get("contact").toString(), userData.get("password").toString());

                                    saloonList.add(salon);

                                    Log.d("salon obj",salon.name);

                                }catch (ClassCastException cce){

                                }

                            }

                            mAdapter.notifyDataSetChanged();
                        }else{
                            Log.d("DATA","No Data");
                            Toast.makeText(getApplicationContext(),"No Data Yet",Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                };

                childReference.orderByChild("name")
                        .startAt(searchSaloon.getText().toString())
                        .endAt(searchSaloon.getText().toString()+"\uf8ff").addValueEventListener(postListener);
            }
        });

    }
}