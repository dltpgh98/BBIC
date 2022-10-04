package com.example.bbic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbic.Data.PromiseFriendMarker;
import com.example.bbic.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MarkerListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<PromiseFriendMarker> arrayList;
    List<View> viewList = new List<View>() {
        @Override
        public int size() {
            return viewList.size();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<View> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(View view) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends View> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends View> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public View get(int i) {
            return null;
        }

        @Override
        public View set(int i, View view) {
            return null;
        }

        @Override
        public void add(int i, View view) {

        }

        @Override
        public View remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<View> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<View> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<View> subList(int i, int i1) {
            return null;
        }
    };
//    ArrayList<PlaceData> arrayList;

//    public MarkerListAdapter(Context context, ArrayList<PlaceData> data) {
//        mContext = context;
//        arrayList = data;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }

    public MarkerListAdapter(Context context, ArrayList<PromiseFriendMarker> data) {
        mContext = context;
        arrayList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public List<View> getViewList() {
        return viewList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

//    @Override
//    public PlaceData getItem(int position) {
//        return arrayList.get(position);
//    }
    @Override
    public PromiseFriendMarker getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.drawer_list, null);
        viewList.add(view);

        TextView listTitleTv, listAddTv, listTimeTv;
        ImageView listProfile1Iv, listProfile2Iv, listProfile3Iv;
//        ImageView listProfile[] = new ImageView[arrayList.get(position).getMarkerProUserProfile().length];

        listTitleTv = (TextView) view.findViewById(R.id.pro_list_title_tv);
        listAddTv = (TextView) view.findViewById(R.id.pro_list_address_tv);
        listTimeTv = (TextView) view.findViewById(R.id.pro_list_settime_tv);

        listProfile1Iv = (ImageView) view.findViewById(R.id.pro_list_profile1_iv);
        listProfile2Iv = (ImageView) view.findViewById(R.id.pro_list_profile2_iv);
        listProfile3Iv = (ImageView) view.findViewById(R.id.pro_list_profile3_iv);

//        for (int imv = arrayList.get(position).getMarkerProUserProfile().length-1; imv <= 0; imv--){
//            listProfile[imv] = (ImageView) view.findViewById(R.)
//        }
        listTitleTv.setText(arrayList.get(position).getProTitleName());
        listAddTv.setText(arrayList.get(position).getProPosName());
        listTimeTv.setText(arrayList.get(position).getProTime());

        for(int count =0;count<arrayList.get(position).getMarkerProUserProfile().length;count++){
            switch (count) {
                case 0:
//                    listProfile1Iv.setImageResource(Integer.valueOf(arrayList.get(position).getMarkerProUserProfile()[count]));
                    Glide.with(mContext).load(arrayList.get(position).getMarkerProUserProfile()[count]).circleCrop().into(listProfile1Iv); // 친구프로필

                    break;
                case 1:
                    Glide.with(mContext).load(arrayList.get(position).getMarkerProUserProfile()[count]).circleCrop().into(listProfile2Iv); // 친구프로필

//                    listProfile2Iv.setImageResource(Integer.valueOf(arrayList.get(position).getMarkerProUserProfile()[count]));
                    break;
                case 2:
                    Glide.with(mContext).load(arrayList.get(position).getMarkerProUserProfile()[count]).circleCrop().into(listProfile3Iv); // 친구프로필

//                    listProfile3Iv.setImageResource(Integer.valueOf(arrayList.get(position).getMarkerProUserProfile()[count]));
                    break;
                default:
                    break;
            }
            System.out.println(arrayList.get(position).getMarkerProUserProfile());
        }
//        listProfile1Iv.setImageResource(arrayList.get(position).getPromise_profile1());
//        listProfile2Iv.setImageResource(arrayList.get(position).getPromise_profile2());
//        listProfile3Iv.setImageResource(arrayList.get(position).getPromise_profile3());

        return view;
    }
}