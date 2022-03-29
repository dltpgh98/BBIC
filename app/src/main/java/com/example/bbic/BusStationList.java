package com.example.bbic;

import android.util.Log;

public class BusStationList {

    private String stationName;


    public BusStationList(String stationName) {
        this.stationName = stationName;

        Log.d("Station name : %s", stationName);
    }


}