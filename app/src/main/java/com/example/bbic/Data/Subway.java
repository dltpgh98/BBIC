package com.example.bbic.Data;

public class Subway {
    int stationCode;
    long userCode;

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

    public Subway(int stationCode, long userCode) {
        this.stationCode = stationCode;
        this.userCode = userCode;
    }
}