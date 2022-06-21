package com.example.bbic.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bbic.Data.Promise;
import com.example.bbic.FP.FP_promise_list;
import com.example.bbic.FP.FP_promise_list;
import com.example.bbic.R;

import java.util.List;

public class PromissListAdapter extends BaseAdapter {

    private Context context;
    private List<Promise> promises;
    private List<Promise> userPromises;
    private Fragment parentActivity;


    public PromissListAdapter(Context context, List<Promise> promises, List<Promise> userPromises, FP_promise_list parentActivity) {
        this.context = context;
        this.promises = promises;
        this.userPromises = userPromises;
        //this.userFriendsStatus = userFriendsStatus;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return promises.size();
    }

    @Override
    public Object getItem(int i) {
        return promises.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//        if (view == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.fp_friend_list_list, viewGroup, false);
//        }

        View v = View.inflate(context, R.layout.fp_promise_list_list, null);

        //파티 맴버 프로파일
        ImageView profileImage1 = (ImageView) v.findViewById(R.id.pro_list_profile1_iv);
        ImageView profileImage2 = (ImageView) v.findViewById(R.id.pro_list_profile2_iv);
        ImageView profileImage3 = (ImageView) v.findViewById(R.id.pro_list_profile3_iv);

        TextView promiseTitle = (TextView) v.findViewById(R.id.pro_list_title_tv);//약속 제목
        TextView promiseAddress = (TextView) v.findViewById(R.id.pro_list_address_tv);//약속 주소
        TextView promiseDate = (TextView) v.findViewById(R.id.pro_list_settime_tv);// 약속 날짜

        promiseTitle.setText(promises.get(i).getPartyName());
        System.out.println("약속 타이틀 확인" + promises.get(i).getPartyName());
        promiseAddress.setText(promises.get(i).getPromiseAddress());
        promiseDate.setText(promises.get(i).getPromissTime());

        String friendProfileArr = promises.get(i).getFriendProfile();//친구 이미지 배열
        String[] friendProfile = new String[5];


        String[] strArr = friendProfileArr.split(",");
        for (int j = 0; j <strArr.length ; j++){
            friendProfile[j] = strArr[j];
            if(j == 0){
                if(friendProfile[j] == null){
                    profileImage2.setVisibility(view.GONE);
                }
                Glide.with(context).load(friendProfile[j]).circleCrop().into(profileImage1);
            }
            if(j == 1){
                if(friendProfile[j] == null){
                    profileImage2.setVisibility(view.GONE);
                }
                Glide.with(context).load(friendProfile[j]).circleCrop().into(profileImage2);
            }
            if(j == 2){
                if(friendProfile[j] == null){
                    System.out.println("역속의 세번째 친구 프로필 주소 : "+ friendProfile[j]);
                    profileImage2.setVisibility(view.INVISIBLE);
                }
                Glide.with(context).load(friendProfile[j]).circleCrop().into(profileImage3);
            }
        }

        return v;
    }

}

