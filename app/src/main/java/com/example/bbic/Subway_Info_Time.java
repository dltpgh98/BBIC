package com.example.bbic;

import android.os.Looper;
import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONObject;

public class Subway_Info_Time extends Maps_Activity{

    private JSONObject result;



    public OnResultCallbackListener subway_timeList = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try{
              if(api == API.SUBWAY_TIME_TABLE){
                  result = odsayData.getJson().getJSONObject("result");
                  Log.d("==================result=========왜 안되냐고======",result+"");
              }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onError(int i, String s, API api) {
            if (api == API.SUBWAY_TIME_TABLE) {}
        }
    };

}
