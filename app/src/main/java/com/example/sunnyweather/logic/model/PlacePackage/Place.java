package com.example.sunnyweather.logic.model;

public class Place{   //保存每一地点的位置信息
    private String name;
    private Location location;
    private String formatted_address;

    public Place(String name, Location location, String formatted_address) {
        this.name = name;
        this.location = location;
        this.formatted_address = formatted_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
