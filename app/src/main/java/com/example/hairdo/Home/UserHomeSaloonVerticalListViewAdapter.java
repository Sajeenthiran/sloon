package com.example.hairdo.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairdo.R;
import com.example.hairdo.model.Salon;

import java.util.List;




public class UserHomeSaloonVerticalListViewAdapter extends RecyclerView.Adapter<UserHomeSaloonVerticalListViewAdapter.MyViewHolder>{
    private List<Salon> saloonList;

    public UserHomeSaloonVerticalListViewAdapter(List<Salon> saloonList) {
        this.saloonList = saloonList;
    }

    @NonNull
    @Override
    public UserHomeSaloonVerticalListViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saloon_vertical_list_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeSaloonVerticalListViewAdapter.MyViewHolder holder, int position) {
        Salon movie = saloonList.get(position);
        holder.title.setText(movie.name);
        holder.address.setText(movie.address);

    }

    @Override
    public int getItemCount() {
        return saloonList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, address;
        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.salonname);
            address = view.findViewById(R.id.address);

        }
    }
}

