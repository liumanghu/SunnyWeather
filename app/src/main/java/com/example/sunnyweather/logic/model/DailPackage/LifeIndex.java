package com.example.sunnyweather.logic.model.DailPackage;

import java.util.ArrayList;

public class LifeIndex {
    private ArrayList<LifeDescription> coldRisk;
    private ArrayList<LifeDescription> carWashing;
    private ArrayList<LifeDescription> ultraviolet;
    private ArrayList<LifeDescription> dressing;

    public LifeIndex(ArrayList<LifeDescription> coldRisk, ArrayList<LifeDescription> carWashing, ArrayList<LifeDescription> ultraviolet, ArrayList<LifeDescription> dressing) {
        this.coldRisk = coldRisk;
        this.carWashing = carWashing;
        this.ultraviolet = ultraviolet;
        this.dressing = dressing;
    }

    public ArrayList<LifeDescription> getColdRisk() {
        return coldRisk;
    }

    public void setColdRisk(ArrayList<LifeDescription> coldRisk) {
        this.coldRisk = coldRisk;
    }

    public ArrayList<LifeDescription> getCarWashing() {
        return carWashing;
    }

    public void setCarWashing(ArrayList<LifeDescription> carWashing) {
        this.carWashing = carWashing;
    }

    public ArrayList<LifeDescription> getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(ArrayList<LifeDescription> ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public ArrayList<LifeDescription> getDressing() {
        return dressing;
    }

    public void setDressing(ArrayList<LifeDescription> dressing) {
        this.dressing = dressing;
    }
}
