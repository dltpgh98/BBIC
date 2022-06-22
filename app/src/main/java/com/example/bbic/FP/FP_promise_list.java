package com.example.bbic.FP;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bbic.Adapter.PromissListAdapter;
import com.example.bbic.Data.Promise;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FP_promise_list extends Fragment {

    ListView listView;
    private PromissListAdapter adapter;
    private List<Promise> promises;
    private List<Promise> userPromises;

    String getPromiss = null;
    long userKakaoCode = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_promise_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.promise_list_rv);
        promises = new ArrayList<Promise>();
        userPromises = new ArrayList<Promise>();
        adapter = new PromissListAdapter(getContext(), promises, userPromises, this);
        listView.setAdapter(adapter);

        if (getArguments() != null) {
            getPromiss = getArguments().getString("promiselist");
            userKakaoCode = getArguments().getLong("userCode");
            System.out.println("약속 : " + getPromiss);
            System.out.println("약속에서 유저코드" + userKakaoCode);
        }

        try {
            JSONObject jsonObject = new JSONObject(getPromiss);
            System.out.println("받은 약속" + getPromiss);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            System.out.println(jsonArray);
            int count = 0;

            int partyCode;
            String partyName;
            String promissTime;
            int partyStatus = 0;
            String partyStatuslist;
            long userCode;
            String friendCode;
            String friendName;
            String friendProfile;
            String promiseAddress;
            System.out.println("약속 배열 크기" + jsonArray.length());

            String userCodeString;
            int index = 0;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                partyCode = object.getInt("PP.P_code");
                partyName = object.getString("PP.P_name");
                promissTime = object.getString("PP.P_time");
                userCode = object.getLong("PP.K_code");
                promiseAddress = object.getString("PP.P_address");
                partyStatuslist = object.getString("P.P_status");
                friendCode = object.getString("K.K_code");
                friendName = object.getString("K.K_name");
                friendProfile = object.getString("K.K_profile");

                //System.out.println("파티코드" + partyCode);
                userCodeString = Long.toString(userKakaoCode);//약속의 친구 코드 리스트에서 사용자의 코드의 인덱스 를 얻기위함
                System.out.println("유저코드를 문자열로" + userCodeString);


                String[] arrayCode = friendCode.split(",");

                for (int i = 0; i < arrayCode.length; i++) {
                    System.out.println(arrayCode.length + "   "+arrayCode[i].toString());

                    if (arrayCode[i].toString().equals(userCodeString)) {
                        System.out.println("친구들의 코드들" + arrayCode[i].toString() + " " + userCodeString);
                        index = i;
                        System.out.println("인덱스 번호" + i);
                    }
                }

                String[] array = partyStatuslist.split(",");
                if (array[index].toString().equals("1")) {
                    partyStatus = Integer.parseInt(array[index].toString());
                    System.out.println("파티상태 확인 :" + partyStatus);
                }
                System.out.println("파티상태 확인 :" + partyStatus);
                Promise promise = new Promise(partyCode, partyName, promissTime, userCode, promiseAddress, partyStatus, friendCode, friendName, friendProfile);

                if(friendCode.contains(userCodeString)){
                    //System.out.println("파티에서 유저코드와 유저카카오코드 확인" + userCode + " "+userKakaoCode);
                    if(partyStatus == 1){
                        promises.add(promise);
                    }
                }
                count++;
                partyStatus = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }
}