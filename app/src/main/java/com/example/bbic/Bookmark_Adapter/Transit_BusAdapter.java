package com.example.bbic.Bookmark_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

import java.util.ArrayList;

public class Transit_BusAdapter extends RecyclerView.Adapter<Transit_BusAdapter.BusCustomViewHolder> {
    private ArrayList<PlaceData> arrayList;

    public Transit_BusAdapter(ArrayList<PlaceData> arrayList) {this.arrayList=arrayList;}

    @NonNull
    @Override
    public BusCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_transit_bus_list,parent,false);
        BusCustomViewHolder holder = new BusCustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Transit_BusAdapter.BusCustomViewHolder holder, int position) {
        holder.bookmark_iv.setImageResource(arrayList.get(position).getBookmark_iv());
        holder.bus_info_iv.setImageResource(arrayList.get(position).getBus_info_iv());  //버스모양 버튼과 별모양

        holder.station_name.setText(arrayList.get(position).getStation_name());
        holder.to_station_name.setText(arrayList.get(position).getTo_station_name());

        holder.bus_fastTime1.setText(arrayList.get(position).getBus_fastTime1());
        holder.bus_fastTime2.setText(arrayList.get(position).getBus_fastTime2());
        holder.bus_fastTime3.setText(arrayList.get(position).getBus_fastTime3());

        holder.bus_nextTime1.setText(arrayList.get(position).getBus_nextTime1());
        holder.bus_nextTime2.setText(arrayList.get(position).getBus_nextTime2());
        holder.bus_nextTime3.setText(arrayList.get(position).getBus_nextTime3());

        holder.bus_num1.setText(arrayList.get(position).getBus_num1());
        holder.bus_num2.setText(arrayList.get(position).getBus_num2());
        holder.bus_num3.setText(arrayList.get(position).getBus_num3());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.station_name.getText().toString();
                Toast.makeText(view.getContext(),curName, Toast.LENGTH_LONG).show();
            }
        });
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

    public class BusCustomViewHolder extends RecyclerView.ViewHolder{
        protected ImageButton bookmark_iv;
        protected ImageButton bus_info_iv;
        protected TextView station_name;
        protected TextView to_station_name;
        protected TextView bus_fastTime1;
        protected TextView bus_nextTime1;
        protected TextView bus_num1;
        protected TextView bus_fastTime2;
        protected TextView bus_nextTime2;
        protected TextView bus_num2;
        protected TextView bus_fastTime3;
        protected TextView bus_nextTime3;
        protected TextView bus_num3;

        public BusCustomViewHolder(@NonNull View itemView){
            super(itemView);
            this.bookmark_iv = (ImageButton) itemView.findViewById(R.id.bus_bookmark_ibtn);
            this.bus_info_iv = (ImageButton) itemView.findViewById(R.id.bus_station_info_ibtn);
            this.station_name = (TextView) itemView.findViewById(R.id.bus_station_name_tv);
            this.to_station_name = (TextView) itemView.findViewById(R.id.bus_to_station_name_tv);
            this.bus_fastTime1 = (TextView) itemView.findViewById(R.id.bus1_fastTime_tv);
            this.bus_fastTime2 = (TextView) itemView.findViewById(R.id.bus2_fastTime_tv);
            this.bus_fastTime3 = (TextView) itemView.findViewById(R.id.bus3_fastTime_tv);
            this.bus_nextTime1 = (TextView) itemView.findViewById(R.id.bus1_nextTime_tv);
            this.bus_nextTime2 = (TextView) itemView.findViewById(R.id.bus2_nextTime_tv);
            this.bus_nextTime3 = (TextView) itemView.findViewById(R.id.bus3_nextTime_tv);
            this.bus_num1 = (TextView) itemView.findViewById(R.id.bus1_num_tv);
            this.bus_num2 = (TextView) itemView.findViewById(R.id.bus2_num_tv);
            this.bus_num3 = (TextView) itemView.findViewById(R.id.bus3_num_tv);
        }

    }
}
