package com.example.bbic;

import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

public class Point_Search extends Maps_Activity {
    StationList[] busStationList;

    public Point_Search() {
    }

    public OnResultCallbackListener pointSearch = new OnResultCallbackListener() {  //특정 좌표 기준 반경내 대중교통 POI 정보
        // 호출 성공 시 실행
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try {
                // API Value 는 API 호출 메소드 명을 따라갑니다.
                if (api == API.POINT_SEARCH) {

                    int count = odsayData.getJson().getJSONObject("result").getInt("count");
                    //String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
                    Log.d("Station count : %s", String.valueOf(count));
                    busStationList = new StationList[count];

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

                        busStationList[i] = new StationList(station.getJSONObject(0).getString("stationClass"),station.getJSONObject(i).getString("stationName"),station.getJSONObject(i).getString("stationID"),station.getJSONObject(i).getString("x"),station.getJSONObject(i).getString("y"));

                        System.out.println("클래스 확인" + busStationList[i].getStationClass() +  busStationList[i].getStationName() + busStationList[i].getStationID() + busStationList[i].getX() + busStationList[i].getY());
                    }

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

    public StationList[] getBusStationList() {
        return busStationList;
    }

    public void setBusStationList(StationList[] busStationList) {
        this.busStationList = busStationList;
    }
}
