package com.example.bbic.Data;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;

public class PromiseFriendMarker {
    //위치,유저 이름, 유저 프로필, 약속 장소이름, 약속 이름, 약속시간
    public PromiseFriendMarker(LatLng markerProPos, String markerProUserName, String markerProUserProfile, String proPosName, String proTitleName, String proTime) {

        this.markerProPos = markerProPos;
        this.markerProUserName = markerProUserName;
        this.markerProUserProfile = markerProUserProfile;
        this.proPosName = proPosName;
        this.proTitleName = proTitleName;
        this.proTime = proTime;
    }
    private LatLng markerProPos; //유저 좌표
    private String markerProUserName; //유저 이름
    private String markerProUserProfile; //유저 프로필

    private String proPosName; //약속 장소이름
    private String proTitleName; //약속 제목
    private String proTime; //약속 시간


    public LatLng getMarkerProPos() {
        return markerProPos;
    }

    public void setMarkerProPos(LatLng markerProPos) {
        this.markerProPos = markerProPos;
    }

    public String getMarkerProUserName() {
        return markerProUserName;
    }

    public void setMarkerProUserName(String markerProUserName) {
        this.markerProUserName = markerProUserName;
    }

    public String getMarkerProUserProfile() {
        return markerProUserProfile;
    }

    public void setMarkerProUserProfile(String markerProUserProfile) {
        this.markerProUserProfile = markerProUserProfile;
    }

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
}
