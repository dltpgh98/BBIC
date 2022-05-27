package com.example.bbic;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {
    private static final String TAG = ForegroundService.class.getSimpleName();
    private Thread serviceThread;
    private int serviceCount;

    public ForegroundService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if("startForeground".equals(intent.getAction())){
            startForegroundService();
        }
        return START_STICKY;
    }

    public void startForegroundService(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("BBIC");
        builder.setContentText("BBIC어플리케이션 실행중입니다.");

        Intent notificationIntent = new Intent(this,Maps_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        builder.setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(new NotificationChannel("default","기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        startForeground(1,builder.build());
    }
}
