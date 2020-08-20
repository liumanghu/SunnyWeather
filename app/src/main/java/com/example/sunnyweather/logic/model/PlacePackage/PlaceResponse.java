package com.example.sunnyweather.logic.model.PlacePackage;

import java.util.ArrayList;

public class PlaceResponse {  //保存服务器响应信息
    private String status;
    private ArrayList<Place> places;

    public PlaceResponse(String status, ArrayList<Place> places) {
        this.status = status;
        this.places = places;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}

