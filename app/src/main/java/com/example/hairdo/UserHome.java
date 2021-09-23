package com.example.hairdo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hairdo.FirebaseDBHandler.FirebaseDBHandler;
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
import java.util.Map;
import java.util.function.Consumer;


public class UserHome extends Fragment {
    private DatabaseReference mDatabase;
    private DatabaseReference childReference;
    FirebaseDBHandler firebaseDBHandler;
    private List<Salon> saloonList = new ArrayList<>();
    private UserHomeSaloonHorizontalViewAdapter mAdapter;
    private UserHomeSaloonVerticalListViewAdapter mAdapter2;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /*firebaseDBHandler = new FirebaseDBHandler();
        firebaseDBHandler.getAllSaloon();*/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        childReference = mDatabase.child("Salon");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new UserHomeSaloonHorizontalViewAdapter(saloonList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerView2);
        mAdapter2 = new UserHomeSaloonVerticalListViewAdapter(saloonList);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getContext());
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(mAdapter2);


        getAllSaloon();


        return view;
    }

    public void getAllSaloon() {
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
                mAdapter2.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        };
        childReference.addValueEventListener(postListener);
    }


}