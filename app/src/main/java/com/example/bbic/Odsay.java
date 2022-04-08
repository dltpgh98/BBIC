package com.example.bbic;

import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class Odsay extends Maps_Activity {
    StationList[] StationList;
    StationList[] StationList1;
    SubwayList[] subwayLists;
    BusList[] busLists;
    int stationType;
    int count = 0;



    public Odsay() {
    }

    public OnResultCallbackListener subwayStationInfo = new OnResultCallbackListener() {//지하철 정보
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try{
              if(api == API.SUBWAY_STATION_INFO){
                  String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
                  int type = odsayData.getJson().getJSONObject("result").getInt("type");
                  String laneName = odsayData.getJson().getJSONObject("result").getString("laneName");
                  String laneCity = odsayData.getJson().getJSONObject("result").getString("laneCity");
                  String address = odsayData.getJson().getJSONObject("result").getJSONObject("defaultInfo").getString("address");
                  String new_adress = odsayData.getJson().getJSONObject("result").getJSONObject("defaultInfo").getString("new_address");

                  String exOBJ = odsayData.getJson().getJSONObject("result").getString("exOBJ");
                  if(!exOBJ.equals("")){
                      JSONArray exOBJArray = new JSONArray(exOBJ);//환승역 배열
                      for (int i = 0; i < exOBJArray.length(); i++){

                      }
                  }else {

                  }


              }
            }catch (Exception e){

            }
        }

        @Override
        public void onError(int i, String s, API api) {

        }
    };

    public OnResultCallbackListener busStationInfo = new OnResultCallbackListener() {  //특정 좌표 기준 반경내 대중교통 POI 정보
        // 호출 성공 시 실행
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try {
                // API Value 는 API 호출 메소드 명을 따라갑니다.
                //버스 정류장 아이디 검색 후 버스 정보 출력(해당 정류장의 버스번호, 운행 시간 등)
                if (api == API.BUS_STATION_INFO) {

                    String lane = odsayData.getJson().getJSONObject("result").getString("lane");
                    JSONArray laneArray = new JSONArray(lane);
                    for (int i = 0; i < laneArray.length(); i++){
                        JSONObject subJsonObject = laneArray.getJSONObject(i);
                        String busNo = subJsonObject.getString("busNo");
                        int type = subJsonObject.getInt("type");
                        int busID = subJsonObject.getInt("busID");
                        String busStartPoint = subJsonObject.getString("busStartPoint");
                        String busEndPoint = subJsonObject.getString("busEndPoint");
                        String busFirstTime = subJsonObject.getString("busFirstTime");
                        String busLastTime = subJsonObject.getString("busLastTime");
                        String busInterval = subJsonObject.getString("busInterval");
                        int busCityCode = subJsonObject.getInt("busCityCode");
                        String busCityName = subJsonObject.getString("busCityName");
                        String busLocalBlID = subJsonObject.getString("busLocalBlID");

                       busLists = new BusList[laneArray.length()];
                       busLists[i] = new BusList(busNo,type, busID, busStartPoint, busEndPoint,busFirstTime, busLastTime, busInterval, busCityCode, busCityName, busLocalBlID);



                        System.out.println("busNo" + busNo + "\n" + "busID" + busID + "\n"+"busStartPoint" + busStartPoint + "\n" + "busEndPoint" + busEndPoint);

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


    public OnResultCallbackListener pointSearch = new OnResultCallbackListener() {  //특정 좌표 기준 반경내 대중교통 POI 정보
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    //해당의 위치의 좌표 검색으로 버스 정류장인지 지하철 역인지 판단
                    if (api == API.POINT_SEARCH) {

                        String station = odsayData.getJson().getJSONObject("result").getString("station");
                        JSONArray stationAraay = new JSONArray(station);
                        for (int i = 0; i < stationAraay.length(); i++){
                            JSONObject subJsonObject = stationAraay.getJSONObject(i);
                            //int nonstopStation = subJsonObject.getInt("subJsonObject");
                            int stationClass = subJsonObject.getInt("stationClass");
                            String stationName = subJsonObject.getString("stationName");
                            int stationID = subJsonObject.getInt("stationID");
                            String arsID = subJsonObject.getString("arsID");
                            String ebid = subJsonObject.getString("ebid");
                            String x = subJsonObject.getString("x");
                            String y = subJsonObject.getString("y");
                            count++;
                            StationList = new StationList[stationAraay.length()];
                            StationList[i] = new StationList(stationClass,stationName,stationID,x,y);
                            System.out.println("stationClass : " +stationClass + "\n" + "stationName : " + stationName +"\n" + "count" + count);
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

    public StationList[] getStationList() {

         return this.StationList;
    }

    public void setStationList(StationList[] stationList) {
        this.StationList = stationList;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;

    }


}


