package com.example.bbic.Data;

import com.naver.maps.geometry.LatLng;

public class FriendMarker {

    public FriendMarker(LatLng markerPos, String markerUserName, String markerUserProfile) {
        this.markerPos = markerPos;
        this.markerUserName = markerUserName;
        this.markerUserProfile = markerUserProfile;
    }


    private LatLng markerPos;
    private String markerUserName;
    private String markerUserProfile;


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
}
