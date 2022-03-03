//package com.example.bbic;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import com.odsay.odsayandroidsdk.API;
//import com.odsay.odsayandroidsdk.ODsayData;
//import com.odsay.odsayandroidsdk.ODsayService;
//import com.odsay.odsayandroidsdk.OnResultCallbackListener;
//
//import java.util.jar.JarException;
//
//public class test extends AppCompatActivity {
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test);
//
//        ODsayService oDsayService = ODsayService.init(this, "d/F477b1GZGKZgWCv8LynPEERmoxCdE1jSOojHzKNPM");
//        oDsayService.setReadTimeout(5000);
//        oDsayService.setConnectionTimeout(5000);
//
//        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
//            @Override
//            public void onSuccess(ODsayData oDsayData, API api) {
//                try{
//                  if(api == API.BUS_STATION_INFO){
//                        String stationName = oDsayData.getJson().getJSONObject(result).getString("stationName")
//                        Log.d("Station name : %s", stationName);
//                  }
//                }catch (JarException e){
//                        e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int i, String s, API api) {
//                    if(api == API.BUS_STATION_INFO){
//
//                    }
//            }
//        };
//        oDsayService.requestBusStationInfo("107475", onResultCallbackListener);
//    }
//}
