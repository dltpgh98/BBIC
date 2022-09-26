package com.example.bbic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bbic.KakaoFriend;
import com.example.bbic.R;

import java.util.List;

public class NewKakaoFriendListAdater extends BaseAdapter {

    List<KakaoFriend> items = null;
    Context context;

    public NewKakaoFriendListAdater(List<KakaoFriend> items, Context context) {
        this.items = items;
        this.context = context;
    }

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
    public View getView(int i, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fp_friend_ask_list, parent, false);

        ImageView profile = (ImageView) view.findViewById(R.id.ask_profile_iv);
        TextView friendName = (TextView) view.findViewById(R.id.ask_name);
        ImageView friendstatus = (ImageView) view.findViewById(R.id.ask_pro_stat_iv);


        Glide.with(context).load(items.get(i).getFriend_Image()).circleCrop().into(profile); // 친구프로필
        friendName.setText(items.get(i).getFriend_Nickname()); // 친구이름
        long friendCode = items.get(i).getFriend_id();//친구 코드
        return null;
    }
}
