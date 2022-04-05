package com.example.bbic;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

public class Odsay extends Maps_Activity {
    BusStationList[] busStationList;
    BusStationList[] busStationList1;


    int count;

    public Odsay() {
    }
    public OnResultCallbackListener busStationInfo = new OnResultCallbackListener() {  //특정 좌표 기준 반경내 대중교통 POI 정보
        // 호출 성공 시 실행
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try {
                // API Value 는 API 호출 메소드 명을 따라갑니다.
                if (api == API.BUS_STATION_INFO) {





                    JSONArray bus = odsayData.getJson().getJSONObject("result").getJSONArray("lane");
                    Log.d("bus info:", String.valueOf(bus));
//                    Log.d("stationClass:", String.valueOf(bus.getJSONObject(0).getString("stationClass")));
//                    Log.d("stationID:", String.valueOf(bus.getJSONObject(0).getString("stationID")));
//                    Log.d("stationName:", String.valueOf(bus.getJSONObject(0).getString("stationName")));
//                    System.out.println("이건%s" + String.valueOf(bus.getJSONObject(0).getDouble("y")));
//                    Log.d("y:", String.valueOf(bus.getJSONObject(0).getDouble("y")));


//                    for (int i = 0; i < bus.length(); i++){
//                        String info  = bus.getString(i);
//                        Log.d("info:", info);
//
//                        busStationList[i] = new BusStationList(bus.getJSONObject(0).getString("stationClass"),bus.getJSONObject(i).getString("stationName"),bus.getJSONObject(i).getString("stationID"),bus.getJSONObject(i).getString("x"),bus.getJSONObject(i).getString("y"));
//                        System.out.println("클래스 확인" + busStationList[i].getStationClass() +  busStationList[i].getStationName() + busStationList[i].getStationID() + busStationList[i].getX() + busStationList[i].getY());
//                    }


                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.POINT_SEARCH) {}
        }
    };


    public OnResultCallbackListener pointSearch = new OnResultCallbackListener() {  //특정 좌표 기준 반경내 대중교통 POI 정보
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.POINT_SEARCH) {

                        count = odsayData.getJson().getJSONObject("result").getInt("count");
                        //String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
                        Log.d("Station count : %s", String.valueOf(count));
                        busStationList = new BusStationList[count];

                        JSONArray station = odsayData.getJson().getJSONObject("result").getJSONArray("station");
                        Log.d("station info:", String.valueOf(station));
                        Log.d("stationClass:", String.valueOf(station.getJSONObject(0).getString("stationClass")));
                        Log.d("stationID:", String.valueOf(station.getJSONObject(0).getString("stationID")));
                        Log.d("stationName:", String.valueOf(station.getJSONObject(0).getString("stationName")));
                        System.out.println("이건%s" + String.valueOf(station.getJSONObject(0).getDouble("y")));
                        Log.d("y:", String.valueOf(station.getJSONObject(0).getDouble("y")));


                        for (int i = 0; i < station.length(); i++){
                            String info  = station.getString(i);
                            Log.d("info:", info);

                            busStationList[i] = new BusStationList(station.getJSONObject(0).getString("stationClass"),station.getJSONObject(i).getString("stationName"),station.getJSONObject(i).getString("stationID"),station.getJSONObject(i).getString("x"),station.getJSONObject(i).getString("y"));
                            System.out.println("클래스 확인" + busStationList[i].getStationClass() +  busStationList[i].getStationName() + busStationList[i].getStationID() + busStationList[i].getX() + busStationList[i].getY());
                        }


                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                busStationList1 = new BusStationList[0];
                busStationList1[0].setStationClass("");
                busStationList1[0].setStationID("");
                busStationList1[0].setStationName("");
                busStationList1[0].setX("");
                busStationList1[0].setY("");
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.POINT_SEARCH) {}
            }
        };

    public BusStationList[] getBusStationList() {
        //System.out.println("클래스 확인0" + busStationList[0].getStationClass() +  busStationList[0].getStationName() + busStationList[0].getStationID() + busStationList[0].getX() + busStationList[0].getY());
        if(count == 0){
            return this.busStationList1;
        }else
        return this.busStationList;
    }

    public void setBusStationList(BusStationList[] busStationList) {
        this.busStationList = busStationList;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
