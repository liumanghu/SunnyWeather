package com.example.sunnyweather.logic.model.RealtimePackage;

public class AirQuality {
    private AQI aqi;

    public AirQuality(AQI aqi) {
        this.aqi = aqi;
    }

    public AQI getAqi() {
        return aqi;
    }

    public void setAqi(AQI aqi) {
        this.aqi = aqi;
    }
}
