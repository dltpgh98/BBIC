package com.example.bbic;

public class SubwayList {

    String stationName;
    String stationID;
    int type;
    String laneName;
    String laneCity;
    int CityCode;

    class ExOBJ{
        //exOBJ = 환승역 정보
        int exOBJ_stationID = 0;
        int exOBJ_type = 0;
        String exOBJ_laneName = "";
        String exOBJ_laneCity = "";
        String prevOBJ_stationNme = "";
    }
    //prevOBJ = 이전역
    int prevOBJ_stationID = 0;
    int prevOBJ_type = 0;
    String prev_laneName = "";
    String prev_laneCity = "";
    //nextOBJ = 다음역
    int nextOBJ_stationID = 0;
    int nextOBJ_type = 0;
    String nextOBJ_laneName = "";
    String nextOBJ_laneCity = "";
    //defaultInfo = 기본역 정보
    String defaultInfo_address;
    String defaultInfo_new_address;
    String defaultInfo_tel;
    //exitInfo = 출구정보
    class ExitInfo {
        String exitlnInfo_gateNo;
        String exitlnInfo_gateLink; // 주요명칭
    }
    


}

