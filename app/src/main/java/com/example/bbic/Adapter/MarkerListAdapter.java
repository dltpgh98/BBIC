package com.example.bbic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MarkerListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<PlaceData> arrayList;

    public MarkerListAdapter(Context context, ArrayList<PlaceData> data) {
        mContext = context;
        arrayList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public PlaceData getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.drawer_list, null);

        TextView listTitleTv, listAddTv, listTimeTv;
        ImageView listProfile1Iv, listProfile2Iv, listProfile3Iv;

        listTitleTv = (TextView) view.findViewById(R.id.pro_list_title_tv);
        listAddTv = (TextView) view.findViewById(R.id.pro_list_address_tv);
        listTimeTv = (TextView) view.findViewById(R.id.pro_list_settime_tv);

        listProfile1Iv = (ImageView) view.findViewById(R.id.pro_list_profile1_iv);
        listProfile2Iv = (ImageView) view.findViewById(R.id.pro_list_profile2_iv);
        listProfile3Iv = (ImageView) view.findViewById(R.id.pro_list_profile3_iv);

        listTitleTv.setText(arrayList.get(position).getPromise_title());
        listAddTv.setText(arrayList.get(position).getPromise_address());
        listTimeTv.setText(arrayList.get(position).getPromise_settime());

        listProfile1Iv.setImageResource(arrayList.get(position).getPromise_profile1());
        listProfile2Iv.setImageResource(arrayList.get(position).getPromise_profile2());
        listProfile3Iv.setImageResource(arrayList.get(position).getPromise_profile3());

        return view;
    }
}
