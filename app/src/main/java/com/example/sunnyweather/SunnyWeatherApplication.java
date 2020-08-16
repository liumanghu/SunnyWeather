package com.example.sunnyweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class SunnyWeatherApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private static final String TOKEN  = "a9OHpzRgHkSdwXEN";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
