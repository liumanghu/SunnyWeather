package com.example.sunnyweather.logic.model.RealtimePackage;

import com.google.gson.annotations.SerializedName;

public class Realtime {
    private String skycon;

    private float temperature;

    @SerializedName("air_quality")
    private AirQuality airQuality;


    public Realtime(String skycon, float temperature, AirQuality airQuality) {
        this.skycon = skycon;
        this.temperature = temperature;
        this.airQuality = airQuality;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
    }
}
