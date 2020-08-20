package com.example.sunnyweather.ui.Fragement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.databinding.PlaceFragmentBinding;
import com.example.sunnyweather.logic.model.PlacePackage.Place;
import com.example.sunnyweather.logic.model.PlacePackage.PlaceResponse;
import com.example.sunnyweather.ui.place.PlaceAdapter;
import com.example.sunnyweather.ui.place.PlaceViewModel;
import com.example.sunnyweather.ui.weather.WeatherActivity;

import java.util.ArrayList;

public class PlaceFragment extends Fragment {

    public PlaceViewModel mViewModel;

    public static PlaceFragment newInstance() {
        return new PlaceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //初始化databinding
       ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.place__fragment,container,false);
       return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        if (mViewModel.isPlaceSaved()){
            Place place = mViewModel.getSavedPlace();
            Intent intent = new Intent(getContext(), WeatherActivity.class);
            intent.putExtra("location_lng",place.getLocation().getLng());
            intent.putExtra("location_lat",place.getLocation().getLat());
            intent.putExtra("place_name",place.getName());
            startActivity(intent);
        }
        
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        PlaceFragmentBinding binding = DataBindingUtil.setContentView(getActivity(),R.layout.place__fragment);
        binding.setLifecycleOwner(getActivity());
        binding.placeArrayList.setLayoutManager(layoutManager);
        PlaceAdapter adapter = new PlaceAdapter(this,mViewModel.placeList);
        binding.placeArrayList.setAdapter(adapter);

        binding.searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content =  editable.toString();
                if (!content.isEmpty()){
                    mViewModel.searchPlaces(content);
                }else {
                    binding.placeArrayList.setVisibility(View.GONE);
                    mViewModel.placeList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mViewModel.placeResponseLiveData.observe(getViewLifecycleOwner(), new Observer<PlaceResponse>() {
            @Override
            public void onChanged(PlaceResponse placeResponse) {
                if (placeResponse != null){
                    ArrayList<Place> places = placeResponse.getPlaces();
                    if (places != null){
                        binding.placeArrayList.setVisibility(View.VISIBLE);
                        mViewModel.placeList.clear();
                        mViewModel.placeList.addAll(places);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getActivity(), "未查到任何地点", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "请链接互联网", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // TODO: Use the ViewModel
    }

}