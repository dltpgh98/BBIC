package com.example.bbic;

public class SubwayList {

    String stationName;
    String stationID;
    int type;
    String laneName;
    String laneCity;
    int CityCode;
    //exOBJ = 환승역 정보
    int exOBJ_stationID = 0;
    int exOBJ_type = 0;
    String exOBJ_laneName = "";
    String exOBJ_laneCity = "";
    String prevOBJ_stationNme = "";
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
    String exitlnInfo_gateNo;
    String exitlnInfo_gateLink; // 주요명칭

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getLaneCity() {
        return laneCity;
    }

    public void setLaneCity(String laneCity) {
        this.laneCity = laneCity;
    }

    public int getCityCode() {
        return CityCode;
    }

    public void setCityCode(int cityCode) {
        CityCode = cityCode;
    }

    public int getExOBJ_stationID() {
        return exOBJ_stationID;
    }

    public void setExOBJ_stationID(int exOBJ_stationID) {
        this.exOBJ_stationID = exOBJ_stationID;
    }

    public int getExOBJ_type() {
        return exOBJ_type;
    }

    public void setExOBJ_type(int exOBJ_type) {
        this.exOBJ_type = exOBJ_type;
    }

    public String getExOBJ_laneName() {
        return exOBJ_laneName;
    }

    public void setExOBJ_laneName(String exOBJ_laneName) {
        this.exOBJ_laneName = exOBJ_laneName;
    }

    public String getExOBJ_laneCity() {
        return exOBJ_laneCity;
    }

    public void setExOBJ_laneCity(String exOBJ_laneCity) {
        this.exOBJ_laneCity = exOBJ_laneCity;
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

    public String getPrev_laneName() {
        return prev_laneName;
    }

    public void setPrev_laneName(String prev_laneName) {
        this.prev_laneName = prev_laneName;
    }

    public String getPrev_laneCity() {
        return prev_laneCity;
    }

    public void setPrev_laneCity(String prev_laneCity) {
        this.prev_laneCity = prev_laneCity;
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

    public String getExitlnInfo_gateNo() {
        return exitlnInfo_gateNo;
    }

    public void setExitlnInfo_gateNo(String exitlnInfo_gateNo) {
        this.exitlnInfo_gateNo = exitlnInfo_gateNo;
    }

    public String getExitlnInfo_gateLink() {
        return exitlnInfo_gateLink;
    }

    public void setExitlnInfo_gateLink(String exitlnInfo_gateLink) {
        this.exitlnInfo_gateLink = exitlnInfo_gateLink;
    }

    public SubwayList(String stationName, String stationID, int type, String laneName, String laneCity, int cityCode, int exOBJ_stationID, int exOBJ_type, String exOBJ_laneName, String exOBJ_laneCity, String prevOBJ_stationNme, int prevOBJ_stationID, int prevOBJ_type, String prev_laneName, String prev_laneCity, int nextOBJ_stationID, int nextOBJ_type, String nextOBJ_laneName, String nextOBJ_laneCity, String defaultInfo_address, String defaultInfo_new_address, String defaultInfo_tel, String exitlnInfo_gateNo, String exitlnInfo_gateLink) {
        this.stationName = stationName;
        this.stationID = stationID;
        this.type = type;
        this.laneName = laneName;
        this.laneCity = laneCity;
        this.CityCode = cityCode;
        this.exOBJ_stationID = exOBJ_stationID;
        this.exOBJ_type = exOBJ_type;
        this.exOBJ_laneName = exOBJ_laneName;
        this.exOBJ_laneCity = exOBJ_laneCity;
        this.prevOBJ_stationNme = prevOBJ_stationNme;
        this.prevOBJ_stationID = prevOBJ_stationID;
        this.prevOBJ_type = prevOBJ_type;
        this.prev_laneName = prev_laneName;
        this.prev_laneCity = prev_laneCity;
        this.nextOBJ_stationID = nextOBJ_stationID;
        this.nextOBJ_type = nextOBJ_type;
        this.nextOBJ_laneName = nextOBJ_laneName;
        this.nextOBJ_laneCity = nextOBJ_laneCity;
        this.defaultInfo_address = defaultInfo_address;
        this.defaultInfo_new_address = defaultInfo_new_address;
        this.defaultInfo_tel = defaultInfo_tel;
        this.exitlnInfo_gateNo = exitlnInfo_gateNo;
        this.exitlnInfo_gateLink = exitlnInfo_gateLink;
    }
}