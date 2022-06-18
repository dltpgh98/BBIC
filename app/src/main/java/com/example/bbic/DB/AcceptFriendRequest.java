package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AcceptFriendRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/friendaccept.php";
    private Map<String, String> map;

    public AcceptFriendRequest(long K_code1, long K_code2, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("K_code1",K_code1 +"");// 유저코드
        map.put("K_code2", K_code2 + "");// 친구 코드


        System.out.println("버튼 클릭 리스너");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}