package com.example.bbic.DB;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/validate.php";
    private Map<String, String> map;

    public ValidateRequest(long K_code, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("K_code", K_code + "");

        System.out.println(K_code);
        System.out.println("중복 확인 리퀘스트?");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
