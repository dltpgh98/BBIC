package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/update.php";
    private Map<String, String> map;

    public UpdateRequest(long k_code, String k_name, String k_email, String k_profile, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("K_code",k_code +"");
        map.put("K_name", k_name);
        map.put("K_email",k_email);
        map.put("K_profile", k_profile);

        System.out.println("실행은 하는지?");

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
