package com.example.bbic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.bbic.Data.HistoryData;
import com.example.bbic.Data.PromiseFriendMarker;
import com.example.bbic.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HistoryListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<HistoryData> arrayList;

    public HistoryListAdapter(Context context, ArrayList<HistoryData> data)
    {
        mContext = context;
        arrayList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public HistoryData getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void deleteItem(int i){
        arrayList.remove(i);
    }

    public void insertItem(String start_pos, String end_pos) {
        HistoryData historyData = new HistoryData(start_pos, end_pos);

        if(arrayList.size()>1)
        {
            for(int i = 0; i<arrayList.size(); i++)
            {
                if(arrayList.get(i).check_equals(historyData))return;
            }

            arrayList.add(0,historyData);
        }

        if(arrayList.size()>=6){
            arrayList.remove(5);
        }
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.find_history_list,null);
        TextView start_pos, end_pos;

        start_pos = (TextView) view.findViewById(R.id.history_pos1_tv);
        end_pos = (TextView) view.findViewById(R.id.history_pos2_tv);

        start_pos.setText(arrayList.get(i).getStart_pos());
        end_pos.setText(arrayList.get(i).getEnd_pos());

        return view;
    }
}