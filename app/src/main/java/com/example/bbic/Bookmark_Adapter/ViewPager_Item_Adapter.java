package com.example.bbic.Bookmark_Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbic.R;

import java.util.List;

public class ViewPager_Item_Adapter extends RecyclerView.Adapter<ViewPager_Item_Adapter.PagerHolder> {

    private final List<String> friend_name, friend_profile;
    private final List<Long> friend_code;
    private List<Integer> friend_status;
    private Context context;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private boolean event;

    private final static int pageItemsCount = 8;
    private final static int pageConstant = 1;

    public ViewPager_Item_Adapter(Context context, List<String> friend_name, List<String> friend_profile, List<Long> friend_code, List<Integer> friend_status) {
        this.context = context;
        this.friend_name = friend_name;
        this.friend_profile = friend_profile;
        this.friend_code = friend_code;
        this.friend_status = friend_status;
    }

    @NonNull
    @Override
    public PagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new PagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerHolder holder, int position) {
        for(int i = 0; i < friend_name.size(); i++)
        {
           holder.nameHolder[i].setText(friend_name.get(i));
           holder.profileHolder[i].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Log.d("test", " input : ok");
                   builder = new AlertDialog.Builder(context);
                   dialog = new Dialog(context,R.style.Theme_TransparentBackground);
                   dialog.setContentView(R.layout.view_detail);
                   WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                   layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                   layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                   dialog.getWindow().setAttributes((WindowManager.LayoutParams)layoutParams);
                   dialog.getWindow().setGravity(Gravity.BOTTOM);
                   dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                   dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                   dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                           WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

                   dialog.show();
                   event = true;
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        int page = friend_name.size()/pageItemsCount+pageConstant;
        Log.d("itemSize ", "Size : " + page);
        return page;
    }

    public Dialog getDialog(){
        return dialog;
    }

    public boolean getEvent(){
        return event;
    }

    public class PagerHolder extends RecyclerView.ViewHolder {

        ImageView[] profileHolder = new ImageView[8];
        TextView[] nameHolder = new TextView[8];

        public PagerHolder(@NonNull View itemView) {
            super(itemView);

            profileHolder[0] = itemView.findViewById(R.id.view_item_ibtn1);
            profileHolder[1] = itemView.findViewById(R.id.view_item_ibtn2);
            profileHolder[2] = itemView.findViewById(R.id.view_item_ibtn3);
            profileHolder[3] = itemView.findViewById(R.id.view_item_ibtn4);
            profileHolder[4] = itemView.findViewById(R.id.view_item_ibtn5);
            profileHolder[5] = itemView.findViewById(R.id.view_item_ibtn6);
            profileHolder[6] = itemView.findViewById(R.id.view_item_ibtn7);
            profileHolder[7] = itemView.findViewById(R.id.view_item_ibtn8);

            nameHolder[0] = itemView.findViewById(R.id.view_item_name1);
            nameHolder[1] = itemView.findViewById(R.id.view_item_name2);
            nameHolder[2] = itemView.findViewById(R.id.view_item_name3);
            nameHolder[3] = itemView.findViewById(R.id.view_item_name4);
            nameHolder[4] = itemView.findViewById(R.id.view_item_name5);
            nameHolder[5] = itemView.findViewById(R.id.view_item_name6);
            nameHolder[6] = itemView.findViewById(R.id.view_item_name7);
            nameHolder[7] = itemView.findViewById(R.id.view_item_name8);

            /*for (int i = 0; i < 8; i++) {
                int profileId = itemView.getResources().getIdentifier("view_item_ibtn" + i, "id", context.getPackageName());
                int nameId = itemView.getResources().getIdentifier("view_item_name" + i, "id", context.getPackageName());
                profileHolder[i] = (ImageView) itemView.findViewById(profileId);
                nameHolder[i] = (TextView) itemView.findViewById(nameId);
            }*/
        }
    }
}
