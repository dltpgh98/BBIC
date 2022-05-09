package com.example.bbic;

public class BusList {

    private String busNo;
    private int type;
    private int busID;
    private String busStartPoint;
    private String busEndPoint;
    private String busFirstTime;
    private String busLastTime;
    private String busInterval;
    private int busCityCode;
    private String busCityName;
    private String busLocalBlID;

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
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

    public int getBusCityCode() {
        return busCityCode;
    }

    public void setBusCityCode(int busCityCode) {
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

    public BusList(String busNo, int type, int busID, String busStartPoint, String busEndPoint, String busFirstTime, String busLastTime, String busInterval, int busCityCode, String busCityName, String busLocalBlID) {
        this.busNo = busNo;
        this.type = type;
        this.busID = busID;
        this.busStartPoint = busStartPoint;
        this.busEndPoint = busEndPoint;
        this.busFirstTime = busFirstTime;
        this.busLastTime = busLastTime;
        this.busInterval = busInterval;
        this.busCityCode = busCityCode;
        this.busCityName = busCityName;
        this.busLocalBlID = busLocalBlID;
    }
}