package com.example.sunnyweather.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather.logic.dao.PlaceDao;
import com.example.sunnyweather.logic.model.DailPackage.Daily;
import com.example.sunnyweather.logic.model.DailPackage.DailyResponse;
import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.example.sunnyweather.logic.model.PlacePackage.PlaceResponse;
import com.example.sunnyweather.logic.model.RealtimePackage.Realtime;
import com.example.sunnyweather.logic.model.RealtimePackage.RealtimeResponse;
import com.example.sunnyweather.logic.model.Weather;
import com.example.sunnyweather.logic.network.PlaceService;
import com.example.sunnyweather.logic.network.ServiceCreator;
import com.example.sunnyweather.logic.network.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository repository;

    public static Repository getInstance(){
        if (repository == null){
            repository = new Repository();
        }
        return repository;
    }

    //进行地区搜索的仓库接口
    public LiveData<PlaceResponse> searchPlaces(String query){
        final MutableLiveData<PlaceResponse> liveData = new MutableLiveData<>();

        PlaceService placeService = ServiceCreator.Create(PlaceService.class);
        placeService.searchPlaces(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                PlaceResponse placeResponse = response.body();
                liveData.setValue(placeResponse);
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    Daily daily = null;
    Realtime realtime = null;
    //更新天气
    public LiveData<Weather> refreshWeather(String lng, String lat) throws InterruptedException {
        MutableLiveData<Weather> liveData = new MutableLiveData<>();
        liveData.setValue(new Weather(null,null));

        WeatherService weatherService = ServiceCreator.Create(WeatherService.class);


        weatherService.getDailyWeather(lng,lat).enqueue(new Callback<DailyResponse>() {

            @Override
            public void onResponse(Call<DailyResponse> call, Response<DailyResponse> response) {
                DailyResponse dailyResponse = response.body();
                if (dailyResponse != null&&dailyResponse.getStatus().equals("ok")){
                    daily = response.body().getResult().getDaily();
                    liveData.setValue(new Weather(realtime,daily));
                }
            }

            @Override
            public void onFailure(Call<DailyResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });

        weatherService.getRealtimeWeather(lng,lat).enqueue(new Callback<RealtimeResponse>() {
            @Override
            public void onResponse(Call<RealtimeResponse> call, Response<RealtimeResponse> response) {
                RealtimeResponse realtimeResponse = response.body();
                if (realtimeResponse != null&&realtimeResponse.getStatus().equals("ok")){
                    realtime = response.body().getResult().getRealtime();
                    liveData.setValue(new Weather(realtime,daily));
                }
            }

            @Override
            public void onFailure(Call<RealtimeResponse> call, Throwable t) {
               liveData.setValue(null);
            }
        });

        return liveData;
    }
    //调用保存place的接口
    public void savePlace(Place place){
        PlaceDao.getInstance().savePlace(place);
    }
    //调用获取place的接口
    public Place getSavedPlace(){
        return PlaceDao.getInstance().getSavedPlace();
    }

    public boolean isPlaceSaved(){
        return PlaceDao.getInstance().isPlaceSaved();
    }

}
