package com.example.bbic;

import android.util.Log;

public class BusStationList {

    private String stationClass;
    private String stationName;
    private String stationID;
    private String x;
    private String y;

    public String getStationClass() {
        return stationClass;
    }

    public void setStationClass(String stationClass) {
        this.stationClass = stationClass;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public BusStationList(String stationClass, String stationName, String stationID, String x, String y) {
        this.stationClass = stationClass;
        this.stationName = stationName;
        this.stationID = stationID;
        this.x = x;
        this.y = y;
    }
}