package com.example.sunnyweather.ui.place;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sunnyweather.logic.Repository;
import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.example.sunnyweather.logic.model.PlacePackage.PlaceResponse;

import java.util.ArrayList;

public class PlaceViewModel extends ViewModel {
    //保存当前用户查询的城市
    private MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    //用于缓存界面上显示的城市数据
    public ArrayList<Place> placeList = new ArrayList<>();

    //因为LiveData<PlaceResponse>对象时通过Repository产生的，在PlaceVIewMOdel中无法进行观察，因此需要用到Transformations的SwitchMap方法进行转化
    public LiveData<PlaceResponse> placeResponseLiveData = Transformations.switchMap(searchLiveData, new Function<String, LiveData<PlaceResponse>>() {
        @Override
        public LiveData<PlaceResponse> apply(String input) {
            //当searchLiveData中的数据发生改变时，就调用该转换方法，调用Repository中的searchPlace方法发送Http请求获取返回结果并将其转换成可观察的LiveData
            return Repository.getInstance().searchPlaces(searchLiveData.getValue());
        }
    });

    public void searchPlaces(String query){
        searchLiveData.setValue(query);
    }

    public void savePlace(Place place){
        Repository.getInstance().savePlace(place);
    }

    public Place getSavedPlace(){
        return Repository.getInstance().getSavedPlace();
    }

    public boolean isPlaceSaved(){
        return Repository.getInstance().isPlaceSaved();
    }
}
