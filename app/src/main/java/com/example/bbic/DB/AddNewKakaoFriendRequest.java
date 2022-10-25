package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddNewKakaoFriendRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/newkakaofriendadd.php";
    private Map<String, String> map;

    public AddNewKakaoFriendRequest(long K_code1, long K_code2, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();

        map.put("K_code1",K_code1 + "");
        map.put("K_code2",K_code2 + "");
        map.put("F_status",0 + "");

        //K_code1 = 자신의 카카오 코드
        //K_code2 = 친구의 카카오 코드
        //F)status = 0은 친구 요청 중, 1은 친구 상태

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}