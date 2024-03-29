package com.example.bbic

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.talk.model.Friend

class test : AppCompatActivity() {

    private val friendList = mutableListOf<KakaoFriend>();

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        setContentView(R.layout.test)
//    }

    public fun test1(){

        TalkApiClient.instance.friends { friends, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡 친구 목록 가져오기 실패", error)
            }
            else if (friends != null) {
                Log.i(TAG, "카카오톡 친구 목록 가져오기 성공 \n${friends.elements?.joinToString("/n")}")
                //print("친구" +friends);
                Log.i(TAG, "카카오톡 친구 목록 \n${friends}")

                Log.d("코틀린","카카오톡 친구 수 = ${friends.totalCount}");

                for(i in 0 until friends.totalCount){
                    var friend_id = friends.elements?.get(i)?.id
                    var friend_Nickname = friends.elements?.get(i)?.profileNickname
                    var friend_Image = friends.elements?.get(i)?.profileThumbnailImage
                    var friend_uuid = friends.elements?.get(i)?.uuid

                    println("친구 아이디$friend_id")

                    var kakaFriend = KakaoFriend(friend_id, friend_Nickname, friend_Image, friend_uuid)

                    Log.d("코틀린","가져온 친구 = $kakaFriend");

                    friendList.add(kakaFriend);

                    //array = Array<KakaoFriend?>(result){null}
//                    for(x in 0 until result){
//                        val id = friend_id
//                        val Nikname = friend_Nickname
//                        val Image = friend_Image
//                        val uuid = friend_uuid
//
//                        val kakaFriend = KakaoFriend(id, Nikname, Image, uuid)
//
//                        array[x] = kakaFriend
//                    }
                }
                // 친구의 UUID 로 메시지 보내기 가능
            }
        }
        /*println("리턴 전 카카오톡 친구 배열${friendList.size}")*/
    }

    public fun getFriendList(): List<KakaoFriend?> {

        return friendList;
    }
}