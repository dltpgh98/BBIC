package com.example.bbic;

public class BusList {

    private String busNo;
    private String type;
    private String busID;
    private String busStartPoint;
    private String busEndPoint;
    private String busFirstTime;
    private String busLastTime;
    private String busInterval;
    private String busCityCode;
    private String busCityName;
    private String busLocalBlID;

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getBusFirstTime() {
        return busFirstTime;
    }

    public void setBusFirstTime(String busFirstTime) {
        this.busFirstTime = busFirstTime;
    }

    public String getBusLastTime() {
        return busLastTime;
    }

    public void setBusLastTime(String busLastTime) {
        this.busLastTime = busLastTime;
    }

    public String getBusInterval() {
        return busInterval;
    }

    public void setBusInterval(String busInterval) {
        this.busInterval = busInterval;
    }

    public String getBusCityCode() {
        return busCityCode;
    }

    public void setBusCityCode(String busCityCode) {
        this.busCityCode = busCityCode;
    }

    public String getBusCityName() {
        return busCityName;
    }

    public void setBusCityName(String busCityName) {
        this.busCityName = busCityName;
    }

    public String getBusLocalBlID() {
        return busLocalBlID;
    }

    public void setBusLocalBlID(String busLocalBlID) {
        this.busLocalBlID = busLocalBlID;
    }

    public String getStationClass() {
        return busNo;
    }

    public void setStationClass(String stationClass) {
        this.busNo = stationClass;
    }

    public String getStationName() {
        return type;
    }

    public void setStationName(String stationName) {
        this.type = stationName;
    }

    public String getStationID() {
        return busID;
    }

    public void setStationID(String stationID) {
        this.busID = stationID;
    }

    public String getBusStartPoint() {
        return busStartPoint;
    }

    public void setBusStartPoint(String busStartPoint) {
        this.busStartPoint = busStartPoint;
    }

    public String getBusEndPoint() {
        return busEndPoint;
    }

    public void setBusEndPoint(String busEndPoint) {
        this.busEndPoint = busEndPoint;
    }

    public BusList(String stationClass, String stationName, String stationID, String x, String y) {
        this.busNo = stationClass;
        this.type = stationName;
        this.busID = stationID;
        this.busStartPoint = x;
        this.busEndPoint = y;
    }
}