package com.example.bbic.Data;

public class Subway {
    int stationCode;
    long userCode;
    String stationName;

    public int getStationCode() {
        return stationCode;
    }

    public void setStationCode(int stationCode) {
        this.stationCode = stationCode;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Subway(int stationCode, long userCode, String stationName) {
        this.stationCode = stationCode;
        this.userCode = userCode;
        this.stationName = stationName;
    }
}