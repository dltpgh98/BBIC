package com.example.bbic;

import org.json.JSONArray;

public class Find_way_Data {
    private JSONArray object;
    private int onFoot_time;
    private int bus_time;
    private int sub_time;
    private int total_time;
    private String bus_num;
    private String sub_num;
    private int bus_iv;
    private int sub_iv;
    private int expansion_iv;

    public Find_way_Data(JSONArray object,int onFoot_time,int bus_iv,int bus_time,int sub_iv,int sub_time,int total_time,int expansion_iv,String bus_num,String sub_num){  //통합(버스+지하철) 인덱스번호 3번
        this.object=object;
        this.onFoot_time=onFoot_time;
        this.bus_iv=bus_iv;
        this.bus_time=bus_time;
        this.sub_iv=sub_iv;
        this.sub_time=sub_time;
        this.total_time=total_time;
        this.expansion_iv=expansion_iv;
        this.bus_num=bus_num;
        this.sub_num=sub_num;
    }



    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public JSONArray getObject() {
        return object;
    }

    public void setObject(JSONArray object) {
        this.object = object;
    }

    public int getOnFoot_time() {
        return onFoot_time;
    }

    public void setOnFoot_time(int onFoot_time) {
        this.onFoot_time = onFoot_time;
    }

    public int getBus_time() {
        return bus_time;
    }

    public void setBus_time(int bus_time) {
        this.bus_time = bus_time;
    }

    public int getSub_time() {
        return sub_time;
    }

    public void setSub_time(int sub_time) {
        this.sub_time = sub_time;
    }

    public String getBus_num() {
        return bus_num;
    }

    public void setBus_num(String bus_num) {
        this.bus_num = bus_num;
    }

    public String getSub_num() {
        return sub_num;
    }

    public void setSub_num(String sub_num) {
        this.sub_num = sub_num;
    }

    public int getExpansion_iv() {
        return expansion_iv;
    }

    public void setExpansion_iv(int expansion_iv) {
        this.expansion_iv = expansion_iv;
    }

    public int getBus_iv() {
        return bus_iv;
    }

    public void setBus_iv(int bus_iv) {
        this.bus_iv = bus_iv;
    }

    public int getSub_iv() {
        return sub_iv;
    }

    public void setSub_iv(int sub_iv) {
        this.sub_iv = sub_iv;
    }
}
