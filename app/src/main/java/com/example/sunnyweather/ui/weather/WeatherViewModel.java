package com.example.sunnyweather.ui.weather;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlacePackage.Location;
import com.example.sunnyweather.logic.model.Weather;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    public String locationLng = "";
    public String locationLat = "";
    public String placeName = "";

    public LiveData<Weather> weatherLiveData = Transformations.switchMap(locationLiveData, new Function<Location, LiveData<Weather>>() {
        @Override
        public LiveData<Weather> apply(Location input) {
            try {
                return Repository.getInstance().refreshWeather(input.getLng(),input.getLat());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    });

    public void refreshWeather(String lng,String lat){
        locationLiveData.setValue(new Location(lng,lat));
    }
}
