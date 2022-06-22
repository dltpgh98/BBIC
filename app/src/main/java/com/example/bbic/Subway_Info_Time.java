package com.example.bbic;

import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Subway_Info_Time extends Maps_Activity {

    private JSONObject result;
    private int count;
    private int ifCount;
    private String[] stationMinuet;

    private String[] stationR_name;
    private String[] stationL_name;
    private String[] stationR_time;
    private String[] stationL_time;

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public String[] getStationR_name() {
        return stationR_name;
    }

    public void setStationR_name(String[] stationR_name) {
        this.stationR_name = stationR_name;
    }

    public String[] getStationR_time() {
        return stationR_time;
    }

    public void setStationR_time(String[] stationR_time) {
        this.stationR_time = stationR_time;
    }

    public String[] getStationL_name() {
        return stationL_name;
    }

    public void setStationL_name(String[] stationL_name) {
        this.stationL_name = stationL_name;
    }

    public String[] getStationL_time() {
        return stationL_time;
    }

    public void setStationL_time(String[] stationL_time) {
        this.stationL_time = stationL_time;
    }

    public Subway_Info_Time() {
    }

    public OnResultCallbackListener subway_timeList = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try {
                if (api == API.SUBWAY_TIME_TABLE) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat hour = new SimpleDateFormat("HH");
                    SimpleDateFormat minuet = new SimpleDateFormat("mm");


                    int getHourTime = Integer.valueOf(hour.format(date));
                    String getMinuetTime = minuet.format(date);
                    Log.d("====time=====", ""+getHourTime);
                    Log.d("====timeMinut=====", getMinuetTime);

                    result = odsayData.getJson().getJSONObject("result");
                    try {
                        JSONArray upTime = result.getJSONObject("OrdList").getJSONObject("up").getJSONArray("time");
                        JSONArray downTime = result.getJSONObject("OrdList").getJSONObject("down").getJSONArray("time");

                        String time_gep = "";
                        stationR_name = new String[2];
                        stationL_name = new String[2];
                        stationR_time = new String[2];
                        stationL_time = new String[2];
                        for (int i = 0; i < upTime.length(); i++) {
                            if (upTime.getJSONObject(i).getString("Idx").equals(String.valueOf(getHourTime))||upTime.getJSONObject(i).getString("Idx").equals(String.valueOf(getHourTime+1))) {
                                String timeStr = upTime.getJSONObject(i).getString("list");
                                String str=upTime.getJSONObject(i).getString("Idx");

                                stationMinuet = timeStr.split(" ");
                                for (int j = 0; j < stationMinuet.length; j++) {

                                    int times = Integer.valueOf(stationMinuet[j].replaceAll("[^0-9]", ""));
                                    String direction = stationMinuet[j].replaceAll("[0-9]", "");
                                    if(str.equals(Integer.valueOf(getHourTime+1))){
                                        times += 60;
                                    }
                                    Log.d("==times==",times+"==Idx=="+str);

                                    int minus = times - Integer.valueOf(getMinuetTime);
//                                    int minus = times - 56;

                                    if (minus > 0) {
                                        System.out.println("==minus==" + minus);
                                        if (count < 2) {
                                            stationR_name[count] = direction;
                                            if (minus < 2) {
                                                stationR_time[count] = "곧 도착";
                                            } else {
                                                stationR_time[count] = String.valueOf(minus) + "분전";
                                            }
                                            Log.d("========statR============", "" + stationR_name[count]);
                                        }
                                        count++;

                                    }
                                }

                            }
                        }
                        count=0;
                        for (int i = 0; i < downTime.length(); i++) {
                            if (downTime.getJSONObject(i).getString("Idx").equals(String.valueOf(getHourTime))||downTime.getJSONObject(i).getString("Idx").equals(String.valueOf(getHourTime+1))) {
                                String timeStr = downTime.getJSONObject(i).getString("list");
                                String str=downTime.getJSONObject(i).getString("Idx");

                                stationMinuet = timeStr.split(" ");
                                for (int j = 0; j < stationMinuet.length; j++) {
                                    int times = Integer.valueOf(stationMinuet[j].replaceAll("[^0-9]", ""));
                                    String direction = stationMinuet[j].replaceAll("[0-9]", "");
                                    if(str.equals(Integer.valueOf(getHourTime+1))){
                                        times += 60;
                                    }
                                    int minus = times - Integer.valueOf(getMinuetTime);
//                                    int minus = times - 56;
                                    if (minus > 0) {
                                        System.out.println("==minus==" + minus);
                                        if (count < 2) {
                                            stationL_name[count] = direction;
                                            if (minus < 2) {
                                                stationL_time[count] = "곧 도착";
                                            } else {
                                                stationL_time[count] = String.valueOf(minus) + "분전";
                                            }
                                            Log.d("========statR============", "" + stationL_name[count]);

                                            count++;
                                        }
                                    }
                                }

                            }
                        }
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(int i, String s, API api) {
            if (api == API.SUBWAY_TIME_TABLE) {
            }
        }
    };

}
