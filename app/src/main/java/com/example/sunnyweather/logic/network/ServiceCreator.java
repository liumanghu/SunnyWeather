package com.example.sunnyweather.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private static final String BASE_URL = "https://api.caiyunapp.com/";
    private static Retrofit retrofit;

    public static <T> T Create(Class<T> serviceClass){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)//设置服务器接口的根路径
                    .addConverterFactory(GsonConverterFactory.create())//设置Retrofit解析数据时的转换库，设置完之后可以自动的将从服务器收到的Json字符串转换成对应的对象
                    .build(); //创建retrofit对象
        }
        return retrofit.create(serviceClass);
    }
}
