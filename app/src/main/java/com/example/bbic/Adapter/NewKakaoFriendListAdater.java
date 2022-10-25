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

    LayoutInflater layoutInflater = null;
    List<KakaoFriend> items = null;
    private int count = 0;

    public NewKakaoFriendListAdater(List<KakaoFriend> items) {
        this.items = items;
        count =items.size();
    }

    @Override
    public int getCount() {
        return count;
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

        if(convertView == null){
            final Context context = parent.getContext();
            if(layoutInflater == null){
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = layoutInflater.inflate(R.layout.fp_friend_add_list, parent, false);
        }


        ImageView profile = (ImageView) convertView.findViewById(R.id.add_profile_iv);
        TextView friendName = (TextView) convertView.findViewById(R.id.add_name);
        //ImageView friendstatus = (ImageView) convertView.findViewById(R.id.ask_pro_stat_iv);


        System.out.println("친구 카카오톡 이름" + items.get(i).getFriend_Nickname());

        //Glide.with().load(items.get(i).getFriend_Image()).circleCrop().into(profile); // 친구프로필
        friendName.setText(items.get(i).getFriend_Nickname()); // 친구이름
        long friendCode = items.get(i).getFriend_id();//친구 코드


        return convertView;
    }
}
