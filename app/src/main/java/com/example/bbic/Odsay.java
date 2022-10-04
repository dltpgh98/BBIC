package com.example.bbic;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Odsay extends Maps_Activity {
    StationList[] StationList;
    StationList[] StationList1;
    SubwayList subwayLists;
    SubwayList.ExOBJ[] ExOBJ;
    SubwayList.ExitInfo[] ExitInfo;
    BusList[] busLists;
    int stationType;
    int count = 0;
    String arsID;

    public Odsay() {
    }

    public OnResultCallbackListener subwayStationInfo = new OnResultCallbackListener() {//지하철 정보
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try {
                if (api == API.SUBWAY_STATION_INFO) {
                    //해당 역 정보
                    String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
                    String stationID = odsayData.getJson().getJSONObject("result").getString("stationID");
                    int type = odsayData.getJson().getJSONObject("result").getInt("type");
                    int cityCode = odsayData.getJson().getJSONObject("result").getInt("CityCode");
                    String laneName = odsayData.getJson().getJSONObject("result").getString("laneName");
                    String laneCity = odsayData.getJson().getJSONObject("result").getString("laneCity");
                    String address = odsayData.getJson().getJSONObject("result").getJSONObject("defaultInfo").getString("address");
                    String tel = odsayData.getJson().getJSONObject("result").getJSONObject("defaultInfo").getString("tel");
                    String new_adress;

                    //환승역 정보
                    String exOBJ;
                    JSONArray exOBJArray;
                    JSONObject sunJsonObject;
                    String ex_stationName;
                    int ex_stationID;
                    int ex_type;
                    String ex_laneName;
                    String ex_laneCity;

                    //이전역 정보
                    String prevOBJ;
                    JSONArray prevOBJArray;
                    JSONObject prevOBJObject;
                    String prev_stationName;
                    int prev_stationID;
                    int prev_type;
                    String prev_laneName;
                    String prev_laneCity;
                    double prev_x;
                    double prev_y;

                    //다음역 정보
                    String nextOBJ;
                    JSONArray nextOBJArray;
                    JSONObject nextOBJObject;
                    String next_stationName;
                    int next_stationID;
                    int next_type;
                    String next_laneName;
                    String next_laneCity;
                    double next_x;
                    double next_y;


                    subwayLists = new SubwayList(stationName, stationID, type, laneName, laneCity, cityCode);
                    subwayLists.setDefaultInfo_tel(tel);


                    // 역 마다 신주소 여부
                    try {
                        new_adress = odsayData.getJson().getJSONObject("result").getJSONObject("defaultInfo").getString("new_address");
                        subwayLists.setDefaultInfo_new_address(new_adress);
                    } catch (JSONException e) {
                        new_adress = "";
                        subwayLists.setDefaultInfo_new_address(new_adress);
                        e.printStackTrace();
                    }


                    //환승역 여부
                    try {
                        exOBJ = odsayData.getJson().getJSONObject("result").getJSONObject("exOBJ").getString("station");
                        exOBJArray = new JSONArray(exOBJ);//환승역 배열
                        for (int i = 0; i < exOBJArray.length(); i++) {
                            sunJsonObject = exOBJArray.getJSONObject(i);
                            ex_stationName = sunJsonObject.getString("stationName");
                            ex_stationID = sunJsonObject.getInt("stationID");
                            ex_type = sunJsonObject.getInt("type");
                            ex_laneName = sunJsonObject.getString("laneName");
                            ex_laneCity = sunJsonObject.getString("laneCity");

                            ExOBJ = new SubwayList.ExOBJ[exOBJArray.length()];
                            ExOBJ[i] = new SubwayList.ExOBJ(ex_stationName, ex_stationID, ex_type, ex_laneName, ex_laneCity);

                            System.out.println("sunJsonObject" + sunJsonObject + "\n" + "ex_stationName" + ex_stationName + "\n" + "ex_stationID" + ex_stationID + "\n" + "ex_type" + ex_type + "\n");
                        }
                    } catch (JSONException e) {
                        exOBJ = "";
                        e.printStackTrace();
                        System.out.println("환승역 없음");
                    }


                    //이전역 여부
                    try {
                        prevOBJ = odsayData.getJson().getJSONObject("result").getJSONObject("prevOBJ").getString("station");
                        prevOBJArray = new JSONArray(prevOBJ);//이전역 배열
                        for (int i = 0; i < prevOBJArray.length(); i++) {
                            prevOBJObject = prevOBJArray.getJSONObject(i);
                            prev_stationName = prevOBJObject.getString("stationName");
                            prev_stationID = prevOBJObject.getInt("stationID");
                            prev_type = prevOBJObject.getInt("type");
                            prev_laneName = prevOBJObject.getString("laneName");
                            prev_laneCity = prevOBJObject.getString("laneCity");
                            prev_x = prevOBJObject.getDouble("x");
                            prev_y = prevOBJObject.getDouble("y");

                            subwayLists.setPrevOBJ_stationNme(prev_stationName);
                            subwayLists.setPrevOBJ_stationID(prev_stationID);
                            subwayLists.setPrevOBJ_type(prev_type);
                            subwayLists.setPrevOBJ_laneName(prev_laneName);
                            subwayLists.setPrevOBJ_laneCity(prev_laneCity);


                            System.out.println("prev_stationName" + prev_stationName + "\n" + "prev_stationID" + prev_stationID + "\n");
                        }
                    } catch (JSONException e) {
                        prevOBJ = "";
                        System.out.println("이전역 없음");
                        e.printStackTrace();
                    }


                    //다음역 여부
                    try {
                        nextOBJ = odsayData.getJson().getJSONObject("result").getJSONObject("nextOBJ").getString("station");
                        nextOBJArray = new JSONArray(nextOBJ);//다음역 배열
                        for (int i = 0; i < nextOBJArray.length(); i++) {
                            nextOBJObject = nextOBJArray.getJSONObject(i);
                            next_stationName = nextOBJObject.getString("stationName");
                            next_stationID = nextOBJObject.getInt("stationID");
                            next_type = nextOBJObject.getInt("type");
                            next_laneName = nextOBJObject.getString("laneName");
                            next_laneCity = nextOBJObject.getString("laneCity");
                            next_x = nextOBJObject.getDouble("x");
                            next_y = nextOBJObject.getDouble("y");


                            subwayLists.setNextOBJ_stationName(next_stationName);
                            subwayLists.setNextOBJ_stationID(next_stationID);
                            subwayLists.setNextOBJ_type(next_type);
                            subwayLists.setNextOBJ_laneName(next_laneName);
                            subwayLists.setNextOBJ_laneCity(next_laneCity);

                            System.out.println("next_stationName" + next_stationName + "\n" + "next_stationID" + next_stationID + "\n");
                        }
                    } catch (JSONException e) {
                        nextOBJ = "";
                        System.out.println("다음역 없음");
                        e.printStackTrace();
                    }

                    //출구 정보
                    String gate = odsayData.getJson().getJSONObject("result").getJSONObject("exitInfo").getString("gate");// 츨구 정보
                    JSONArray gateArray = new JSONArray(gate);
                    for (int i = 0; i < gateArray.length(); i++) {
                        JSONObject subJsonObject = gateArray.getJSONObject(i);
                        String gateNo = subJsonObject.getString("gateNo");
                        String gateLink = subJsonObject.getString("gateLink");

                        ExitInfo = new SubwayList.ExitInfo[gateArray.length()];
                        ExitInfo[i] = new SubwayList.ExitInfo(gateNo, gateLink);

                        System.out.println("gateLink :" + gateLink + " " + "i : " + i);
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
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
//
                    String lane = odsayData.getJson().getJSONObject("result").getString("lane");
                    JSONArray laneArray = new JSONArray(lane);
                    busLists = new BusList[laneArray.length()];
                    arsID= odsayData.getJson().getJSONObject("result").getString("arsID");
                    for (int i = 0; i < laneArray.length(); i++) {
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


                        busLists[i] = new BusList(busNo, type, busID, busStartPoint, busEndPoint, busFirstTime, busLastTime, busInterval, busCityCode, busCityName, busLocalBlID);


                        System.out.println("사이즈 지정" + busLists);
                        System.out.println(busLists[i] + "\n" + i + "\n" + busNo + busNo + "\n" + "busID" + busID + "\n" + "busStartPoint" + busStartPoint + "\n" + "busEndPoint" + busEndPoint);

                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.POINT_SEARCH) {
            }
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

                    System.out.println("pointSearch : " + odsayData.getJson() + "   api: " + api);
                    String station = odsayData.getJson().getJSONObject("result").getString("station");
                    JSONArray stationAraay = new JSONArray(station);
                    StationList = new StationList[stationAraay.length()];
                    for (int i = 0; i < stationAraay.length(); i++) {
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

                        StationList[i] = new StationList(stationClass, stationName, stationID, x, y);
                        System.out.println("stationClass : " + stationClass + "\n" + "stationName : " + stationName + "\n" + "count" + count);
                    }

                }
            } catch (JSONException e) {
                //e.printStackTrace();
                System.out.println("콜백 리스너 에러");
            }

        }

        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.POINT_SEARCH) {
            }
        }
    };

    public StationList[] getStationList() {

        return this.StationList;
    }

    public void setStationList(StationList[] stationList) {
        this.StationList = stationList;
    }

    public BusList[] getBusLists() {
        return busLists;
    }

    public void setBusLists(BusList[] busLists) {
        this.busLists = busLists;
    }

    public String getArsID() {
        return arsID;
    }

    public void setArsID(String arsID) {
        this.arsID = arsID;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;

    }


}

