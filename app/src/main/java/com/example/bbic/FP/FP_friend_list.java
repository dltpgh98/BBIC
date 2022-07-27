package com.example.bbic.FP;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.Adapter.FriendRecyclerAdapter;
import com.example.bbic.Data.Friend;
import com.example.bbic.R;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FP_friend_list extends Fragment {
    FP_friend_ask fp_friend_ask;

    private FriendRecyclerAdapter adapter;
    private List<Friend> friendList;
    private List<Friend> userFriendlist;
    Bundle bundleask; // 프래그먼트 간의 데이터 전달 시 필요

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fp_friend_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.friend_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new FriendRecyclerAdapter();
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    private void ThreadProc() {
        new Thread() {
            @Override
            public void run() {
                //super.run();
                String str, receiveMsg = "";

                String urlStr = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/friendlist.php";
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();

                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        reader.close();

                        Bundle bun = new Bundle();
                        bun.putString("jsonGap", receiveMsg);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);


                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void jsonParsing(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray friendArr = jsonObject.getJSONArray("response");

            for (int i = 0; i < friendArr.length(); i++) {
                JSONObject friendObj = friendArr.getJSONObject(i);

                Friend friend = new Friend();
                friend.setUserKakaoCode(friendObj.getLong("F.K_code1"));
                friend.setFriendKakaoCode(friendObj.getLong("F.K_code2"));
                friend.setFriendName(friendObj.getString("K.K_name"));
                friend.setFriendStatus(friendObj.getInt("F.F_status"));
                friend.setFriendEmail(friendObj.getString("K.K_email"));
                friend.setFriendProfileURL(friendObj.getString("K.K_profile"));
                friend.setFriendlong(friendObj.getLong("K.K_long"));
                friend.setFriendLat(friendObj.getLong("K.K_lat"));
                friend.setFriendGhost(friendObj.getInt("K.K_ghost"));

                adapter.addItem(friend);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parsing error", e.getMessage());
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bundle bun = message.getData();
            String str = bun.getString("jsonGap");

            jsonParsing(str);
            return true;
        }
    });
}