package com.example.bbic.Bookmark_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Transit_SubwayAdapter extends RecyclerView.Adapter<Transit_SubwayAdapter.SubwayCustomViewHolder>{
    private ArrayList<PlaceData> arrayList;

    public Transit_SubwayAdapter(ArrayList<PlaceData> arrayList) {this.arrayList=arrayList;}

    @NonNull
    @Override
    public SubwayCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_transit_subway_list,parent,false);
        SubwayCustomViewHolder holder = new SubwayCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubwayCustomViewHolder holder, int position) {
        holder.bookmark_iv.setImageResource(arrayList.get(position).getBookmark_iv());
        holder.subway_this_name.setText(arrayList.get(position).getSubway_this_name());
        holder.subway_left_direction.setText(arrayList.get(position).getSubway_left_direction());
        holder.subway_right_direction.setText(arrayList.get(position).getSubway_right_direction());

        holder.subway_left_name1.setText(arrayList.get(position).getSubway_left_name1());
        holder.subway_right_name1.setText(arrayList.get(position).getSubway_right_name1());
        holder.subway_left_name2.setText(arrayList.get(position).getSubway_left_name2());
        holder.subway_right_name2.setText(arrayList.get(position).getSubway_right_name2());
        holder.subway_left_name3.setText(arrayList.get(position).getSubway_left_name3());
        holder.subway_right_name3.setText(arrayList.get(position).getSubway_right_name3());

        holder.subway_left_time1.setText(arrayList.get(position).getSubway_left_time1());
        holder.subway_right_time1.setText(arrayList.get(position).getSubway_right_time1());
        holder.subway_left_time2.setText(arrayList.get(position).getSubway_left_time2());
        holder.subway_right_time2.setText(arrayList.get(position).getSubway_right_time2());
        holder.subway_left_time3.setText(arrayList.get(position).getSubway_left_time3());
        holder.subway_right_time3.setText(arrayList.get(position).getSubway_right_time3());

        holder.subway_left_whole1.setText(arrayList.get(position).getSubway_left_whole1());
        holder.subway_right_whole1.setText(arrayList.get(position).getSubway_right_whole1());
        holder.subway_left_whole2.setText(arrayList.get(position).getSubway_left_whole2());
        holder.subway_right_whole2.setText(arrayList.get(position).getSubway_right_whole2());
        holder.subway_left_whole3.setText(arrayList.get(position).getSubway_left_whole3());
        holder.subway_right_whole3.setText(arrayList.get(position).getSubway_right_whole3());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    public void setArrayList(ArrayList<PlaceData> list){
        this.arrayList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public void remove(int position){
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class SubwayCustomViewHolder extends RecyclerView.ViewHolder{
        protected ImageButton bookmark_iv;

        protected TextView subway_this_name;
        protected TextView subway_left_direction;
        protected TextView subway_right_direction;

        protected TextView subway_left_name1;
        protected TextView subway_left_time1;
        protected TextView subway_left_whole1;
        protected TextView subway_left_name2;
        protected TextView subway_left_time2;
        protected TextView subway_left_whole2;
        protected TextView subway_left_name3;
        protected TextView subway_left_time3;
        protected TextView subway_left_whole3;

        protected TextView subway_right_name1;
        protected TextView subway_right_time1;
        protected TextView subway_right_whole1;
        protected TextView subway_right_name2;
        protected TextView subway_right_time2;
        protected TextView subway_right_whole2;
        protected TextView subway_right_name3;
        protected TextView subway_right_time3;
        protected TextView subway_right_whole3;

        public SubwayCustomViewHolder(@NonNull View itemView){
            super(itemView);
            this.bookmark_iv = (ImageButton) itemView.findViewById(R.id.subway_bookmark_ibtn);
            this.subway_this_name = (TextView) itemView.findViewById(R.id.subway_this_station_name_tv);
            this.subway_left_direction = (TextView) itemView.findViewById(R.id.subway_left_station_direction_tv);
            this.subway_right_direction = (TextView) itemView.findViewById(R.id.subway_right_station_direction_tv);

            this.subway_left_name1 = (TextView) itemView.findViewById(R.id.subway_left_station1_name_tv);
            this.subway_left_time1 = (TextView) itemView.findViewById(R.id.subway_left_station1_time_tv);
            this.subway_left_whole1 = (TextView) itemView.findViewById(R.id.subway_left_station1_whole_tv);

            this.subway_left_name2 = (TextView) itemView.findViewById(R.id.subway_left_station2_name_tv);
            this.subway_left_time2 = (TextView) itemView.findViewById(R.id.subway_left_station2_time_tv);
            this.subway_left_whole2 = (TextView) itemView.findViewById(R.id.subway_left_station2_whole_tv);

            this.subway_left_name3 = (TextView) itemView.findViewById(R.id.subway_left_station3_name_tv);
            this.subway_left_time3 = (TextView) itemView.findViewById(R.id.subway_left_station3_time_tv);
            this.subway_left_whole3 = (TextView) itemView.findViewById(R.id.subway_left_station3_whole_tv);


            this.subway_right_name1 = (TextView) itemView.findViewById(R.id.subway_right_station1_name_tv);
            this.subway_right_time1 = (TextView) itemView.findViewById(R.id.subway_right_station1_time_tv);
            this.subway_right_whole1 = (TextView) itemView.findViewById(R.id.subway_right_station1_whole_tv);

            this.subway_right_name2 = (TextView) itemView.findViewById(R.id.subway_right_station2_name_tv);
            this.subway_right_time2 = (TextView) itemView.findViewById(R.id.subway_right_station2_time_tv);
            this.subway_right_whole2 = (TextView) itemView.findViewById(R.id.subway_right_station2_whole_tv);

            this.subway_right_name3 = (TextView) itemView.findViewById(R.id.subway_right_station3_name_tv);
            this.subway_right_time3 = (TextView) itemView.findViewById(R.id.subway_right_station3_time_tv);
            this.subway_right_whole3 = (TextView) itemView.findViewById(R.id.subway_right_station3_whole_tv);
        }
    }
}
