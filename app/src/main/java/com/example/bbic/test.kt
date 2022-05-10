package com.example.bbic

import android.content.ContentValues.TAG
import android.util.Log
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.talk.model.Friend

class test {
    var result : Int = 0;
    var array = Array<KakaoFriend?>(result){null}


    public fun test1() {
        TalkApiClient.instance.friends { friends, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡 친구 목록 가져오기 실패", error)
            }
            else if (friends != null) {
                Log.i(TAG, "카카오톡 친구 목록 가져오기 성공 \n${friends.elements?.joinToString("/n")}")
                //print("친구" +friends);
                Log.i(TAG, "카카오톡 친구 목록 \n${friends}")
                result = friends.totalCount;

                for(i in 0 until result){
                    val friend_id = friends.elements?.get(i)?.id
                    val friend_Nickname = friends.elements?.get(i)?.profileNickname
                    val friend_Image = friends.elements?.get(i)?.profileThumbnailImage
                    val friend_uuid = friends.elements?.get(i)?.uuid

                    println("친구 아이디$friend_id")

                    array = Array<KakaoFriend?>(result){null}
                    for(x in 0 until result){
                        val id = friend_id
                        val Nikname = friend_Nickname
                        val Image = friend_Image
                        val uuid = friend_uuid

                        val kakaFriend = KakaoFriend(id, Nikname, Image, uuid)

                        array[x] = kakaFriend
                    }
                }

                // 친구의 UUID 로 메시지 보내기 가능
            }
        }
    }


}