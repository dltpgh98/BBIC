package com.example.bbic.Data;

public class Promise {

    int partyCode;
    String partyName;
    String promissTime;
    int partyStatus;
    long userCode;
    String friendCode;
    String friendName;
    String friendProfile;
    String promiseAddress;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getPromiseAddress() {
        return promiseAddress;
    }

    public void setPromiseAddress(String promiseAddress) {
        this.promiseAddress = promiseAddress;
    }

    public int getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(int partyCode) {
        this.partyCode = partyCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPromissTime() {
        return promissTime;
    }

    public void setPromissTime(String promissTime) {
        this.promissTime = promissTime;
    }

    public int getPartyStatus() {
        return partyStatus;
    }

    public void setPartyStatus(int partyStatus) {
        this.partyStatus = partyStatus;
    }

    public long getUserCode() {
        return userCode;
    }

    public void setUserCode(long userCode) {
        this.userCode = userCode;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }

    public String getFriendProfile() {
        return friendProfile;
    }

    public void setFriendProfile(String friendProfile) {
        this.friendProfile = friendProfile;
    }

    public Promise(int partyCode, String partyName, String promissTime, long userCode, String promiseAddress, int partyStatus, String friendCode, String friendName, String friendProfile) {
        this.partyCode = partyCode;
        this.partyName = partyName;
        this.promissTime = promissTime;
        this.partyStatus = partyStatus;
        this.userCode = userCode;
        this.friendCode = friendCode;
        this.friendName = friendName;
        this.friendProfile = friendProfile;
        this.promiseAddress = promiseAddress;
    }

}
