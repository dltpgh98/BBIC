package com.example.bbic;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {

    BackgroundTask task;
    int value = 0;

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        task = new BackgroundTask();
        task.execute();

        println("onStartCommand");

        initializeNotification();
        return START_NOT_STICKY;
    }

    public void initializeNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("BBIC is running");
        style.setBigContentTitle(null);
        style.setSummaryText("위치 확인중");
        builder.setContentText(null);
        builder.setContentTitle(null);
        builder.setOngoing(true);
        builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);

        Intent notificationIntent = new Intent(this,Maps_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent, FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel("1","포그라운드 서비스",NotificationManager.IMPORTANCE_NONE));
        }
        Notification notification = builder.build();
        startForeground(1,notification);
    }

    class BackgroundTask extends AsyncTask<Integer, String, Integer>{
        String result = "";

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        @Override
        protected Integer doInBackground(Integer... values){

            while(isCancelled() == false){
                try{
                    Thread.sleep(1000);
                    value++;
                }catch (InterruptedException ie){

                }

            }
            return value;
        }
        @Override
        protected void onProgressUpdate(String... String){
            println("onProgressUpdate()");
        }

        @Override
        protected void onPostExecute(Integer integer){
            println("onPostExecute()");
            value = 0;
        }

        @Override
        protected void onCancelled(){
            value = 0;
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        task.cancel(true);
    }

    public void println(String message){
        Log.d("Foreground", message);
    }
}