package com.example.bbic;

public class SubwayList {

    String stationName;
    String stationID;
    int type;
    String laneName;
    String laneCity;
    int CityCode;

    public static class ExOBJ{
        //exOBJ = 환승역 정보
        String exOBJ_stationNme = "";
        int exOBJ_stationID = 0;
        int exOBJ_type = 0;
        String exOBJ_laneName = "";
        String exOBJ_laneCity = "";

        public ExOBJ(String exOBJ_stationNme, int exOBJ_stationID, int exOBJ_type, String exOBJ_laneName, String exOBJ_laneCity) {
            this.exOBJ_stationNme = exOBJ_stationNme;
            this.exOBJ_stationID = exOBJ_stationID;
            this.exOBJ_type = exOBJ_type;
            this.exOBJ_laneName = exOBJ_laneName;
            this.exOBJ_laneCity = exOBJ_laneCity;
        }
    }
    //prevOBJ = 이전역
    String prevOBJ_stationNme = "";
    int prevOBJ_stationID = 0;
    int prevOBJ_type = 0;
    String prevOBJ_laneName = "";
    String prevOBJ_laneCity = "";
    int prevOBJ_x;
    int prevOBJ_y;
    //nextOBJ = 다음역
    String nextOBJ_stationName = "";
    int nextOBJ_stationID = 0;
    int nextOBJ_type = 0;
    String nextOBJ_laneName = "";
    String nextOBJ_laneCity = "";
    //defaultInfo = 기본역 정보
    String defaultInfo_address;
    String defaultInfo_new_address;
    String defaultInfo_tel;
    //exitInfo = 출구정보
    public static class ExitInfo {
        String exitlnInfo_gateNo;
        String exitlnInfo_gateLink; // 주요명칭

        public ExitInfo(String exitlnInfo_gateNo, String exitlnInfo_gateLink) {
            this.exitlnInfo_gateNo = exitlnInfo_gateNo;
            this.exitlnInfo_gateLink = exitlnInfo_gateLink;
        }
    }

    public SubwayList(String stationName, String stationID, int type, String laneName, String laneCity, int cityCode) {
        this.stationName = stationName;
        this.stationID = stationID;
        this.type = type;
        this.laneName = laneName;
        this.laneCity = laneCity;
        CityCode = cityCode;
    }

    public String getDefaultInfo_address() {
        return defaultInfo_address;
    }

    public void setDefaultInfo_address(String defaultInfo_address) {
        this.defaultInfo_address = defaultInfo_address;
    }

    public String getDefaultInfo_new_address() {
        return defaultInfo_new_address;
    }

    public void setDefaultInfo_new_address(String defaultInfo_new_address) {
        this.defaultInfo_new_address = defaultInfo_new_address;
    }

    public String getDefaultInfo_tel() {
        return defaultInfo_tel;
    }

    public void setDefaultInfo_tel(String defaultInfo_tel) {
        this.defaultInfo_tel = defaultInfo_tel;
    }

    public String getPrevOBJ_stationNme() {
        return prevOBJ_stationNme;
    }

    public void setPrevOBJ_stationNme(String prevOBJ_stationNme) {
        this.prevOBJ_stationNme = prevOBJ_stationNme;
    }

    public int getPrevOBJ_stationID() {
        return prevOBJ_stationID;
    }

    public void setPrevOBJ_stationID(int prevOBJ_stationID) {
        this.prevOBJ_stationID = prevOBJ_stationID;
    }

    public int getPrevOBJ_type() {
        return prevOBJ_type;
    }

    public void setPrevOBJ_type(int prevOBJ_type) {
        this.prevOBJ_type = prevOBJ_type;
    }

    public String getPrevOBJ_laneName() {
        return prevOBJ_laneName;
    }

    public void setPrevOBJ_laneName(String prevOBJ_laneName) {
        this.prevOBJ_laneName = prevOBJ_laneName;
    }

    public String getPrevOBJ_laneCity() {
        return prevOBJ_laneCity;
    }

    public void setPrevOBJ_laneCity(String prevOBJ_laneCity) {
        this.prevOBJ_laneCity = prevOBJ_laneCity;
    }

    public String getNextOBJ_stationName() {
        return nextOBJ_stationName;
    }

    public void setNextOBJ_stationName(String nextOBJ_stationName) {
        this.nextOBJ_stationName = nextOBJ_stationName;
    }

    public int getNextOBJ_stationID() {
        return nextOBJ_stationID;
    }

    public void setNextOBJ_stationID(int nextOBJ_stationID) {
        this.nextOBJ_stationID = nextOBJ_stationID;
    }

    public int getNextOBJ_type() {
        return nextOBJ_type;
    }

    public void setNextOBJ_type(int nextOBJ_type) {
        this.nextOBJ_type = nextOBJ_type;
    }

    public String getNextOBJ_laneName() {
        return nextOBJ_laneName;
    }

    public void setNextOBJ_laneName(String nextOBJ_laneName) {
        this.nextOBJ_laneName = nextOBJ_laneName;
    }

    public String getNextOBJ_laneCity() {
        return nextOBJ_laneCity;
    }

    public void setNextOBJ_laneCity(String nextOBJ_laneCity) {
        this.nextOBJ_laneCity = nextOBJ_laneCity;
    }

    public int getPrevOBJ_x() {
        return prevOBJ_x;
    }

    public void setPrevOBJ_x(int prevOBJ_x) {
        this.prevOBJ_x = prevOBJ_x;
    }

    public int getPrevOBJ_y() {
        return prevOBJ_y;
    }

    public void setPrevOBJ_y(int prevOBJ_y) {
        this.prevOBJ_y = prevOBJ_y;
    }
}

