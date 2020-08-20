package com.example.sunnyweather.ui.place;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.MainActivity;
import com.example.sunnyweather.R;
import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.example.sunnyweather.ui.weather.WeatherActivity;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private ArrayList<Place> places;
    private PlaceFragment fragment;

    //构造方法
    public PlaceAdapter(PlaceFragment fragment, ArrayList<Place> places) {
        this.fragment = fragment;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Place place = places.get(position);
                Activity activity = fragment.getActivity();
                //判断当前的Fragment处于那个Activity中，如果在weatherActivity中则不需要重新打开一个Activity，只用更新页面内容就可以了
                if (activity.getClass().getName().equals(WeatherActivity.class.getName())){
                    WeatherActivity activity1 = (WeatherActivity) activity;
                    activity1.binding.drawerLayout.closeDrawers();
                    activity1.weatherViewModel.locationLng = place.getLocation().getLng();
                    activity1.weatherViewModel.locationLat = place.getLocation().getLat();
                    activity1.weatherViewModel.placeName = place.getName();
                    fragment.mViewModel.savePlace(place);
                    activity1.refreshWeather();
                }else {
                    Intent intent = new Intent(fragment.getContext(), WeatherActivity.class);
                    intent.putExtra("location_lng",place.getLocation().getLng());
                    intent.putExtra("location_lat",place.getLocation().getLat());
                    intent.putExtra("place_name",place.getName());
                    fragment.mViewModel.savePlace(place);
                    fragment.startActivity(intent);
                    activity.finish();
                }
            }
        });
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
            PlaceName = itemView.findViewById(R.id.placeNameText);
            PlaceAddress = itemView.findViewById(R.id.placeAddress);
        }

    }


}
