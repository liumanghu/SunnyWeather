package com.example.sunnyweather.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sunnyweather.logic.model.PlaceResponse;
import com.example.sunnyweather.logic.network.PlaceService;
import com.example.sunnyweather.logic.network.ServiceCreator;

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
                if (placeResponse != null)liveData.setValue(placeResponse);
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
