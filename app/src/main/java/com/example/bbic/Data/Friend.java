package com.example.bbic.Data;

public class Friend {

    long userKakapCode;
    long friendKakaoCode;
    int friendStatus;
    String friendName;
    String friendEmail;
    String friendProfileURL;
    int friendGhost;
    double friendlong;
    double friendLat;
    String userProfile;
    String userName;
    int userGhost;


    public long getUserKakapCode() {
        return userKakapCode;
    }

    public void setUserKakapCode(long userKakapCode) {
        this.userKakapCode = userKakapCode;
    }

    public long getFriendKakaoCode() {
        return friendKakaoCode;
    }

    public void setFriendKakaoCode(long friendKakaoCode) {
        this.friendKakaoCode = friendKakaoCode;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getFriendProfileURL() {
        return friendProfileURL;
    }

    public void setFriendProfileURL(String friendProfileURL) {
        this.friendProfileURL = friendProfileURL;
    }

    public int getFriendGhost() {
        return friendGhost;
    }

    public void setFriendGhost(int friendGhost) {
        this.friendGhost = friendGhost;
    }

    public double getFriendlong() {
        return friendlong;
    }

    public void setFriendlong(double friendlong) {
        this.friendlong = friendlong;
    }

    public double getFriendLat() {
        return friendLat;
    }

    public void setFriendLat(double friendLat) {
        this.friendLat = friendLat;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserGhost() {
        return userGhost;
    }

    public void setUserGhost(int userGhost) {
        this.userGhost = userGhost;
    }

    public Friend(long userKakapCode, long friendKakaoCode, int friendStatus, String friendName, String friendEmail, String friendProfileURL, int friendGhost, double friendlong, double friendLat, String userProfile, String userName, int userGhost) {
        this.userKakapCode = userKakapCode;
        this.friendKakaoCode = friendKakaoCode;
        this.friendStatus = friendStatus;
        this.friendName = friendName;
        this.friendEmail = friendEmail;
        this.friendProfileURL = friendProfileURL;
        this.friendGhost = friendGhost;
        this.friendlong = friendlong;
        this.friendLat = friendLat;
        this.userProfile = userProfile;
        this.userName = userName;
        this.userGhost = userGhost;
    }
}