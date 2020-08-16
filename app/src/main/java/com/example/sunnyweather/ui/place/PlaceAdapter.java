package com.example.sunnyweather.ui.place;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.logic.model.Place;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private ArrayList<Place> places;

    //构造方法
    public PlaceAdapter(ArrayList<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.PlaceName.setText(places.get(position).getName());
        holder.PlaceAddress.setText(places.get(position).getFormatted_address());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }


    //创建RecyclerView的ViewHolder内部类
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView PlaceName;
        private TextView PlaceAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PlaceName = itemView.findViewById(R.id.placeName);
            PlaceAddress = itemView.findViewById(R.id.placeAddress);
        }

    }


}
