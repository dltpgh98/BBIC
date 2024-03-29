package com.example.bbic.DB;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateGhostRequest extends StringRequest{

    final static private String URL = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/updateghost.php";
    private Map<String, String> map;

    public UpdateGhostRequest(long K_code, int K_ghost ,int P_location, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("K_code",K_code +"");
        map.put("K_ghost", K_ghost + "");
        map.put("P_location", P_location + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
