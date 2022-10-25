package com.example.bbic.Data;

import com.naver.maps.geometry.LatLng;

public class FriendMarker {

    public FriendMarker(LatLng markerPos, String markerUserName, String markerUserProfile, String markerUserKode) {
        this.markerPos = markerPos;
        this.markerUserName = markerUserName;
        this.markerUserProfile = markerUserProfile;
        this.markerUserKode = markerUserKode;
    }


    private LatLng markerPos;
    private String markerUserName;
    private String markerUserProfile;
    private String markerUserKode;


    public LatLng getMarkerPos() {
        return markerPos;
    }

    public void setMarkerPos(LatLng markerPos) {
        this.markerPos = markerPos;
    }

    public String getMarkerUserName() {
        return markerUserName;
    }

    public void setMarkerUserName(String markerUserName) {
        this.markerUserName = markerUserName;
    }

    public String getMarkerUserProfile() {
        return markerUserProfile;
    }

    public void setMarkerUserProfile(String markerUserProfile) {
        this.markerUserProfile = markerUserProfile;
    }

    public String getMarkerUserKode() {
        return markerUserKode;
    }

    public void setMarkerUserKode(String markerUserKode) {
        this.markerUserKode = markerUserKode;
    }
}