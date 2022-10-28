package com.example.bbic.Data;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class PromiseFriendMarker {
    //위치,유저 이름, 유저 프로필, 약속 장소이름, 약속 이름, 약속시간
//    public PromiseFriendMarker(LatLng markerProPos, String markerProUserName, String markerProUserProfile, String proPosName, String proTitleName, String proTime) {
//
//        this.markerProPos = markerProPos;
//        this.markerProUserName = markerProUserName;
//        this.markerProUserProfile = markerProUserProfile;
//        this.proPosName = proPosName;
//        this.proTitleName = proTitleName;
//        this.proTime = proTime;
//    }

//    //위치,유저long,유저lat,유저 이름, 유저 프로필, 약속 장소이름, 약속 이름, 약속시간
//    public PromiseFriendMarker(LatLng markerProPos, String[] myFrLong, String[] myFrLat, String markerProUserName, String markerProUserProfile, String proPosName, String proTitleName, String proTime) {
//        this.markerProPos = markerProPos;
//        this.myFrLong = myFrLong;
//        this.myFrLat = myFrLat;
//        this.markerProUserName = markerProUserName;
//        this.markerProUserProfile = markerProUserProfile;
//        this.proPosName = proPosName;
//        this.proTitleName = proTitleName;
//        this.proTime = proTime;
//    }

    //약속 장소이름, 약속 제목, 약속시간, 유저 long, 유저 lat, 유저 위치 공유 수락여부, 유저 이름, 유저 프로필, 유저
    public PromiseFriendMarker(String proPosName, String proTitleName, String proTime,
                               String[] proFrLong, String[] proFrLat, String[] proFrLocalCheck, String[] markerProUserName, String[] markerProUserProfile, String[] markerProUser) {
        this.proPosName = proPosName;
        this.proTitleName = proTitleName;
        this.proTime = proTime;
        this.proFrLong = proFrLong;
        this.proFrLat = proFrLat;
        this.proFrLocalCheck = proFrLocalCheck;
        this.markerProUserName = markerProUserName;
        this.markerProUserProfile = markerProUserProfile;
        this.markerProUser = markerProUser;
    }

    private String proPosName; //약속 장소이름
    private String proTitleName; //약속 제목
    private String proTime; //약속 시간

    private String proFrLong[] ; //유저 long
    private String proFrLat[]; //유저 lat
    private String proFrLocalCheck[]; // 유저 위치 공유 수락여부

    private String markerProUserName[]; //유저 이름
    private String markerProUserProfile[]; //유저 프로필
    private String markerProUser[]; //유저 코드


//    private LatLng markerProPos; //유저 좌표

    public String getProPosName() {
        return proPosName;
    }

    public void setProPosName(String proPosName) {
        this.proPosName = proPosName;
    }

    public String getProTitleName() {
        return proTitleName;
    }

    public void setProTitleName(String proTitleName) {
        this.proTitleName = proTitleName;
    }

    public String getProTime() {
        return proTime;
    }

    public void setProTime(String proTime) {
        this.proTime = proTime;
    }

    public String[] getProFrLong() {
        return proFrLong;
    }

    public void setProFrLong(String[] proFrLong) {
        this.proFrLong = proFrLong;
    }

    public String[] getProFrLat() {
        return proFrLat;
    }

    public void setProFrLat(String[] proFrLat) {
        this.proFrLat = proFrLat;
    }

    public String[] getProFrLocalCheck() {
        return proFrLocalCheck;
    }

    public void setProFrLocalCheck(String[] proFrLocalCheck) {
        this.proFrLocalCheck = proFrLocalCheck;
    }

    public String[] getMarkerProUserName() {
        return markerProUserName;
    }

    public void setMarkerProUserName(String[] markerProUserName) {
        this.markerProUserName = markerProUserName;
    }

    public String[] getMarkerProUserProfile() {
        return markerProUserProfile;
    }

    public void setMarkerProUserProfile(String[] markerProUserProfile) {
        this.markerProUserProfile = markerProUserProfile;
    }

    public String[] getMarkerProUser() {
        return markerProUser;
    }

    public void setMarkerProUser(String[] markerProUser) {
        this.markerProUser = markerProUser;
    }


//    public LatLng getMarkerProPos() {
//        return markerProPos;
//    }
//
//    public void setMarkerProPos(LatLng markerProPos) {
//        this.markerProPos = markerProPos;
//    }

}
