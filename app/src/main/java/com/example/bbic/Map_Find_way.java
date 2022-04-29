package com.example.bbic;

import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Map_Find_way extends Maps_Activity{

    public OnResultCallbackListener Find_way = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try{
                if(api == API.SEARCH_PUB_TRANS_PATH){
                    int name = odsayData.getJson().getJSONObject("result").getJSONArray("path").length();
                    Log.d("Station name : ", name+"");

                    JSONArray path = odsayData.getJson().getJSONObject("result").getJSONArray("path");
                    Log.d("Path",path+"");
                    for(int i=0; i < path.length();i++){
                        JSONArray s_Path = path.getJSONObject(i).getJSONArray("subPath");
                        Log.d("s_Path",s_Path+"");
                        JSONObject p_info = path.getJSONObject(i).getJSONObject("info");
                        String i_mapObj =  p_info.getString("mapObj");
                        Log.d("mapObj",i_mapObj);
                        JSONArray subPath = path.getJSONObject(i).getJSONArray("subPath");
                        for(int s_len=0 ; s_len < subPath.length(); s_len++){
                            try{
                              String st_Name = subPath.getJSONObject(s_len).getString("startName");
                              Double st_X = subPath.getJSONObject(s_len).getDouble("startX");
                              Double st_Y = subPath.getJSONObject(s_len).getDouble("startY");
                                Log.d("st_Name :",st_Name+"st_X : "+st_X+" st_Y : "+st_Y);
                            }catch (Exception e){

                            }
                        }

//                        JSONObject s_path = path.getJSONObject(i);
//                        String mapObj = s_path.getJSONObject("info").getString("mapObj");
//                        Log.d("mapObj",mapObj);
//                        JSONArray s_Path =  path.getJSONObject(i).getJSONArray("subPath");
//                        Log.d("S_Path",s_Path+"");


//                        int mapInfo = path.getJSONObject(i).getJSONObject("info").length();
//                        //String mapObj =  path.getJSONObject(i).getJSONObject("info").getString("mapObj");;
//                        Log.d("Station mapobj : //// ", mapObj+"");
//                        for(int subPath = 0;subPath < path.getJSONObject(i).getJSONArray("subPath").length();subPath++){
//                            String mapsub_sX = path.getJSONObject(i).getJSONArray("subPath").getJSONObject(subPath).getString("trafficType");
////                                    String mapsub_sY = jsonArray.getJSONObject(i).getJSONArray("subPath").getJSONObject(subPath).getString("startY");
//                            Log.d("Station traffic : ", mapsub_sX+"//");
//                            Log.d("Station subPath : ", subPath+"//");
//                        }
//                        try{
//                            String mapsub_sY = path.getJSONObject(i).getJSONArray("subPath").getJSONObject(1).getString("startName");
//                            Log.d("Station name",mapsub_sY+"");
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {}
        }

    };

    public OnResultCallbackListener LoadLane = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try{
                if(api == API.LOAD_LANE){
    //                int name = odsayData.getJson().getJSONObject("result").getJSONArray("path").length();
       //             Log.d("Station name : ", name+"");

                    JSONObject l_result = odsayData.getJson().getJSONObject("result");
                    JSONArray laneArray = l_result.getJSONArray("lane");
                    for (int i = 0; i < laneArray.length(); i++){
                                JSONArray l_sectionArray = laneArray.getJSONObject(i).getJSONArray("section");
                                Log.d("l_sectionArray",l_sectionArray+"");
                                Log.d("l_sectionArray",l_sectionArray.length()+"");
                                for (int j = 0; j < l_sectionArray.length(); j++){
                                            JSONArray l_graPos = l_sectionArray.getJSONObject(j).getJSONArray("graphPos");
                                            Log.d("graPos test",l_graPos+"");
                                            for(int length = 0; length < l_graPos.length();length++){
                                                double gPos_x = l_graPos.getJSONObject(length).getDouble("x");
                                                double gPos_y = l_graPos.getJSONObject(length).getDouble("y");
                                                Log.d("graPos test x:",gPos_x+"y:"+gPos_y);
                                            }
                                }


                    }

//                    JSONArray path = odsayData.getJson().getJSONObject("result").getJSONArray("path");
//                    Log.d("Path",path+"");
//                    for(int i=0; i < path.length();i++){
//                        JSONArray s_Path = path.getJSONObject(i).getJSONArray("subPath");
//                        Log.d("s_Path",s_Path+"");
//                        JSONObject p_info = path.getJSONObject(i);
//                        String i_mapObj =  p_info.getString("mapObj");
//                        Log.d("mapObj",i_mapObj);
//
//                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.LOAD_LANE) {}
        }

    };
}
