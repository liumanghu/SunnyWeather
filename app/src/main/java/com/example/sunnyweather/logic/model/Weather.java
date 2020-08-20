package com.example.sunnyweather.logic.model;

import com.example.sunnyweather.logic.model.DailPackage.Daily;
import com.example.sunnyweather.logic.model.RealtimePackage.Realtime;

public class Weather {
    private Realtime realtime;
    private Daily daily;

    public Weather(Realtime realtime, Daily daily) {
        this.realtime = realtime;
        this.daily = daily;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }
}
