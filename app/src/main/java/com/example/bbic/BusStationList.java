package com.example.bbic;

import android.util.Log;

public class BusStationList {

    public String stationClass;
    public String stationName;
    public String stationID;
    public String x;
    public String y;


    public BusStationList(String stationClass,String stationName, String stationID, String x, String y) {
        this.stationClass = stationClass;
        this.stationName = stationName;
        this.stationID = stationID;
        this.x = x;
        this.y = y;

        Log.d("stationClass : %s", stationClass);
        Log.d("Station name : %s", stationName);
        Log.d("stationID : %s", stationID);
        Log.d("x : %s", x);
        Log.d("y : %s", y);


    }


}