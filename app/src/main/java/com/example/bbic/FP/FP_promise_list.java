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
            int partyStatus;
            long userCode;
            String friendCode;
            String friendName;
            String friendProfile;
            String promiseAddress;
            System.out.println("약속 배열 크기" + jsonArray.length());

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                partyCode = object.getInt("PP.P_code");
                partyName = object.getString("PP.P_name");
                promissTime = object.getString("PP.P_time");
                userCode = object.getLong("PP.K_code");
                promiseAddress = object.getString("PP.P_address");
                partyStatus = object.getInt("P.P_status");
                friendCode = object.getString("K.K_code");
                friendName = object.getString("K.K_name");
                friendProfile = object.getString("K.K_profile");

                //System.out.println("파티코드" + partyCode);


                Promise promise = new Promise(partyCode, partyName, promissTime, userCode, promiseAddress, partyStatus, friendCode, friendName, friendProfile);
                if(userCode == userKakaoCode){
                    System.out.println("파티에서 유저코드와 유저카카오코드 확인" + userCode + " "+userKakaoCode);
                    if(partyStatus == 1){
                        promises.add(promise);
                    }
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }
}
