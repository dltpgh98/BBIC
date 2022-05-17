package com.example.bbic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Find_Way_Frag extends Fragment {

    private RecyclerView view_recyclerView;
    private ArrayList<Find_way_Data> fArrayList;
    private Find_way_listAdapter find_way_listAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String str;

    private int tabNum;

    private int onFoot_time;
    private int bus_time;
    private int sub_time;
    private int total_time;
    private String bus_num;
    private String sub_num;
    private int bus_iv;
    private int sub_iv;
    private int expansion_iv;

    private JSONArray sub_path;
    private JSONArray bus_path;
    private JSONArray sb_path;


//    private View rootView=null;

    Bundle bundle;

    private JSONObject object;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = (View) inflater.inflate(R.layout.find_way_list, container, false);


        view_recyclerView = (RecyclerView) rootView.findViewById(R.id.view_RecyclerView);
//        Log.d("리스트 선언부분","");
        linearLayoutManager = new LinearLayoutManager(getActivity());
//        Log.d("리니어메니저 선언 부분","");
        view_recyclerView.setLayoutManager(linearLayoutManager);
//        Log.d("레이아웃 매니저 설정","");
        fArrayList = new ArrayList<>();
        find_way_listAdapter = new Find_way_listAdapter(fArrayList);
//        Log.d("어뎁터 생성 부분","");
        view_recyclerView.setAdapter(find_way_listAdapter);
//        Log.d("뷰에 어뎁터 설정 부분","");
        Bundle b = getArguments();

        if (b != null) {
            str = b.getString("odsay");
//            Log.d("str",str);
            tabNum = 0;

            try {
                sub_path = new JSONArray();
                bus_path = new JSONArray();
                sb_path = new JSONArray();

                JSONObject result = new JSONObject(str);
                JSONArray path = result.getJSONArray("path");
                try {
                    if (result.getInt("searchType") == 0) {
                        for (int i = 0; i < path.length(); i++) {
                            try {
                                if (path.getJSONObject(i).getInt("pathType") == 3) { // 통합(버스+지하철)
                                    sb_path.put(path.getJSONObject(i));
//                                    Log.d("==============TEST====================", sub_path.getJSONObject(0) + "");
                                } else if (path.getJSONObject(i).getInt("pathType") == 2) {  // 버스
                                    bus_path.put(path.getJSONObject(i));
                                } else if (path.getJSONObject(i).getInt("pathType") == 1) { // 지하철
                                    sub_path.put(path.getJSONObject(i));
//                                    Log.d("==============TEST====================", sb_path.getJSONObject(0) + "");
                                }
                            } catch (Exception e) {

                            }
                        }

                    }
                    switch (tabNum) {
                        case 0:
                            for (int i = 0; i < sb_path.length(); i++) {
                                try {
                                    JSONArray subP = sb_path.getJSONObject(i).getJSONArray("subPath");
                                    Log.d("***********************trafficType*************************",subP.getJSONObject(0).getInt("trafficType")+"");
                                    total_time = sb_path.getJSONObject(i).getJSONObject("info").getInt("totalTime");
                                    Log.d("***********************Total*************************",total_time+"");
//                                    int[] onFoot=new int[4];
//                                    String[] busS= new String[3];
//                                    String[] subwayS= new String[3];

                                    ArrayList<Integer> onFoot = new ArrayList<>();
                                    ArrayList<String> busS = new ArrayList<>();
                                    ArrayList<String> subWayS = new ArrayList<>();
                                    StringBuilder aa= new StringBuilder();


                                    for (int j = 0; j < subP.length(); j++) {
                                        int traffic = subP.getJSONObject(j).getInt("trafficType");
                                        Log.d("*************trafficType************************",subP.getJSONObject(j).getInt("trafficType")+"");
                                        try{
                                            switch (subP.getJSONObject(j).getInt("trafficType")) {
                                                case 3:
                                                    onFoot_time = subP.getJSONObject(j).getInt("sectionTime");
                                                    onFoot.add(onFoot_time);
                                                    aa.append("도보:"+onFoot_time+"분 ");
//                                                    onFoot[j]=subP.getJSONObject(j).getInt("sectionTime");
                                                Log.d("**********=======================**도보************************",onFoot_time+"");
                                                    break;
                                                case 2:
                                                    bus_time = subP.getJSONObject(j).getInt("sectionTime");
//                                                Log.d("***************************bus**********************", subP.getJSONObject(j)+"");
//                                                    Log.d("***************************bus**********************", subP.getJSONObject(j).getInt("trafficType") + "");
                                                    Log.d("***************************busTime**********************",bus_time+"");
                                                    bus_num = subP.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("busNo");
                                                    Log.d("***************************busNum**********************", bus_num);
                                                    busS.add(bus_num);
                                                    aa.append("bus:"+bus_num+"번 ");
//                                                    busS[j]()

//                                                    Log.d("***************************busNum**********************", subP.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("busNo") + "");
                                                    break;
                                                case 1:
                                                    sub_time = subP.getJSONObject(j).getInt("sectionTime");
                                                    Log.d("***************************subTime**********************", sub_time+"");
                                                    sub_num = subP.getJSONObject(j).getJSONArray("lane").getJSONObject(0).getString("name");
                                                    subWayS.add(sub_num);
                                                    Log.d("***************************subNum**********************", sub_num);
                                                    aa.append(sub_num+" ");
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }catch (Exception e){

                                        }

                                    }

//                                    JSONArray subPath = path.getJSONObject(i).getJSONArray("sub_path");
//                                    onFoot_time = subPath.getJSONObject(0).getInt("sectionTime");
//                                    bus_time = subPath.getJSONObject();
//
//                                    sub_time;
//                                    total_time;
//                                    bus_num;
//                                    sub_num;
//                                    bus_iv;
//                                    sub_iv;
//                                    expansion_iv;

                                    aa.append("총 시간:"+total_time+"분");
                                    Find_way_Data fwData = new Find_way_Data(onFoot_time, bus_num, bus_time, sub_num, sub_time,total_time);
//                                    Find_way_Data fwData = new Find_way_Data(aa);
                                    fArrayList.add(fwData);
                                    find_way_listAdapter.notifyDataSetChanged();
                                    Log.d("=========********한개 배열 끝*******=========","");
                                    Log.d("=========********한개 배열 끝*******=========",aa+"");
                                } catch (Exception e) {

                                }

                            }
                            break;

                        default:
                            break;
                    }
                } catch (Exception e) {

                }
//                Log.d("===Find_Way_Frag=====JsonObject======================", result.getJSONArray("path") + "");

            } catch (JSONException e) {
                Log.d("실패============================", "");
                e.printStackTrace();
            }

//            Find_way_Data fwData = new Find_way_Data(9, R.color.yellow, 24, R.color.black, 24, 56);
//            fArrayList.add(fwData);
//            find_way_listAdapter.notifyDataSetChanged();
            try {
                object = new JSONObject(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//
//        Find_way_Data fwData = new Find_way_Data(9, R.color.yellow, 24, R.color.black, 24, 56, R.color.red);
//        fArrayList.add(fwData);
//        find_way_listAdapter.notifyDataSetChanged();

//
//            Find_way_Data way_data = new Find_way_Data();
        return rootView;
    }

}