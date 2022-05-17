package com.example.bbic.DB;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://ec2-3-85-238-108.compute-1.amazonaws.com/validate.php";
    private Map<String, String> map;

    public ValidateRequest(long k_code, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("K_code", k_code + "");

        System.out.println("중복 확인 리퀘스트?");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
