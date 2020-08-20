package com.example.sunnyweather.logic.model.DailPackage;

public class DailyResponse {
    private String status;
    private Result result;

    public DailyResponse(String status, Result result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
