package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/update.php";
    private Map<String, String> map;

    public UpdateRequest(long k_code, String k_name, String k_email, String k_profile, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("k_code",k_code +"");
        map.put("k_name", k_name);
        map.put("k_email",k_email);
        map.put("k_profile", k_profile);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
