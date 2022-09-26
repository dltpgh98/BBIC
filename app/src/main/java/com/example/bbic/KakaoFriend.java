package com.example.bbic;

import java.io.Serializable;

public class KakaoFriend implements Serializable {

    Long friend_id;
    String friend_Nickname;
    String friend_Image;
    String friend_uuid;

    public KakaoFriend(Long friend_id, String friend_Nickname, String friend_Image, String friend_uuid) {
        this.friend_id = friend_id;
        this.friend_Nickname = friend_Nickname;
        this.friend_Image = friend_Image;
        this.friend_uuid = friend_uuid;
    }

    public Long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(Long friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_Nickname() {
        return friend_Nickname;
    }

    public void setFriend_Nickname(String friend_Nickname) {
        this.friend_Nickname = friend_Nickname;
    }

    public String getFriend_Image() {
        return friend_Image;
    }

    public void setFriend_Image(String friend_Image) {
        this.friend_Image = friend_Image;
    }

    public String getFriend_uuid() {
        return friend_uuid;
    }

    public void setFriend_uuid(String friend_uuid) {
        this.friend_uuid = friend_uuid;
    }
}
