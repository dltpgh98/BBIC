package com.example.bbic.Bookmark_Adapter;

public class PlaceData {
    //place
    private int place_iv;
    private String place_name;
    private String place_address;

    //bus
    private int bookmark_iv;
    private int bus_info_iv;
    private String station_name;
    private String to_station_name;
    private String bus_fastTime1;
    private String bus_nextTime1;
    private String bus_num1;
    private String bus_fastTime2;
    private String bus_nextTime2;
    private String bus_num2;
    private String bus_fastTime3;
    private String bus_nextTime3;
    private String bus_num3;//배열로 추후 변경 예정

    //subway
    private String subway_this_name;
    private String subway_left_direction;
    private String subway_right_direction;
    private String subway_left_name1;
    private String subway_left_time1;
    private String subway_left_whole1;
    private String subway_left_name2;
    private String subway_left_time2;
    private String subway_left_whole2;
    private String subway_left_name3;
    private String subway_left_time3;
    private String subway_left_whole3;

    private String subway_right_name1;
    private String subway_right_time1;
    private String subway_right_whole1;
    private String subway_right_name2;
    private String subway_right_time2;
    private String subway_right_whole2;
    private String subway_right_name3;
    private String subway_right_time3;
    private String subway_right_whole3;

    //friend_list
    private int friend_profile;
    private int friend_name;

    public PlaceData(int place_iv, String place_name,String place_address) {        //3개 place

        this.place_iv = place_iv;
        this.place_name = place_name;
        this.place_address = place_address;
    }

    public PlaceData(int bookmark_iv, int bus_info_iv, String station_name, String to_station_name,    //13개 bus
                     String bus_fastTime1, String bus_nextTime1, String bus_num1, String bus_fastTime2,
                     String bus_nextTime2, String bus_num2, String bus_fastTime3, String bus_nextTime3, String bus_num3) {

        this.bookmark_iv = bookmark_iv;
        this.bus_info_iv = bus_info_iv;
        this.station_name = station_name;
        this.to_station_name = to_station_name;
        this.bus_fastTime1 = bus_fastTime1;
        this.bus_nextTime1 = bus_nextTime1;
        this.bus_num1 = bus_num1;
        this.bus_fastTime2 = bus_fastTime2;
        this.bus_nextTime2 = bus_nextTime2;
        this.bus_num2 = bus_num2;
        this.bus_fastTime3 = bus_fastTime3;
        this.bus_nextTime3 = bus_nextTime3;
        this.bus_num3 = bus_num3;
    }


    public PlaceData(int bookmark_iv,String subway_this_name, String subway_left_direction, String subway_right_direction,    //22개 subway
                     String subway_left_name1, String subway_left_time1, String subway_left_whole1, 
                     String subway_left_name2, String subway_left_time2, String subway_left_whole2,
                     String subway_left_name3, String subway_left_time3, String subway_left_whole3,
                     String subway_right_name1, String subway_right_time1, String subway_right_whole1,
                     String subway_right_name2, String subway_right_time2, String subway_right_whole2,
                     String subway_right_name3, String subway_right_time3, String subway_right_whole3) {

        this.bookmark_iv = bookmark_iv;
        this.subway_this_name = subway_this_name;
        this.subway_left_direction = subway_left_direction;
        this.subway_right_direction = subway_right_direction;
        this.subway_left_name1 = subway_left_name1;
        this.subway_left_time1 = subway_left_time1;
        this.subway_left_whole1 = subway_left_whole1;
        this.subway_left_name2 = subway_left_name2;
        this.subway_left_time2 = subway_left_time2;
        this.subway_left_whole2 = subway_left_whole2;
        this.subway_left_name3 = subway_left_name3;
        this.subway_left_time3 = subway_left_time3;
        this.subway_left_whole3 = subway_left_whole3;
        this.subway_right_name1 = subway_right_name1;
        this.subway_right_time1 = subway_right_time1;
        this.subway_right_whole1 = subway_right_whole1;
        this.subway_right_name2 = subway_right_name2;
        this.subway_right_time2 = subway_right_time2;
        this.subway_right_whole2 = subway_right_whole2;
        this.subway_right_name3 = subway_right_name3;
        this.subway_right_time3 = subway_right_time3;
        this.subway_right_whole3 = subway_right_whole3;
    }


    public int getPlace_iv() {
        return place_iv;
    }

    public void setPlace_iv(int place_iv) {
        this.place_iv = place_iv;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_address() {
        return place_address;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }



    public int getBookmark_iv() {
        return bookmark_iv;
    }

    public void setBookmark_iv(int bookmark_iv) {
        this.bookmark_iv = bookmark_iv;
    }

    public int getBus_info_iv() {
        return bus_info_iv;
    }

    public void setBus_info_iv(int bus_info_iv) {
        this.bus_info_iv = bus_info_iv;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getTo_station_name() {
        return to_station_name;
    }

    public void setTo_station_name(String to_station_name) {
        this.to_station_name = to_station_name;
    }

    public String getBus_fastTime1() {
        return bus_fastTime1;
    }

    public void setBus_fastTime1(String bus_fastTime1) {
        this.bus_fastTime1 = bus_fastTime1;
    }

    public String getBus_nextTime1() {
        return bus_nextTime1;
    }

    public void setBus_nextTime1(String bus_nextTime1) {
        this.bus_nextTime1 = bus_nextTime1;
    }

    public String getBus_num1() {
        return bus_num1;
    }

    public void setBus_num1(String bus_num1) {
        this.bus_num1 = bus_num1;
    }

    public String getBus_fastTime2() {
        return bus_fastTime2;
    }

    public void setBus_fastTime2(String bus_fastTime2) {
        this.bus_fastTime2 = bus_fastTime2;
    }

    public String getBus_nextTime2() {
        return bus_nextTime2;
    }

    public void setBus_nextTime2(String bus_nextTime2) {
        this.bus_nextTime2 = bus_nextTime2;
    }

    public String getBus_num2() {
        return bus_num2;
    }

    public void setBus_num2(String bus_num2) {
        this.bus_num2 = bus_num2;
    }

    public String getBus_fastTime3() {
        return bus_fastTime3;
    }

    public void setBus_fastTime3(String bus_fastTime3) {
        this.bus_fastTime3 = bus_fastTime3;
    }

    public String getBus_nextTime3() {
        return bus_nextTime3;
    }

    public void setBus_nextTime3(String bus_nextTime3) {
        this.bus_nextTime3 = bus_nextTime3;
    }

    public String getBus_num3() {
        return bus_num3;
    }

    public void setBus_num3(String bus_num3) {
        this.bus_num3 = bus_num3;
    }

    public String getSubway_this_name() {
        return subway_this_name;
    }

    public void setSubway_this_name(String subway_this_name) {
        this.subway_this_name = subway_this_name;
    }

    public String getSubway_left_direction() {
        return subway_left_direction;
    }

    public void setSubway_left_direction(String subway_left_direction) {
        this.subway_left_direction = subway_left_direction;
    }

    public String getSubway_right_direction() {
        return subway_right_direction;
    }

    public void setSubway_right_direction(String subway_right_direction) {
        this.subway_right_direction = subway_right_direction;
    }

    public String getSubway_left_name1() {
        return subway_left_name1;
    }

    public void setSubway_left_name1(String subway_left_name1) {
        this.subway_left_name1 = subway_left_name1;
    }

    public String getSubway_left_time1() {
        return subway_left_time1;
    }

    public void setSubway_left_time1(String subway_left_time1) {
        this.subway_left_time1 = subway_left_time1;
    }

    public String getSubway_left_whole1() {
        return subway_left_whole1;
    }

    public void setSubway_left_whole1(String subway_left_whole1) {
        this.subway_left_whole1 = subway_left_whole1;
    }

    public String getSubway_left_name2() {
        return subway_left_name2;
    }

    public void setSubway_left_name2(String subway_left_name2) {
        this.subway_left_name2 = subway_left_name2;
    }

    public String getSubway_left_time2() {
        return subway_left_time2;
    }

    public void setSubway_left_time2(String subway_left_time2) {
        this.subway_left_time2 = subway_left_time2;
    }

    public String getSubway_left_whole2() {
        return subway_left_whole2;
    }

    public void setSubway_left_whole2(String subway_left_whole2) {
        this.subway_left_whole2 = subway_left_whole2;
    }

    public String getSubway_left_name3() {
        return subway_left_name3;
    }

    public void setSubway_left_name3(String subway_left_name3) {
        this.subway_left_name3 = subway_left_name3;
    }

    public String getSubway_left_time3() {
        return subway_left_time3;
    }

    public void setSubway_left_time3(String subway_left_time3) {
        this.subway_left_time3 = subway_left_time3;
    }

    public String getSubway_left_whole3() {
        return subway_left_whole3;
    }

    public void setSubway_left_whole3(String subway_left_whole3) {
        this.subway_left_whole3 = subway_left_whole3;
    }

    public String getSubway_right_name1() {
        return subway_right_name1;
    }

    public void setSubway_right_name1(String subway_right_name1) {
        this.subway_right_name1 = subway_right_name1;
    }

    public String getSubway_right_time1() {
        return subway_right_time1;
    }

    public void setSubway_right_time1(String subway_right_time1) {
        this.subway_right_time1 = subway_right_time1;
    }

    public String getSubway_right_whole1() {
        return subway_right_whole1;
    }

    public void setSubway_right_whole1(String subway_right_whole1) {
        this.subway_right_whole1 = subway_right_whole1;
    }

    public String getSubway_right_name2() {
        return subway_right_name2;
    }

    public void setSubway_right_name2(String subway_right_name2) {
        this.subway_right_name2 = subway_right_name2;
    }

    public String getSubway_right_time2() {
        return subway_right_time2;
    }

    public void setSubway_right_time2(String subway_right_time2) {
        this.subway_right_time2 = subway_right_time2;
    }

    public String getSubway_right_whole2() {
        return subway_right_whole2;
    }

    public void setSubway_right_whole2(String subway_right_whole2) {
        this.subway_right_whole2 = subway_right_whole2;
    }

    public String getSubway_right_name3() {
        return subway_right_name3;
    }

    public void setSubway_right_name3(String subway_right_name3) {
        this.subway_right_name3 = subway_right_name3;
    }

    public String getSubway_right_time3() {
        return subway_right_time3;
    }

    public void setSubway_right_time3(String subway_right_time3) {
        this.subway_right_time3 = subway_right_time3;
    }

    public String getSubway_right_whole3() {
        return subway_right_whole3;
    }

    public void setSubway_right_whole3(String subway_right_whole3) {
        this.subway_right_whole3 = subway_right_whole3;
    }
}

