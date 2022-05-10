package com.example.bbic;

public class StationList {

    private int stationClass;
    private String stationName;
    private int stationID;
    private String x;
    private String y;

    public int getStationClass() {
        return stationClass;
    }

    public void setStationClass(int stationClass) {
        this.stationClass = stationClass;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
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

    public StationList(int stationClass, String stationName, int stationID, String x, String y) {
        this.stationClass = stationClass;
        this.stationName = stationName;
        this.stationID = stationID;
        this.x = x;
        this.y = y;
    }
}