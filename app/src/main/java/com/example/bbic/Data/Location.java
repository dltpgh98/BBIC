package com.example.bbic.Data;

public class Location {
    private String locationName;
    private long userCode;
    private String locationAddress;
    private double locationLong;
    private double locationLat;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public Location(String locationName, long userCode, String locationAddress, double locationLong, double locationLat) {
        this.locationName = locationName;
        this.userCode = userCode;
        this.locationAddress = locationAddress;
        this.locationLong = locationLong;
        this.locationLat = locationLat;
    }
}
