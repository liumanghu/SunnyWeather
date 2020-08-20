package com.example.sunnyweather.logic.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sunnyweather.SunnyWeatherApplication;
import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.google.gson.Gson;

public class PlaceDao {
    private static SharedPreferences sharedPreferences;
    private static PlaceDao placeDao;

    private PlaceDao(){
        sharedPreferences = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE);
    }

    public static PlaceDao getInstance(){
        if (placeDao == null){
            placeDao = new PlaceDao();
        }
        return placeDao;
    }

    public void savePlace(Place place){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("place", new Gson().toJson(place));
        editor.apply();
    }

    public Place getSavedPlace(){
        String placeJson = sharedPreferences.getString("place","");
        return new Gson().fromJson(placeJson,Place.class);
    }

    public boolean isPlaceSaved(){
        return sharedPreferences.contains("place");
    }
}
