package com.example.sunnyweather.ui.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunnyweather.R;
import com.example.sunnyweather.databinding.ActivityWeatherBinding;
import com.example.sunnyweather.logic.model.DailPackage.Daily;
import com.example.sunnyweather.logic.model.DailPackage.LifeIndex;
import com.example.sunnyweather.logic.model.DailPackage.Skycon;
import com.example.sunnyweather.logic.model.DailPackage.Temperature;
import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.example.sunnyweather.logic.model.RealtimePackage.Realtime;
import com.example.sunnyweather.logic.model.Sky;
import com.example.sunnyweather.logic.model.Weather;
import com.example.sunnyweather.ui.place.PlaceFragment;
import com.example.sunnyweather.ui.place.PlaceViewModel;

import org.w3c.dom.Text;

import java.io.DataInput;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    public WeatherViewModel weatherViewModel;
    private String locationLng;
    private String locationLat;
    private String placeName;
    public ActivityWeatherBinding binding;
    private FragmentManager famg;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weather);
        famg = getSupportFragmentManager();
        transaction = famg.beginTransaction();
        transaction.replace(R.id.placefragment,new PlaceFragment());
        transaction.commit();
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        Intent intent = getIntent();
        locationLng = intent.getStringExtra("location_lng");
        locationLat = intent.getStringExtra("location_lat");
        placeName = intent.getStringExtra("place_name");

        if (weatherViewModel.locationLng.isEmpty()){
            weatherViewModel.locationLng = locationLng == null?"":locationLng;
        }
        if (weatherViewModel.locationLat.isEmpty()){
            weatherViewModel.locationLat = locationLat == null?"":locationLat;
        }
        if (weatherViewModel.placeName.isEmpty()){
            weatherViewModel.placeName = placeName == null?"":placeName;
        }


        weatherViewModel.weatherLiveData.observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather != null){
                    if (weather.getDaily()!=null&&weather.getRealtime()!=null){
                        showWeatherInfo(weather);
                    }
                }else {
                    Toast.makeText(WeatherActivity.this, "无法成功获取天气信息,请检查网络", Toast.LENGTH_SHORT).show();
                }
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        refreshWeather();
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWeather();
            }
        });

        binding.includeTitle.navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //当侧边页面关闭后关闭键盘
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(drawerView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        binding.setLifecycleOwner(this);
    }
    //用于刷新界面上的信息
    public void refreshWeather(){
        weatherViewModel.refreshWeather(weatherViewModel.locationLng,weatherViewModel.locationLat);
        binding.swipeRefresh.setRefreshing(true);
    }

    private void showWeatherInfo(Weather weather){
        binding.includeTitle.placeNameText.setText(weatherViewModel.placeName);
        Realtime realtime = weather.getRealtime();
        Daily daily = weather.getDaily();
        //填充now.xml布局中的数据
        String currentTemp = String.valueOf(realtime.getTemperature())+"°C";
        binding.includeTitle.currentTemp.setText(currentTemp);
        binding.includeTitle.currentSky.setText(Sky.getSky(realtime.getSkycon()).getInfo());
        String currentPM25Text = "空气指数"+(int)realtime.getAirQuality().getAqi().getChn();
        binding.includeTitle.currentAQI.setText(currentPM25Text);
        binding.includeTitle.nowLayout.setBackgroundResource(Sky.getSky(realtime.getSkycon()).getBg());

        //填充forecastLayout.xml布局中的数据
        binding.includeForecast.forecastLayout.removeAllViews();
        int days = daily.getSkycon().size();
        for (int i=0;i<days;++i){
            Skycon skycon = daily.getSkycon().get(i);
            Temperature temperature = daily.getTemperature().get(i);
            //将定义的forecast_item.xml加载到View对象中，其中父容器指定为在forecast.xml中指定的forecastLayout
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,binding.includeForecast.forecastLayout,false);
            TextView dateInfo = view.findViewById(R.id.dateInfo);  //显示时间的文本框
            ImageView skyIcon = view.findViewById(R.id.skyIcon);  //显示天气图标
            TextView skyInfo = view.findViewById(R.id.skyInfo);  //显示天气名称
            TextView temperatureInfo = view.findViewById(R.id.temperatureInfo);  //显示当前温度
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateInfo.setText(simpleDateFormat.format(skycon.getDate()));  //设置时间显示
            Sky sky = Sky.getSky(skycon.getValue());
            skyIcon.setImageResource(sky.getIcon());
            skyInfo.setText(sky.getInfo());
            String tempText = String.valueOf((int)temperature.getMin()) + "~" + String.valueOf((int)temperature.getMax());
            temperatureInfo.setText(tempText);
            binding.includeForecast.forecastLayout.addView(view);
        }

        //填充life_index.xml
        LifeIndex lifeIndex = daily.getLifeIndex();
        binding.includeLife.coldRiskText.setText(lifeIndex.getColdRisk().get(0).getDesc());
        binding.includeLife.dressingText.setText(lifeIndex.getDressing().get(0).getDesc());
        binding.includeLife.ultravioletText.setText(lifeIndex.getUltraviolet().get(0).getDesc());
        binding.includeLife.cardWashingText.setText(lifeIndex.getCarWashing().get(0).getDesc());
        binding.scrollView.setVisibility(View.VISIBLE);

    }
}