package com.example.bbic;

public class Menu_Item {

    private long friendCode;
    private String friendName;
    private String friendProfile;

    public long getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(long friendCode) {
        this.friendCode = friendCode;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendProfile() {
        return friendProfile;
    }

    public void setFriendProfile(String friendProfile) {
        this.friendProfile = friendProfile;
    }

    public Menu_Item(long friendCode, String friendName, String friendProfile) {
        this.friendCode = friendCode;
        this.friendName = friendName;
        this.friendProfile = friendProfile;
    }
}
