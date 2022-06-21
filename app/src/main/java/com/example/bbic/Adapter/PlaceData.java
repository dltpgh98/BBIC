package com.example.bbic.Adapter;

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
    private int friend_stat;
    private int friend_setting;
    private String friend_name;

    //friend_ask
    private int friend_delete;
    private int friend_accept;

    //promise_list
    private int promise_sidebar;
    private int promise_profile1;
    private int promise_profile2;
    private int promise_profile3;
    private String promise_title;
    private String promise_address;
    private String promise_settime;

    //promise_ask
    private int promise_delete;
    private int promise_accept;

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

    public PlaceData(int friend_profile, int friend_stat, int friend_setting, String friend_name) {  // 4개 friend_list
        this.friend_profile = friend_profile;
        this.friend_stat = friend_stat;
        this.friend_setting = friend_setting;
        this.friend_name = friend_name;
    }

    public PlaceData(int friend_profile, int friend_stat, int friend_delete, int friend_accept, String friend_name) {  // 5개 friend_ask
        this.friend_profile = friend_profile;
        this.friend_stat = friend_stat;
        this.friend_delete = friend_delete;
        this.friend_accept = friend_accept;
        this.friend_name = friend_name;

    }

    public PlaceData(int promise_sidebar, int promise_profile1, int promise_profile2, int promise_profile3,
                     String promise_title, String promise_address, String promise_settime) {  // 7개 promise_list
        this.promise_sidebar = promise_sidebar;
        this.promise_profile1 = promise_profile1;
        this.promise_profile2 = promise_profile2;
        this.promise_profile3 = promise_profile3;
        this.promise_title = promise_title;
        this.promise_address = promise_address;
        this.promise_settime = promise_settime;
    }

    public PlaceData(int promise_sidebar, int promise_profile1, int promise_profile2, int promise_profile3,
                     String promise_title, String promise_address, String promise_settime,
                     int promise_delete, int promise_accept) {  // 9개 promise_ask
        this.promise_sidebar = promise_sidebar;
        this.promise_profile1 = promise_profile1;
        this.promise_profile2 = promise_profile2;
        this.promise_profile3 = promise_profile3;
        this.promise_title = promise_title;
        this.promise_address = promise_address;
        this.promise_settime = promise_settime;
        this.promise_delete = promise_delete;
        this.promise_accept = promise_accept;
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


    public int getFriend_profile() {
        return friend_profile;
    }

    public void setFriend_profile(int friend_profile) {
        this.friend_profile = friend_profile;
    }

    public int getFriend_stat() {
        return friend_stat;
    }

    public void setFriend_stat(int friend_stat) {
        this.friend_stat = friend_stat;
    }

    public int getFriend_setting() {
        return friend_setting;
    }

    public void setFriend_setting(int friend_setting) {
        this.friend_setting = friend_setting;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public int getFriend_delete() {
        return friend_delete;
    }

    public void setFriend_delete(int friend_delete) {
        this.friend_delete = friend_delete;
    }

    public int getFriend_accept() {
        return friend_accept;
    }

    public void setFriend_accept(int friend_accept) {
        this.friend_accept = friend_accept;
    }


    public int getPromise_sidebar() {
        return promise_sidebar;
    }

    public void setPromise_sidebar(int promise_sidebar) {
        this.promise_sidebar = promise_sidebar;
    }

    public int getPromise_profile1() {
        return promise_profile1;
    }

    public void setPromise_profile1(int promise_profile1) {
        this.promise_profile1 = promise_profile1;
    }

    public int getPromise_profile2() {
        return promise_profile2;
    }

    public void setPromise_profile2(int promise_profile2) {
        this.promise_profile2 = promise_profile2;
    }

    public int getPromise_profile3() {
        return promise_profile3;
    }

    public void setPromise_profile3(int promise_profile3) {
        this.promise_profile3 = promise_profile3;
    }

    public String getPromise_title() {
        return promise_title;
    }

    public void setPromise_title(String promise_title) {
        this.promise_title = promise_title;
    }

    public String getPromise_address() {
        return promise_address;
    }

    public void setPromise_address(String promise_address) {
        this.promise_address = promise_address;
    }

    public String getPromise_settime() {
        return promise_settime;
    }

    public void setPromise_settime(String promise_settime) {
        this.promise_settime = promise_settime;
    }

    public int getPromise_delete() {
        return promise_delete;
    }

    public void setPromise_delete(int promise_delete) {
        this.promise_delete = promise_delete;
    }

    public int getPromise_accept() {
        return promise_accept;
    }

    public void setPromise_accept(int promise_accept) {
        this.promise_accept = promise_accept;
    }
}

