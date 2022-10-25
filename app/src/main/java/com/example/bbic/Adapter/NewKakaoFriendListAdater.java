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

    //LayoutInflater layoutInflater = null;
    Context context;
    List<KakaoFriend> items;


    public NewKakaoFriendListAdater(Context context ,List<KakaoFriend> items) {
        this.items = items;
        this.context = context;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.fp_friend_add_list, null);


        ImageView friendProfile = (ImageView) v.findViewById(R.id.add_profile_iv);
        TextView friendName = (TextView) v.findViewById(R.id.add_name);
        //ImageView friendstatus = (ImageView) convertView.findViewById(R.id.ask_pro_stat_iv);
        ImageView friendAddBtn = (ImageView) v.findViewById(R.id.add_friend_iv);


        System.out.println("친구 카카오톡 프로파일 주소" + items.get(i).getFriend_Image());
        System.out.println("친구 카카오톡 이름" + items.get(i).getFriend_Nickname());

        Glide.with(context).load(items.get(i).getFriend_Image()).circleCrop().into(friendProfile); // 친구프로필
        friendName.setText(items.get(i).getFriend_Nickname().toString()); // 친구이름
        long friendCode = items.get(i).getFriend_id();//친구 코드


        friendAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
    }
}
