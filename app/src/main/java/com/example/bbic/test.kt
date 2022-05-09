package com.example.bbic

import android.content.ContentValues.TAG
import android.util.Log
import com.kakao.sdk.talk.TalkApiClient

class test {

    public fun test1() {
        TalkApiClient.instance.friends { friends, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡 친구 목록 가져오기 실패", error)
            }
            else if (friends != null) {
                Log.i(TAG, "카카오톡 친구 목록 가져오기 성공 \n${friends.elements?.joinToString("/n")}")

                // 친구의 UUID 로 메시지 보내기 가능
            }
        }
    }


}