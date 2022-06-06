package com.example.bbic.FindWay;

import android.util.Log;

import com.example.bbic.Maps_Activity;
import com.naver.maps.geometry.LatLng;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Map_Find_way extends Maps_Activity {

    private JSONArray l_graPos;

    private JSONObject result;

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    private JSONArray[] path_s = new JSONArray[3];

    public JSONArray[] getPath_s() {
        return path_s;
    }

    public void setPath_s(JSONArray[] path_s) {
        this.path_s = path_s;
    }

    public JSONArray getL_graPos() {
        return l_graPos;
    }

    public void setL_graPos(JSONArray l_graPos) {
        this.l_graPos = l_graPos;
    }

    public OnResultCallbackListener Find_way = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try{
                if(api == API.SEARCH_PUB_TRANS_PATH){
//                    int name = odsayData.getJson().getJSONObject("result").getJSONArray("path").length();
//                    Log.d("Station name : ", name+"");
//                    Log.d("Station name : ", odsayData.getJson().getJSONObject("result")+"");
//                    subWay_path = new JSONArray();
//                    bus_path = new JSONArray();
//                    sb_path = new JSONArray();

                    result = odsayData.getJson().getJSONObject("result");

                    JSONArray path = odsayData.getJson().getJSONObject("result").getJSONArray("path");
//                    Log.d("Path",path+"");
//                    try{
//                        if(odsayData.getJson().getJSONObject("result").getInt("searchType") == 0){
//                            for(int i=0; i < path.length() ; i++){
//                                try{
////                                Log.d("aaaaa",path.getJSONObject(i).getJSONObject("info")+"");
////                                Log.d("aaaaa",path.getJSONObject(i).getInt("pathType")+"");
//                                    if(path.getJSONObject(i).getInt("pathType") == 3){ // 통합(버스+지하철)
//                                        sb_path.put(path.getJSONObject(i));
////                                    Log.d("sub_ =======",i+""+path.getJSONObject(i)+"");
//                                    }
//                                    else if(path.getJSONObject(i).getInt("pathType") == 2){  // 버스
//                                        bus_path.put(path.getJSONObject(i));
//                                    }
//                                    else if(path.getJSONObject(i).getInt("pathType") == 1){ // 지하철
//                                        subWay_path.put(path.getJSONObject(i));
//                                    }
//                                }catch (Exception e){
//
//                                }

//                            int pathType = path.getJSONObject(i).getInt("pathType");
//                            Log.d("pathType (1-지하철,2-버스,3-버스+지하철)결과종류 = ",pathType+"");
//                            JSONArray s_Path = path.getJSONObject(i).getJSONArray("subPath"); //도착지까지의 이동 수단들의 정보
//                            //                    Log.d("s_Path",s_Path+"");
//                            JSONObject p_info = path.getJSONObject(i).getJSONObject("info"); //간단하게 표시할때 필요한 내용들
//                            String i_mapObj =  p_info.getString("mapObj"); //보간점 좌표를 위한
//                            Log.d("mapObj",i_mapObj);
//                            JSONArray subPath = path.getJSONObject(i).getJSONArray("subPath");
//                            for(int s_len=0 ; s_len < subPath.length(); s_len++){
//                                try{
//                                    if(subPath.getJSONObject(s_len).getInt("trafficType")==1){//지하철
//                                        JSONObject subArray = subPath.getJSONObject(s_len);
//                                        String sb_S_Name = subArray.getString("startName"); // 시작지 이름
//                                        String sb_E_Name = subArray.getString("endName");
//                                        JSONArray sub_Len = subArray.getJSONArray("lane");
//                                        Log.d("sub_Len",sub_Len+"");
//                                        String sb_W_code = subArray.getString("wayCode");
//                                        Log.d("========trafficType1 ============================","");
//                                    }
//                                    else if(subPath.getJSONObject(s_len).getInt("trafficType")==2) {
//
//                                        int traffic = subPath.getJSONObject(s_len).getInt("trafficType");
//                                        String st_Name = subPath.getJSONObject(s_len).getString("startName");
//                                        //시작 역 또는 정류장 이름
//                                        Log.d("stTest_Name:", st_Name + " // traffic (1-지하처,2-버스,3-도보): " + traffic);
//                                        Double st_X = subPath.getJSONObject(s_len).getDouble("startX");
//                                        Double st_Y = subPath.getJSONObject(s_len).getDouble("startY");
//                                        Log.d("st_Name : ", st_Name + " / st_X : " + st_X + " / st_Y : " + st_Y);
//                                        Log.d("------------------------------------------", "");
////                                JSONArray passList = subPath.getJSONObject(s_len).getJSONArray("passStopList");
////                                for(int pass = 0; pass < passList.length();pass++){
////                                    String st_Name = passList.getJSONObject(pass).getString("startName");
////                                    Double st_X = passList.getJSONObject(pass).getDouble("startX");
////                                    Double st_Y = passList.getJSONObject(pass).getDouble("startY");
////                                }
//                                        Log.d("========trafficType2 ============================","");
//                                    }
//                                    else if(subPath.getJSONObject(s_len).getInt("trafficType") == 3){
//                                        Log.d("trafficType 3",""+subPath.getJSONObject(s_len));
//                                    }
//                                }catch (Exception e){
//
//                                }
//                    }
//                            }
//                            Log.d("subPath_length",sub_path.length()+"");
//                            Log.d("bus_length",bus_path.length()+"");
//                            Log.d("sb_length",sb_path.length()+"");
//                            Log.d("subPath",sub_path+"");
//                            Log.d("bus_Path",bus_path+"");
//                            Log.d("sb_Path",sb_path+"");
//                            Log.d("=============================================","");
//                            path_s[0]= subWay_path;
//                            path_s[1]=bus_path;
//                            path_s[2]=sb_path;

//                            Log.d("걸어서 가는 시간",+"");
//                            Log.d("Path_s",path_s[0]+"");
//                            Log.d("==========================Path_s TEST=========================",""+path_s[0].getJSONObject(0).getJSONArray("subPath"));
//                            Log.d("sub_path TEST=========================",""+sub_path.getJSONArray(0));


//                            view_recyclerView = (RecyclerView) findViewById(R.id.view_RecyclerView);
//                            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                            view_recyclerView.setLayoutManager(linearLayoutManager);
//
//                            fArrayList = new ArrayList<>();
//                            find_way_listAdapter = new Find_way_listAdapter(fArrayList);
//                            view_recyclerView.setAdapter(find_way_listAdapter);

//                            if(path_s[2]!=null){
//                                for (int i = 0; i < path_s.length; i++){
////                                    Find_way_Data find_way_data = new Find_way_Data(path_s[2].getJSONArray(i),path_s[2].getJSONArray())
//                                }
//                            }

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
//                        }
//                    }catch (Exception e){
//
//                    }

//                    Log.d("Path_s======밖에서==========",path_s[0]+"");
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.d("Path_s===========저 밖에서============",path_s[0]+"");
        }
        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.SEARCH_PUB_TRANS_PATH) {}
        }

    };

    double[] gPos_x;
    double[] gPos_y;
    int posCount;

    private ArrayList<LatLng> findWay_LatLngArrayList;
    private LatLng findWay_endPosLatLng;

    public ArrayList<LatLng> getFindWay_LatLngArrayList() {
        return findWay_LatLngArrayList;
    }

    public LatLng getFindWay_endPosLatLng() {
        return findWay_endPosLatLng;
    }

    public void setFindWay_endPosLatLng(LatLng findWay_endPosLatLng) {
        this.findWay_endPosLatLng = findWay_endPosLatLng;
    }

    public void setFindWay_LatLngArrayList(ArrayList<LatLng> findWay_LatLngArrayList) {
        this.findWay_LatLngArrayList = findWay_LatLngArrayList;

    }

    public double[] getgPos_x() {
        return gPos_x;
    }

    public void setgPos_x(double[] gPos_x) {
        this.gPos_x = gPos_x;
    }

    public double[] getgPos_y() {
        return gPos_y;
    }

    public void setgPos_y(double[] gPos_y) {
        this.gPos_y = gPos_y;
    }

    public int getPosCount() {
        return posCount;
    }

    public void setPosCount(int posCount) {
        this.posCount = posCount;
    }

    public OnResultCallbackListener LoadLane = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try{
                if(api == API.LOAD_LANE){
                    JSONObject l_result = odsayData.getJson().getJSONObject("result");
                    JSONArray laneArray = l_result.getJSONArray("lane");
                    LatLng l_pos;
                    findWay_LatLngArrayList = new ArrayList<>();
                    for (int i = 0; i < laneArray.length(); i++){
                        JSONArray l_sectionArray = laneArray.getJSONObject(i).getJSONArray("section");
//                        Log.d("l_sectionArray",l_sectionArray+"");
//                        Log.d("l_sectionArray",l_sectionArray.length()+"");
                        for (int j = 0; j < l_sectionArray.length(); j++){
                            l_graPos = l_sectionArray.getJSONObject(j).getJSONArray("graphPos");
//                            Log.d("graPos test",l_graPos+"");
                            posCount = l_graPos.length();
//                            gPos_x = new double[l_graPos.length()];
//                            gPos_y = new double[l_graPos.length()];


                            for(int length = 0; length < l_graPos.length();length++){
                                l_pos = new LatLng( l_graPos.getJSONObject(length).getDouble("y"),l_graPos.getJSONObject(length).getDouble("x"));
//                                Log.d("l_graPos.length()=====================",""+l_graPos.length());
                                findWay_LatLngArrayList.add(l_pos);
//                                gPos_x[length] = l_graPos.getJSONObject(length).getDouble("x");
//                                gPos_y[length] = l_graPos.getJSONObject(length).getDouble("y");

//                                Log.d("graPos test x:",gPos_x[length]+"y:"+gPos_y[length]);

//                                Log.d("count=============",""+posCount);
//                                Log.d("Length()=============",""+length);
                            }
                            findWay_endPosLatLng = new LatLng( l_graPos.getJSONObject(posCount-1).getDouble("y"),l_graPos.getJSONObject(posCount-1).getDouble("x"));
//                            Log.d("endLatLng=============",""+findWay_endPosLatLng);
//                            Log.d("findWayLatLng=============",""+findWay_LatLngArrayList);
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
