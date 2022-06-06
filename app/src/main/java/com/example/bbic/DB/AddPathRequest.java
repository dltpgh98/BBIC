package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddPathRequest extends StringRequest{

    final static private String URL = "http://3.85.238.108/path.php";
    private Map<String, String> map;

    public AddPathRequest(long k_code, String p_origin, String p_destination, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("K_code",k_code +"");
        map.put("P_origin", p_origin);
        map.put("P_destination",p_destination);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
