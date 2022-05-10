package com.example.bbic.Bookmark_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bbic.R;

import java.util.ArrayList;

public class KakaoFriendListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
//    ArrayList<KakaoFriendData> Data;
//
//    public KakaoFriendListAdapter(Context mContext, ArrayList<KakaoFriendData> data) {
//        this.mContext = mContext;
//        Data = data;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        View view1 = mLayoutInflater.inflate(R.layout.fp_add_kakaofriend_item, null);
//
//        ImageView imageView = (ImageView)view1.findViewById(R.id.fp_profile);
//        TextView textView = (TextView)view1.findViewById(R.id.fp_nickname);
//
//        Glide.with(view1).load(Data.get(i).getProfileThumbnailImage()).circleCrop().into(imageView);
//        textView.setText(Data.get(i).getProfileNickname());
        return view;
    }
}
