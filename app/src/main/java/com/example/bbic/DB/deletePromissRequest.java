package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class deletePromissRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/deletepromiss.php";
    private Map<String, String> map;

    public deletePromissRequest(int p_code, long k_code, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("P_code",p_code +"");
        map.put("K_code",k_code +"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
