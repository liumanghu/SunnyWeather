package com.example.sunnyweather;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.example.sunnyweather.ui.place.PlaceFragment;
import com.example.sunnyweather.ui.place.PlaceViewModel;
import com.example.sunnyweather.ui.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {
    private FragmentManager famg;
    private FragmentTransaction transaction;
    private PlaceViewModel placeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        famg = getSupportFragmentManager();
        transaction = famg.beginTransaction();
        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        if (placeViewModel.isPlaceSaved()){
            Place place = placeViewModel.getSavedPlace();
            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("location_lng",place.getLocation().getLng());
            intent.putExtra("location_lat",place.getLocation().getLat());
            intent.putExtra("place_name",place.getName());
            startActivity(intent);





            finish();
        }else {
            transaction.replace(R.id.mainFragment,new PlaceFragment());
            transaction.commit();
        }
    }
}