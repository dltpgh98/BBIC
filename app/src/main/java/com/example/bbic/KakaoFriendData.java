package com.example.bbic;

public class KakaoFriendData {

    Long userId; //회원번호
    String uuid; // 친구마다 고유한 참고용 코드 (카카오톡 메시지 전송 시 필요)
    String profileNickname; // 친구 프로필 닉네임 HTTP만 지원
    String profileThumbnailImage; // 친구 프로필 썸네일 URL


    public KakaoFriendData(Long userId, String uuid, String profileNickname, String profileThumbnailImage) {
        this.userId = userId;
        this.uuid = uuid;
        this.profileNickname = profileNickname;
        this.profileThumbnailImage = profileThumbnailImage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProfileNickname() {
        return profileNickname;
    }

    public void setProfileNickname(String profileNickname) {
        this.profileNickname = profileNickname;
    }

    public String getProfileThumbnailImage() {
        return profileThumbnailImage;
    }

    public void setProfileThumbnailImage(String profileThumbnailImage) {
        this.profileThumbnailImage = profileThumbnailImage;
    }
}
