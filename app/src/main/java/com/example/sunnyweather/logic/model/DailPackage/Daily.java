package com.example.sunnyweather.logic.model.DailPackage;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Daily {
    private ArrayList<Temperature> temperature;
    private ArrayList<Skycon> skycon;
    @SerializedName("life_index")
    private LifeIndex lifeIndex;

    public Daily(ArrayList<Temperature> temperature, ArrayList<Skycon> skycon, LifeIndex lifeIndex) {
        this.temperature = temperature;
        this.skycon = skycon;
        this.lifeIndex = lifeIndex;
    }

    public ArrayList<Temperature> getTemperature() {
        return temperature;
    }

    public void setTemperature(ArrayList<Temperature> temperature) {
        this.temperature = temperature;
    }

    public ArrayList<Skycon> getSkycon() {
        return skycon;
    }

    public void setSkycon(ArrayList<Skycon> skycon) {
        this.skycon = skycon;
    }

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }
}
