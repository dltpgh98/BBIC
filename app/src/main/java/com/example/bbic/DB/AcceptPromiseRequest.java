package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AcceptPromiseRequest extends StringRequest{//약속 추가(파티 수락)

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/promiseaccept.php";
    private Map<String, String> map;

    public AcceptPromiseRequest(int P_code, long K_code, int P_location, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("P_code",P_code + "");
        map.put("K_code",K_code + "");
        


        System.out.println("버튼 클릭 리스너");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}