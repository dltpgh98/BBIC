package com.example.bbic;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GpsTracker extends Service implements LocationListener {

    private Context mContext;
    Location location;
    double latitude, longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    public GpsTracker(Context context){
        this.mContext = context;
        getLocation();
    }

    public Location getLocation(){
        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); //GPS 활성화 여부 체크
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); //인터넷 연결 여부 체크

            if(!isGPSEnabled && !isNetworkEnabled){

            } else {
                int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);//권한체크
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

                if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){

                }else{
                    return null;
                }

                if(isNetworkEnabled){//네트워크 활성화시 위치정보 저장 if문
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);//위치 정보 업데이트 설정

                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//네트워크를 통한 위치 정보 저장

                        if(location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled){//GPS 활성화시 위치정보 저장 if문
                    if(location == null){//네트워크로 위치정보 저장 실패시
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);//위치 정보 업데이트 설정

                        if(locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//GPS를 통한 위치 정보 저장

                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            Log.e("위치 조회 에러",e.toString());
        }
        return location;
    }

    public double getLatitude(){//위도 조회
        if(location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){//경도 조회
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public void stopUsingGPS(){//위치 업데이트 중지
        if(locationManager != null){
            locationManager.removeUpdates(GpsTracker.this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {//위치 업데이트시 이벤트

    }

    @Override
    public void onProviderDisabled(String provider) {//권한 비허용시

    }

    @Override
    public void onProviderEnabled(String provider){//권한 허용시

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { //권한 변경시

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
