package com.example.bbic.Bookmark_Adapter;

public class PlaceData {
    private int place_iv;
    private String place_name;
    private String place_address;

    public PlaceData(int place_iv, String place_name,String place_address) {
        this.place_iv = place_iv;
        this.place_name = place_name;
        this.place_address = place_address;
    }

    public int getPlace_iv() {
        return place_iv;
    }

    public void setPlace_iv(int place_iv) {
        this.place_iv = place_iv;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_address() {
        return place_address;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }
}
