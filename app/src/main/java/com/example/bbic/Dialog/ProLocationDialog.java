package com.example.bbic.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bbic.DB.AcceptPromiseRequest;
import com.example.bbic.Data.Promise;
import com.example.bbic.R;

import org.json.JSONObject;

import java.util.List;

public class ProLocationDialog {
    Context context;
    Dialog dialog;

    public ProLocationDialog(Context context) {
        this.context = context;
    }



    public void ShowDialog(int partycode , long userCode){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.promiss_location_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.loading_text);

        Button cancel= (Button)dialog.findViewById(R.id.pro_loc_cancel_bt);
        Button accept= (Button)dialog.findViewById(R.id.pro_loc_accept_bt);
        dialog.setCancelable(false);

        dialog.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                AcceptPromiseRequest acceptPromiseRequest = new AcceptPromiseRequest(partycode, userCode, 0, responseListener1);
                Log.d("",partycode+"파티    "+userCode+" 유저코드");
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                queue.add(acceptPromiseRequest);
                dialog.dismiss();
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "수락");
                Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Log.d("",partycode+"파티    "+userCode+" 유저코드");
                AcceptPromiseRequest acceptPromiseRequest = new AcceptPromiseRequest(partycode, userCode, 1, responseListener1);
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                queue.add(acceptPromiseRequest);
                dialog.dismiss();
            }
        });
    }
}
