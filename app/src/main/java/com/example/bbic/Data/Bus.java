package com.example.bbic.Data;

public class Bus {
    int buskey;
    int stationCode;
    long userCode;
    String busNum;
    String busStationName;
    String busDirction;

    public int getBuskey() {
        return buskey;
    }

    public void setBuskey(int buskey) {
        this.buskey = buskey;
    }

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

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusDirction() {
        return busDirction;
    }

    public void setBusDirction(String busDirction) {
        this.busDirction = busDirction;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName;
    }

    public Bus(int buskey, int stationCode, long userCode, String busNum, String busStationName, String busDirction) {
        this.buskey = buskey;
        this.stationCode = stationCode;
        this.userCode = userCode;
        this.busNum = busNum;
        this.busStationName = busStationName;
        this.busDirction = busDirction;
    }
}