package com.example.sunnyweather.logic.model.DailPackage;

public class Result {
    private Daily daily;

    public Result(Daily daily) {
        this.daily = daily;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }
}
