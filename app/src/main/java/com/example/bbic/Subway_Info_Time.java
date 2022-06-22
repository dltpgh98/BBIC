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

                    String getHourTime = hour.format(date);
                    String getMinuetTime = minuet.format(date);
                    Log.d("====time=====",getHourTime);
                    Log.d("====timeMinut=====",getMinuetTime);

                    result = odsayData.getJson().getJSONObject("result");
                    JSONArray time = result.getJSONObject("OrdList").getJSONObject("up").getJSONArray("time");
                    Log.d("=====ssss====", "" + time);
                    String time_gep="";
                    stationR_name=new String[2];
                    stationL_name=new String[2];
                    stationR_time=new String[2];
                    stationL_time=new String[2];
                    for (int i = 0; i < time.length(); i++) {
                        if(time.getJSONObject(i).getString("Idx").equals(getHourTime)){
                            String timeStr = time.getJSONObject(i).getString("list");
                            Log.d("timeStr",""+timeStr);
                            stationMinuet = timeStr.split(" ");
                            for(int j = 0; j< stationMinuet.length;j++){
                                int times = Integer.valueOf(stationMinuet[j].replaceAll("[^0-9]",""));
                                String direction = stationMinuet[j].replaceAll("[0-9]","");
                                int minus =times-Integer.valueOf(getMinuetTime);
                                if(minus>0){
                                    System.out.println("==minus=="+minus);
                                    if(count<2){
                                        stationR_name[count]=direction;
                                        if(minus<2){
                                            stationR_time[count]="곧 도착";
                                        }else{
                                        stationR_time[count]=String.valueOf(minus);
                                        }
                                        Log.d("========statR============",""+stationR_name[count]);
                                    }
                                   count++;

                                }
                            }

                        }
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
