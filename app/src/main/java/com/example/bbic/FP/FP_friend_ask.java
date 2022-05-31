package com.example.bbic.FP;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bbic.Adapter.FriendAskAdapter;
import com.example.bbic.Adapter.FriendListAdapter;
import com.example.bbic.Data.Friend;
import com.example.bbic.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FP_friend_ask extends Fragment {

    private ListView listView;
    private FriendAskAdapter adapter;
    private List<Friend> friendList;
    private List<Friend> userFriendlist;
    long userKakaoCode = 0;
    private String str;
    FP_friend fp_friend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_ask, container, false);

        listView = (ListView) rootView.findViewById(R.id.friend_ask_lv);
        friendList = new ArrayList<Friend>();
        userFriendlist = new ArrayList<Friend>();


        adapter = new FriendAskAdapter(getContext(), friendList, userFriendlist, this);
        listView.setAdapter(adapter);

        String getFriend = null;
        long userKakaoCode = 0;

        if (getArguments() != null) {
            getFriend = getArguments().getString("friendlist");
            userKakaoCode = getArguments().getLong("userCode");
            System.out.println("friendask 친구 목록 확인 : " + getFriend);
            System.out.println("유저의 카카오코드 확인" + userKakaoCode);
        }


//        new BackgroundTask().execute();

        try {
            JSONObject jsonObject = new JSONObject(getFriend);
            System.out.println(getFriend);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            long userCode;
            long friendCode;
            int friendStatus;
            String friendName;
            String friendEmail;
            String friendProfile;
            double friendLong;
            double friendLat;
            int friendGhost;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userCode = object.getLong("F.K_code1");
                friendCode = object.getLong("F.K_code2");
                friendStatus = object.getInt("F.F_status");
                friendName = object.getString("K.K_name");
                friendEmail = object.getString("K.K_email");
                friendProfile = object.getString("K.K_profile");
                friendLong = object.getDouble("K.K_long");
                friendLat = object.getDouble("K.K_lat");
                friendGhost = object.getInt("K.K_ghost");

                Friend friend = new Friend(userCode, friendCode, friendStatus, friendName, friendEmail, friendProfile, friendGhost, friendLong, friendLat);
                if (userCode == userKakaoCode) {
                    if (friendStatus == 0) {
                        friendList.add(friend);
                    }
                }
//                friendList.add(friend); //원본용
//                userFriendlist.add(friend);//코드 검색용
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();

        return rootView;
    }

//    class BackgroundTask extends AsyncTask<Void, Void, String> {
//
//        String target;
//
//        @Override
//        protected void onPreExecute() {
//            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/friendlist.php";
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            try{
//                URL url = new URL(target);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String temp;
//
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while((temp = bufferedReader.readLine()) != null){
//                    stringBuilder.append(temp + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                System.out.println(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("response");
//                int count = 0;
//
//                long userCode;
//                long friendCode;
//                int friendStatus;
//                String friendName;
//                String friendEmail;
//                String friendProfile;
//                double friendLong;
//                double friendLat;
//                int friendGhost;
//
//                while (count < jsonArray.length()) {
//                    JSONObject object = jsonArray.getJSONObject(count);
//                    userCode = object.getLong("F.K_code1");
//                    friendCode = object.getLong("F.K_code2");
//                    friendStatus = object.getInt("F.F_status");
//                    friendName = object.getString("K.K_name");
//                    friendEmail = object.getString("K.K_email");
//                    friendProfile = object.getString("K.K_profile");
//                    friendLong = object.getDouble("K.K_long");
//                    friendLat = object.getDouble("K.K_lat");
//                    friendGhost = object.getInt("K.K_ghost");
//
//                    Friend friend = new Friend(userCode, friendCode, friendStatus, friendName, friendEmail, friendProfile, friendGhost, friendLong, friendLat);
//                    if(userCode == userKakaoCode){
//                        if(friendStatus == 0){
//                            friendList.add(friend);
//                        }
//                    }
////                friendList.add(friend); //원본용
////                userFriendlist.add(friend);//코드 검색용
//                    count++;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void onCancelled(String s) {
//            super.onCancelled(s);
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//
//    }
}