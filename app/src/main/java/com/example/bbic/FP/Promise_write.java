package com.example.bbic.FP;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbic.R;

public class Promise_write extends AppCompatActivity {

    TextView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promise_write);

        close = (TextView) findViewById(R.id.promise_write_close_tv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int num = (int)(Math.random()*999999) + 100000;

        EditText promiseTitle = (EditText) findViewById(R.id.promise_pen_title_tv);
        TextView promiseTime = (TextView) findViewById(R.id.promise_pen_time_tv);
        TextView promiseFriend = (TextView) findViewById(R.id.promise_pen_member_tv);
        EditText promisePlace = (EditText) findViewById(R.id.promise_pen_place_tv);
        EditText promiseText = (EditText) findViewById(R.id.promise_pen_txt_tv);


        TextView saveBtn = (TextView) findViewById(R.id.schedule_pen_save_tv);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = promiseTitle.getText().toString();
                String time = promiseTime.getText().toString();


            }
        });

    }
}