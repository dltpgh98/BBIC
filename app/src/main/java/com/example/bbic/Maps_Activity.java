package com.example.bbic;

import static com.naver.maps.map.NaverMap.LAYER_GROUP_TRANSIT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bbic.Adapter.ViewPager_Item_Adapter;
import com.example.bbic.Bookmark.Bookmark;
import com.example.bbic.DB.AddBusRequest;
import com.example.bbic.DB.AddBusStationRequest;
import com.example.bbic.DB.AddLocationRequest;
import com.example.bbic.DB.AddSubwayRequest;
import com.example.bbic.DB.UpdateGhostRequest;
import com.example.bbic.DB.UpdatePosRequest;
import com.example.bbic.Data.FriendMarker;
import com.example.bbic.FP.FP;
import com.example.bbic.FindWay.Find_Way_Frag;
import com.example.bbic.FindWay.Map_Find_way;
import com.google.android.material.tabs.TabLayout;
import com.naver.maps.geometry.Coord;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.MultipartPathOverlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.odsay.odsayandroidsdk.ODsayService;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

//ver 0.0.1
public class Maps_Activity extends AppCompatActivity implements OnMapReadyCallback {


    public Maps_Activity() {
    }

    private static class InfoWindowAdapter extends InfoWindow.DefaultTextAdapter {


        private InfoWindowAdapter(@NonNull Context context) {
            super(context);
        }

        @NonNull
        @Override
        public CharSequence getText(@NonNull InfoWindow infoWindow) {

            if (infoWindow.getMarker() != null) {
                return getContext().getString(R.string.format_info_window_on_marker, infoWindow.getMarker().getTag());
            } else {
//                    y = String.valueOf(infoWindow.getPosition().latitude);
//                    x = String.valueOf(infoWindow.getPosition().longitude);
                System.out.println("????????????????????? ????????? ?????? : " + StationName);
                return StationName;
            }

        }
    }

    //?????? ?????? ????????? ?????????
    class BtnOnClickListener implements View.OnClickListener {
        private int sw = 0;

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            Handler handl = new Handler();
            Response.Listener<String> responseListenerPos = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        System.out.println("????????????" + response);
                        //JSONObject jsonObject = new JSONObject( response );
                        //boolean success = jsonObject.getBoolean( "success" );
                        boolean success = Boolean.parseBoolean(response);
                        System.out.println(success);
                        //???????????? ?????????
                        if (success) {
                            System.out.println("???????????? ??????");
                            //???????????? ?????????
                        } else {
                            System.out.println("???????????? ??????");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            switch (view.getId()) {
                //case??? ?????? id??? ?????? ??????????????? ??????

                case R.id.menu_ibtn:
                    if (!drawerEnabled) {
                        gpsTracker = new GpsTracker(Maps_Activity.this);

                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();

                        String myAddress = getCurrentAddress(latitude, longitude);

                        drawerInit(myAddress);
                        drawerEnabled = true;
                    }
                    try {
                        if (upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        }
                        keyboardmanager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                    drawerLayout.openDrawer(drawerView);
                    break;
                case R.id.drawer_menu_1:
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.drawer_menu_2:
                    Intent intent2 = new Intent(getApplicationContext(), Subway.class);
                    drawerLayout.closeDrawer(drawerView);
                    intent2.putExtra("??????", k_code);
                    intent2.putExtra("?????????", name);
                    intent2.putExtra("?????????", address);
                    intent2.putExtra("????????????", fineDust);
                    intent2.putExtra("???????????????", ultraFineDust);
                    intent2.putExtra("??????", tem);
                    intent2.putExtra("??????", weather);
                    intent2.putExtra("???", area);
                    intent2.putExtra("???", city);
                    intent2.putExtra("?????????", covidNum);
                    intent2.putExtra("friendlist", friendlist);
                    intent2.putExtra("promiselist", promiselist);
                    intent2.putExtra("locationlist", locationlist);
                    intent2.putExtra("subwaylist", subwaylist);
                    intent2.putExtra("buslist", buslist);
                    startActivity(intent2);
                    break;
                case R.id.drawer_menu_3:

                    Intent intent3 = new Intent(getApplicationContext(), Bookmark.class);
                    intent3.putExtra("??????", k_code);
                    intent3.putExtra("?????????", name);
                    intent3.putExtra("?????????", address);
                    intent3.putExtra("????????????", fineDust);
                    intent3.putExtra("???????????????", ultraFineDust);
                    intent3.putExtra("??????", tem);
                    intent3.putExtra("??????", weather);
                    intent3.putExtra("???", area);
                    intent3.putExtra("???", city);
                    intent3.putExtra("?????????", covidNum);
                    intent3.putExtra("friendlist", friendlist);
                    intent3.putExtra("locationlist", locationlist);
                    intent3.putExtra("subwaylist", subwaylist);
                    intent3.putExtra("buslist", buslist);
                    Log.d("=================buslist================", "" + buslist);
                    intent3.putExtra("promiselist", promiselist);
                    drawerLayout.closeDrawer(drawerView);
                    startActivity(intent3);
//                    finish();
                    break;
                case R.id.drawer_menu_4:
                    drawerLayout.closeDrawer(drawerView);

                    view_Header.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    indicator.setVisibility(View.GONE);
                    find_way_page.setVisibility(View.VISIBLE);

                    upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    break;
                case R.id.drawer_menu_5:
                case R.id.view_header_setting_btn:

                    Intent intent5 = new Intent(getApplicationContext(), FP.class);
                    intent5.putExtra("??????", k_code);
                    intent5.putExtra("?????????", name);
                    intent5.putExtra("?????????", address);
                    intent5.putExtra("????????????", fineDust);
                    intent5.putExtra("???????????????", ultraFineDust);
                    intent5.putExtra("??????", tem);
                    intent5.putExtra("??????", weather);
                    intent5.putExtra("???", area);
                    intent5.putExtra("???", city);
                    intent5.putExtra("?????????", covidNum);
                    intent5.putExtra("friendlist", friendlist);
                    intent5.putExtra("promiselist", promiselist);
                    intent5.putExtra("buslist", buslist);
                    intent5.putExtra("subwaylist", subwaylist);
                    intent5.putExtra("locationlist", locationlist);
                    drawerLayout.closeDrawer(drawerView);
                    startActivity(intent5);
//                    finish();
                    break;
                case R.id.drawer_menu_6:
                    Intent intent6 = new Intent(getApplicationContext(), Setting_Activity.class);
                    intent6.putExtra("??????", k_code);
                    intent6.putExtra("?????????", name);
                    intent6.putExtra("?????????", address);
                    intent6.putExtra("????????????", fineDust);
                    intent6.putExtra("???????????????", ultraFineDust);
                    intent6.putExtra("??????", tem);
                    intent6.putExtra("??????", weather);
                    intent6.putExtra("???", area);
                    intent6.putExtra("???", city);
                    intent6.putExtra("?????????", covidNum);
                    intent6.putExtra("friendlist", friendlist);
                    intent6.putExtra("promiselist", promiselist);
                    intent6.putExtra("locationlist", locationlist);
                    intent6.putExtra("subwaylist", subwaylist);
                    intent6.putExtra("buslist", buslist);
                    drawerLayout.closeDrawer(drawerView);
                    startActivity(intent6);
//                    finish();
                    break;
                case R.id.main_search_ibtn:
//                    Intent intent = new Intent(getApplicationContext(), Bookmark.class);
//                    startActivity(intent);
                    search_location();
                    break;
                case R.id.main_find_way_ibtn:
                    if (viewPager.getVisibility() == View.VISIBLE) {
                        viewSwitch = false;
                        viewPager.setVisibility(View.GONE);
                    }
//                    else if (viewDetail.getVisibility() == View.VISIBLE) {
//                        viewSwitch = true;
//                        viewDetail.setVisibility(View.GONE);
//                    }


                    view_Header.setVisibility(View.GONE);

                    indicator.setVisibility(View.GONE);

                    place_info_window.setVisibility(View.GONE);
                    subway_info_window.setVisibility(View.GONE);
                    bus_info_window.setVisibility(View.GONE);

                    find_way_page.setVisibility(View.VISIBLE);

                    upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

//                    if(upPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
//                        view_Header.setVisibility(View.VISIBLE);
//                        viewPager.setVisibility(View.VISIBLE);
//                        indicator.setVisibility(View.VISIBLE);
//                        find_way_page.setVisibility(View.GONE);
//                    }
                    break;
                case R.id.view_find_way_ibtn:
//                    stopService();

                    System.out.println("?????? ??????");
                    pathOverlay.setMap(null);
                    String sPosEt = sPosEdit.getText().toString();
                    String ePosEt = ePosEdit.getText().toString();

                    System.out.print(sPosEt + "?????? " + ePosEt + "??????");

                    try {
                        nameToPos(sPosEt, ePosEt);
                    } catch (Exception e) {
                        System.out.println("????????? ?????? ???????????????.");
                    }
                    keyboardmanager.hideSoftInputFromWindow(sPosEdit.getWindowToken(), 0);

                    break;
//                    startService();
                case R.id.view_header_ghost_btn:

                    switch (userGhost) {
                        case 0:
                            userGhost = 1;
                            headerGhostBtn.setImageResource(R.drawable.ghost1);
                            break;

                        case 1:
                            userGhost = 0;
                            headerGhostBtn.setImageResource(R.drawable.ghost2);
                            break;
                    }

                    UpdateGhostRequest updateGhostRequest = new UpdateGhostRequest(k_code, userGhost, responseListenerPos);
                    RequestQueue queuePos = Volley.newRequestQueue(Maps_Activity.this);
                    queuePos.add(updateGhostRequest);

                    break;
                case R.id.posEdit_change_ibtn:
                    String startEdit = sPosEdit.getText().toString();
                    //String endEdit = ePosEdit.getText().toString();
                    sPosEdit.setText(ePosEdit.getText().toString());
                    ePosEdit.setText(startEdit);
                    ePosEdit.setSelection(ePosEdit.length());
                    sPosEdit.setSelection(sPosEdit.length());
                    break;
                case R.id.main_findWay_overlay_clear_ibtn:
                    pathOverlay.setMap(null);
                    findWayOverlayClearIBtn.setVisibility(View.GONE);
                    break;
                case R.id.main_findWay_friend_ibtn:  //?????? ????????? ?????? Test
                    break;


                case R.id.bus_info_bookmarkStar_ib:
                    bus_info_bookmarkStar.setColorFilter(Color.YELLOW);
                    AddBusStationRequest busStationRequest = new AddBusStationRequest(StationId, StationName, ardID, info_window);
                    RequestQueue busStationQueue = Volley.newRequestQueue(Maps_Activity.this);
                    busStationQueue.add(busStationRequest);

                    handl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AddBusRequest busRequest = new AddBusRequest(bus_ID[0], StationId, k_code, bus_numbers[0], info_window);
                            RequestQueue busQueue = Volley.newRequestQueue(Maps_Activity.this);
                            busQueue.add(busRequest);
                        }
                    }, 200);
                    handl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new BackgroundTask_Subway().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Bus().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_location().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Friend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        }
                    }, 500);

                    break;

                case R.id.subway_info_bookmarkStar_ib:
                    subway_info_bookmarkStar.setColorFilter(Color.YELLOW);
                    Log.d("sub", "");
                    AddSubwayRequest subwayRequest = new AddSubwayRequest(StationId, k_code, StationName, info_window);
                    RequestQueue subwayQueue = Volley.newRequestQueue(Maps_Activity.this);
                    subwayQueue.add(subwayRequest);
                    handl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new BackgroundTask_Subway().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Bus().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_location().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Friend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        }
                    }, 500);

                    break;

                case R.id.place_info_bookmarkStar_ib:
                    place_info_bookmarkStar.setColorFilter(Color.YELLOW);
                    AddLocationRequest locationRequest = new AddLocationRequest(bustitle, k_code, getCurrentAddress(mapsPointPos.latitude, mapsPointPos.longitude), Double.valueOf(mapsPointPos.latitude), Double.valueOf(mapsPointPos.longitude), info_window);
                    RequestQueue placeQueue = Volley.newRequestQueue(Maps_Activity.this);
                    placeQueue.add(locationRequest);
                    handl.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new BackgroundTask_Subway().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Bus().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_location().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            new BackgroundTask_Friend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        }
                    }, 500);


                    break;


                case R.id.place_info_startFind_ibtn:
                    findWayIbtn.callOnClick();

                    friendLat = mapsPointPos.latitude;
                    friendLong = mapsPointPos.longitude;

                    sPosEdit.setText(getCurrentAddress(mapsPointPos.latitude, mapsPointPos.longitude));

                    break;
                case R.id.place_info_endFind_ibtn:
                    findWayIbtn.callOnClick();

                    friendLat = mapsPointPos.latitude;
                    friendLong = mapsPointPos.longitude;

                    sPosEdit.setText("??? ??????");
                    ePosEdit.setText(getCurrentAddress(mapsPointPos.latitude, mapsPointPos.longitude));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            vFindIbtn.callOnClick();
                        }
                    }, 400);
                    break;
            }
        }
    }

    //?????? ????????? ?????? ??????
    GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private Geocoder geocoder;

    //????????? ?????? ??? ?????? ??????
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, searchIbtn, findWayIbtn, vFindIbtn, vEditChangeFindIbtn, findWayOverlayClearIBtn, subway_info_bookmarkStar, bus_info_bookmarkStar, place_info_bookmarkStar;
    private TextView
            temText, fineText, ultraText, covidText, nickName, areaText;
    private ImageView weatherImage, profile;
    private String[] add, place_name, bus_numbers;
    private int[] bus_ID;
    private EditText editText, sPosEdit, ePosEdit;
    private Button[] drawerMenu = new Button[6];
    private FusedLocationSource locationSource;
    private boolean drawerEnabled = false;
    private LatLng mapsPointPos;

    private ImageView headerProfile;
    private TextView headerName, headerCode,
            subway_info_title, subway_info_direction, subway_info_left_station, subway_info_this_station, subway_info_right_station,
            subway_info_left_arriveSoon_name, subway_info_left_arriveNext_name, subway_info_left_arriveSoon_time, subway_info_left_arriveNext_time,
            subway_info_right_arriveSoon_name, subway_info_right_arriveNext_name, subway_info_right_arriveSoon_time, subway_info_right_arriveNext_time,
            bus_info_title, bus_info_direction, bus_info_number,
            place_info_title, place_info_address;
    private ImageView headerGhostBtn, headerSettingBtn, place_info_start_point, place_info_end_point;

    //    private JSONArray[] path;
    private JSONObject result, meResult;
    private String fw_pos_path, mapObject;
    private Find_Way_Frag fw_frag;
    private Map_Find_way mapFindWay;
    private Subway_Info_Time subway_time;


    private double[] latiPos, longPos;
    private double sLatiPos, sLongPos, eLatiPos, eLongPos;
    private LatLng sLatLngPos, eLatLngPos;
    private String mapObjectPos;
    private ArrayList<LatLng> findPosArrayOne, findPosArrayTwo, findPosArrayTree, findPosArrayFour;

    private SlidingUpPanelLayout upPanelLayout;
    private Bundle bundleFw;
    private int position, userGhost;
    private FragmentTransaction ft;
    private MultipartPathOverlay pathOver;
    private PathOverlay pathOverlay;


    private ViewPager2 viewPager;
    private WormDotsIndicator indicator;
    private ConstraintLayout view_Header, find_way_page, place_info_window, subway_info_window, bus_info_window;
    private boolean viewSwitch;
    private Intent serviceIntent;
    private NaverMap naverMap;

    private String allDust, weather, tem, fineDust, ultraFineDust, covidNum, name, address, area, city, friendlist, promiselist, subwaylist, locationlist, buslist, userlist, ardID, bustitle;
    private long k_code;

    // ?????? ?????? ???????????? ????????? ??????
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;
    private ArrayList<FriendMarker> friendMarker;

    //?????? ?????? ?????????
    private MapFriendMarkerTread mapTread;
    private JSONObject friendListObject;

    private double friendLat, friendLong;

    private StationList[] StationLists;
    private BusList[] busLists;

    private static String y = "", x = "";
    private static Odsay odsay;
    private static Odsay bus_info;
    private static String StationName;
    private static int StationId;
    private static int stationClass;

    private List<Double> friendLatList = new ArrayList<>();
    private List<Double> friendLongList = new ArrayList<>();
    private List<String> friendNameList = new ArrayList<>();
    private List<String> friendProfileList = new ArrayList<>();
    private List<Long> friendCodeList = new ArrayList<>();
    private List<Integer> friendStatusList = new ArrayList<>();
    private List<Integer> friendGhostList = new ArrayList<>();



    public static ODsayService odsayService;
    private InputMethodManager keyboardmanager;

    //??????????????? ?????? ==============================================
    // ?????? ???????????? ???????????? ??????
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(((CameraPosition) cameraPosition).target.latitude, cameraPosition.target.longitude);
    }

    // ????????? ????????? ????????? ????????????(???????????? ???????????? ?????? ?????? 3km ???)??? ????????? ??????
    public final static double REFERANCE_LAT = 1 / 109.958489129649955;
    public final static double REFERANCE_LNG = 1 / 88.74;
    public final static double REFERANCE_LAT_X3 = 3 / 109.958489129649955;
    public final static double REFERANCE_LNG_X3 = 3 / 88.74;
    public final static double REFERANCE_LAT_X5 = 5 / 109.958489129649955;
    public final static double REFERANCE_LNG_X5 = 5 / 88.74;
    public final static double REFERANCE_LAT_X15 = 18 / 109.958489129649955;
    public final static double REFERANCE_LNG_X15 = 18 / 88.74;

    public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
        boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X15;
        boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X15;
        return withinSightMarkerLat && withinSightMarkerLng;
    }

    // ???????????? ?????????????????? ????????? ???????????? ??????
    private void freeActiveMarkers() {
        if (activeMarkers == null) {
            activeMarkers = new Vector<Marker>();
            return;
        }
        for (Marker activeMarker : activeMarkers) {
            activeMarker.setMap(null);
        }
        activeMarkers = new Vector<Marker>();
    }
//===============================================================

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        geocoder = new Geocoder(this);
        this.naverMap = naverMap;

        odsayService = ODsayService.init(getApplicationContext(), "d/F477b1GZGKZgWCv8LynPEERmoxCdE1jSOojHzKNPM");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        naverMap.setLayerGroupEnabled(LAYER_GROUP_TRANSIT, true);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        LatLng initialPosition = new LatLng(37.506855, 127.066242);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setLogoGravity(Gravity.RIGHT | Gravity.BOTTOM);

//        if(fw_pos_path != null){
//            pathOver = new PathOverlay();
//            pathOver.setCoords(Arrays.asList(
//                    new LatLng(37.57152, 126.97714),
//                    new LatLng(37.56607, 126.98268),
//                    new LatLng(37.56445, 126.97707),
//                    new LatLng(37.55855, 126.97822)
//            ));
//            pathOver.setMap(naverMap);
//        }


        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setPosition(new LatLng(37.5666102, 126.9783881));
        infoWindow.setAdapter(new InfoWindowAdapter(this));
        infoWindow.setOnClickListener(overlay -> {
            infoWindow.close();
            return true;
        });


        //infoWindow.open(naverMap);//??????????????? ?????? ???

//
//        LocationButtonView locationButtonView = findViewById(R.id.navermap_location_button);
//        locationButtonView.setMap(naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);


        naverMap.setOnMapClickListener((point, coord) -> {

            stopService();

            odsay = new Odsay();
            bus_info = new Odsay();
            StationName = "";
            x = "";
            y = "";
            y = String.valueOf(coord.latitude);
            x = String.valueOf(coord.longitude);

            mapsPointPos = coord;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    odsayService.requestPointSearch(x, y, "5", "1:2", odsay.pointSearch);
                    if ((odsay.getCount() >= 1) != true) {
                        Log.d("========if=========x: ", x + "  y: " + y);
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("????????? ?????? : " + odsay.getCount());
                            if (odsay.getCount() >= 1) {
                                StationLists = new StationList[odsay.getStationList().length];
                                StationLists = odsay.getStationList();
                                for (int i = 0; i < odsay.getCount(); i++) {

                                    System.out.println("????????? ??????" + StationLists[i].getStationClass());
                                    System.out.println("????????? ????????? ??????" + StationLists[i].getStationName());
                                    System.out.println("????????? ????????? ?????????" + StationLists[i].getStationID());
                                    System.out.println("????????? ????????? " + StationLists[i].getX());
                                    System.out.println("????????? ????????? " + StationLists[i].getY());
                                    StationName = StationLists[i].getStationName();
                                    System.out.println("????????? ?????? : " + StationName + "\n" + "????????????????????? ????????? ?????? : " + odsay.getCount());
                                    StationId = StationLists[i].getStationID();
                                    stationClass = StationLists[i].getStationClass();

                                    if (stationClass == 1) {
                                        odsayService.requestBusStationInfo(String.valueOf(StationLists[i].getStationID()), odsay.busStationInfo);
                                        setBus_info_window();
                                    } else if (stationClass == 2) {
                                        odsayService.requestSubwayStationInfo(String.valueOf(StationId), odsay.subwayStationInfo);
                                        setSubway_info_window();
                                    }

                                }
//                                infoWindow.setPosition(coord);
//                                infoWindow.open(naverMap);

                            } else {
                                String myAddress = getCurrentAddress(coord.latitude, coord.longitude);
                                setPlace_info_window(myAddress);

//                                infoWindow.setPosition(coord);
//                                infoWindow.open(naverMap);
                            }

                        }
                    }, 450);
                }
            }, 200);

//            odsayService.requestPointSearch(x, y, "5", "1:2", odsay.pointSearch);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("????????? ?????? : " + odsay.getCount());
//                    if (odsay.getCount() >= 1) {
//                        StationLists = new StationList[odsay.getStationList().length];
//                        StationLists = odsay.getStationList();
//                        for (int i = 0; i < odsay.getCount(); i++) {
//
//                            System.out.println("????????? ??????" + StationLists[i].getStationClass());
//                            System.out.println("????????? ????????? ??????" + StationLists[i].getStationName());
//                            System.out.println("????????? ????????? ?????????" + StationLists[i].getStationID());
//                            System.out.println("????????? ????????? " + StationLists[i].getX());
//                            System.out.println("????????? ????????? " + StationLists[i].getY());
//                            StationName = StationLists[i].getStationName();
//                            System.out.println("????????? ?????? : " + StationName + "\n" + "????????????????????? ????????? ?????? : " + odsay.getCount());
//                            StationId = StationLists[i].getStationID();
//                            stationClass = StationLists[i].getStationClass();
//
//                            if (stationClass == 1) {
//                                odsayService.requestBusStationInfo(String.valueOf(StationLists[i].getStationID()), odsay.busStationInfo);
//                            } else if (stationClass == 2) {
//                                odsayService.requestSubwayStationInfo(String.valueOf(StationId), odsay.subwayStationInfo);
//                            }
//
//                        }
//                        infoWindow.setPosition(coord);
//                        infoWindow.open(naverMap);
//
//                    } else {
//                        infoWindow.setPosition(coord);
//                        infoWindow.open(naverMap);
//                    }
//
//
//                }
//            }, 2500);


//            new Thread() {
//                @Override
//                public void run() {
//                    Document doc;
//                    try {
//                        System.out.println("count : " + point_search.getCount());
//                        busStationLists = new BusStationList[point_search.getCount()];
//                        busStationLists = point_search.getBusStationList();
//                        for (int i = 0; i < point_search.getCount(); i++){
//
//                            System.out.println("????????? ??????" + busStationLists[i].getStationClass());
//                            System.out.println("????????? ????????? ??????" + busStationLists[i].getStationName());
//                            System.out.println("????????? ????????? ?????????" + busStationLists[i].getStationID());
//                            System.out.println("????????? ????????? " + busStationLists[i].getX());
//                            System.out.println("????????? ????????? " + busStationLists[i].getY());
//                            busStationName = busStationLists[i].getStationName();
//
//                        }
//                        //10????????? ????????? ??????
//                        Thread.sleep(600);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();


//            if(point_search.getCount() >= 1 ){
//                System.out.println("?????? ?????????");
//                busStationLists = new BusStationList[point_search.getCount()];
//                for (int i = 0; i <point_search.getCount() ; i++){
//                    busStationLists[i] = new BusStationList(point_search.getBusStationList()[i].getStationClass(), point_search.getBusStationList()[i].getStationName(),point_search.getBusStationList()[i].getStationID(), point_search.getBusStationList()[i].getX(),point_search.getBusStationList()[i].getY());
//                    System.out.println("?????? " + busStationLists[i]);
//                }
//            }else{
//                System.out.println("?????? ?????????");
//
//            }


//
//            String name = point_search.getBusStationList()[0].getStationName();
//            System.out.println("???????????? ?????? " + name);

        });


        markersPosition = new Vector<LatLng>();

        //=======
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    markersPosition.clear();
                    System.out.println("===========?????? ??????====================" + markersPosition.toString());
                    mapTread.run();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, new Date(), 5000);
        //========

//        for (int x = 0; x < 100; ++x) {
//            for (int y = 0; y < 100; ++y) {
//                markersPosition.add(new LatLng(
//                        initialPosition.latitude - (REFERANCE_LAT * x),
//                        initialPosition.longitude + (REFERANCE_LNG * y)
//                ));
//                markersPosition.add(new LatLng(
//                        initialPosition.latitude + (REFERANCE_LAT * x),
//                        initialPosition.longitude - (REFERANCE_LNG * y)
//                ));
//                markersPosition.add(new LatLng(
//                        initialPosition.latitude + (REFERANCE_LAT * x),
//                        initialPosition.longitude + (REFERANCE_LNG * y)
//                ));
//                markersPosition.add(new LatLng(
//                        initialPosition.latitude - (REFERANCE_LAT * x),
//                        initialPosition.longitude - (REFERANCE_LNG * y)
//                ));
//            }
//        }
        // ????????? ?????? ?????? ?????? ?????? ?????????
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // ????????? ?????????????????? ???????????? ????????????????????? ?????? ??????
                int count = 0;
                LatLng currentPosition = getCurrentPosition(naverMap);
                String userName;
//               for(int i = 0; i <= friendMarker.size();i++){
//                   LatLng markerPos = friendMarker.get(i).getMarkerPos();
//                   if(!withinSightMarker(currentPosition,markerPos)){
//                       continue;
//                   }
//                   Marker marker = new Marker();
//                   marker.setIconTintColor(Color.RED);
//                   marker.setPosition(friendMarker.get(i).getMarkerPos());
//                   marker.setCaptionText(friendMarker.get(i).getMarkerUserName());
//                   marker.setMap(naverMap);
//                   activeMarkers.add(marker);
//               }
                for (LatLng markerPosition : markersPosition) {
                    if (!withinSightMarker(currentPosition, markerPosition)) {
                        continue;
                    }
                    Marker marker = new Marker();
                    marker.setHideCollidedMarkers(true);

//                    System.out.println("==============="+markerPosition.toString()+"======??????====== :"+friendMarkerNameList.get(count));
//                    marker.setIcon(OverlayImage.fromResource(R.drawable.image_profile));
                    marker.setIconTintColor(Color.RED);
                    marker.setPosition(friendMarker.get(count).getMarkerPos());
                    marker.setCaptionText(friendMarker.get(count).getMarkerUserName());

//                    marker.setHideCollidedCaptions(true);
                    marker.setMap(naverMap);
                    activeMarkers.add(marker);
                    count++;
//                    System.out.println("=======????????? ??????========");
                }
            }
        });
    }

//
//    class TestThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            Looper.prepare();
//
//            Looper.loop();
//        }
//    }


    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
                return;
            } else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        gpsTracker = new GpsTracker(Maps_Activity.this);

        fw_frag = new Find_Way_Frag();
        mapFindWay = new Map_Find_way();
        pathOverlay = new PathOverlay();

        subway_time = new Subway_Info_Time();

        mapTread = new MapFriendMarkerTread();

        friendMarker = new ArrayList<>();

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String myAddress = getCurrentAddress(latitude, longitude);
        String[] add = myAddress.split(" ");
        drawerInit(myAddress);

        //?????????
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        bundleFw = new Bundle();

        ft = getSupportFragmentManager().beginTransaction();

        Response.Listener<String> responseListenerPos = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("????????????" + response);
                    //JSONObject jsonObject = new JSONObject( response );
                    //boolean success = jsonObject.getBoolean( "success" );
                    boolean success = Boolean.parseBoolean(response);
                    System.out.println(success);
                    //???????????? ?????????
                    if (success) {
                        System.out.println("???????????? ??????");
                        //???????????? ?????????
                    } else {
                        System.out.println("???????????? ??????");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (userGhost == 0) {
                    UpdatePosRequest updatePosRequest = new UpdatePosRequest(k_code, gpsTracker.getLongitude(), gpsTracker.getLatitude(), responseListenerPos);
                    RequestQueue queuePos = Volley.newRequestQueue(Maps_Activity.this);
                    queuePos.add(updatePosRequest);
                }
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.schedule(timerTask, 0, 3000);
                System.out.println(k_code);
                System.out.println("???" + gpsTracker.getLongitude());
                System.out.println("???" + gpsTracker.getLatitude());
            }
        }, 30000);


        //?????????
        //?????? ?????? ????????? ????????? ?????? ??????(?????? ???????????? ??????)
        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //??? ????????? ???????????? ?????????
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);
        drawerView = (View) findViewById(R.id.drawer_main);
        menuIbtn = (ImageButton) findViewById(R.id.menu_ibtn);
        temText = (TextView) findViewById(R.id.drawer_tem_text);
        fineText = (TextView) findViewById(R.id.drawer_fine_text);
        ultraText = (TextView) findViewById(R.id.drawer_ultra_text);
        covidText = (TextView) findViewById(R.id.drawer_covid_text);
        weatherImage = (ImageView) findViewById(R.id.drawer_weather_img);
        searchIbtn = (ImageButton) findViewById(R.id.main_search_ibtn);
        profile = (ImageView) findViewById(R.id.drawer_profile_img); // ???????????? ???????????? ?????????
        nickName = (TextView) findViewById(R.id.drawer_profile_name); // ???????????? ?????????
        areaText = (TextView) findViewById(R.id.drawer_area_text);
        editText = (EditText) findViewById(R.id.main_search_et);
        findWayIbtn = (ImageButton) findViewById(R.id.main_find_way_ibtn);  // ????????? ????????? ??????

        upPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slide);
        view_Header = (ConstraintLayout) findViewById(R.id.view_header);
        viewPager = (ViewPager2) findViewById(R.id.view_pager);
        indicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
        find_way_page = (ConstraintLayout) findViewById(R.id.view_find_way_lay);
        vEditChangeFindIbtn = (ImageButton) findViewById(R.id.posEdit_change_ibtn);
        vFindIbtn = (ImageButton) findViewById(R.id.view_find_way_ibtn);
        sPosEdit = (EditText) findViewById(R.id.start_pos_et);
        ePosEdit = (EditText) findViewById(R.id.end_pos_et);


        subway_info_window = (ConstraintLayout) findViewById(R.id.subway_info_window);//????????? ?????????

        subway_info_title = (TextView) findViewById(R.id.subway_info_title_tv);
        subway_info_direction = (TextView) findViewById(R.id.subway_info_address_tv);
        subway_info_right_station = (TextView) findViewById(R.id.subway_info_right_station_tv);
        subway_info_this_station = (TextView) findViewById(R.id.subway_info_this_station_tv);
        subway_info_left_station = (TextView) findViewById(R.id.subway_info_left_station_tv);

        subway_info_left_arriveNext_name = (TextView) findViewById(R.id.subway_info_left_arriveNext_station_tv);
        subway_info_left_arriveNext_time = (TextView) findViewById(R.id.subway_info_left_arriveNext_time_tv);
        subway_info_left_arriveSoon_name = (TextView) findViewById(R.id.subway_info_left_arriveSoon_station_tv);
        subway_info_left_arriveSoon_time = (TextView) findViewById(R.id.subway_info_left_arriveSoon_time_tv);

        subway_info_right_arriveNext_name = (TextView) findViewById(R.id.subway_info_right_arriveNext_station_tv);
        subway_info_right_arriveNext_time = (TextView) findViewById(R.id.subway_info_right_arriveNext_time_tv);
        subway_info_right_arriveSoon_name = (TextView) findViewById(R.id.subway_info_right_arriveSoon_station_tv);
        subway_info_right_arriveSoon_time = (TextView) findViewById(R.id.subway_info_right_arriveSoon_time_tv);

        bus_info_window = (ConstraintLayout) findViewById(R.id.bus_info_window); //?????? ?????????

        bus_info_title = (TextView) findViewById(R.id.bus_info_title_tv);
        bus_info_direction = (TextView) findViewById(R.id.bus_info_direction_tv);
        bus_info_number = (TextView) findViewById(R.id.bus_info_number_tv);

        place_info_window = (ConstraintLayout) findViewById(R.id.place_info_window); // ?????? ?????????

        place_info_title = (TextView) findViewById(R.id.place_info_title_tv);
        place_info_address = (TextView) findViewById(R.id.place_info_address_tv);

        place_info_start_point = (ImageView) findViewById(R.id.place_info_startFind_ibtn);
        place_info_end_point = (ImageView) findViewById(R.id.place_info_endFind_ibtn);

        subway_info_bookmarkStar = (ImageButton) findViewById(R.id.subway_info_bookmarkStar_ib);
        bus_info_bookmarkStar = (ImageButton) findViewById(R.id.bus_info_bookmarkStar_ib);
        place_info_bookmarkStar = (ImageButton) findViewById(R.id.place_info_bookmarkStar_ib);

        findWayOverlayClearIBtn = (ImageButton) findViewById(R.id.main_findWay_overlay_clear_ibtn);


        ImageButton friendTest_btn = (ImageButton) findViewById(R.id.main_findWay_friend_ibtn);
        friendTest_btn.setOnClickListener(onClickListener);


        drawerMenu[0] = (Button) findViewById(R.id.drawer_menu_1);
        drawerMenu[1] = (Button) findViewById(R.id.drawer_menu_2);
        drawerMenu[2] = (Button) findViewById(R.id.drawer_menu_3);
        drawerMenu[3] = (Button) findViewById(R.id.drawer_menu_4);
        drawerMenu[4] = (Button) findViewById(R.id.drawer_menu_5);
        drawerMenu[5] = (Button) findViewById(R.id.drawer_menu_6);

        headerProfile = (ImageView) findViewById(R.id.view_header_profile);
        headerName = (TextView) findViewById(R.id.view_header_name);
        headerCode = (TextView) findViewById(R.id.view_header_code);
        headerGhostBtn = (ImageView) findViewById(R.id.view_header_ghost_btn);
        headerSettingBtn = (ImageView) findViewById(R.id.view_header_setting_btn);


        //??????????????? ??????????????? ????????? ??????
        drawerLayout.setDrawerListener(drawerListener);

        //????????? ?????? ????????? ??????
        menuIbtn.setOnClickListener(onClickListener);
        drawerMenu[0].setOnClickListener(onClickListener);
        drawerMenu[1].setOnClickListener(onClickListener);
        drawerMenu[2].setOnClickListener(onClickListener);
        drawerMenu[3].setOnClickListener(onClickListener);
        drawerMenu[4].setOnClickListener(onClickListener);
        drawerMenu[5].setOnClickListener(onClickListener);

        headerGhostBtn.setOnClickListener(onClickListener);
        headerSettingBtn.setOnClickListener(onClickListener);

        keyboardmanager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        viewSwitch = false;

        new BackgroundTask_Subway().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new BackgroundTask_Bus().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new BackgroundTask_location().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new BackgroundTask_Promise().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new BackgroundTask_Friend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        bus_info_bookmarkStar.setOnClickListener(onClickListener);
        subway_info_bookmarkStar.setOnClickListener(onClickListener);
        place_info_bookmarkStar.setOnClickListener(onClickListener);

        place_info_start_point.setOnClickListener(onClickListener);
        place_info_end_point.setOnClickListener(onClickListener);


//============================================================================================SlidingUpPanel
//        upPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
//            @Override
//            public void onPanelSlide(View panel, float slideOffset) {
////                Log.d("upPanel ?????? ", "onPanelSlide, offset " + slideOffset);
//            }
//
//            @Override
//            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
//                Log.d("upPanel ????????? ?????? ", "onPanelStateChanged " + newState);
//                if (newState == (SlidingUpPanelLayout.PanelState.COLLAPSED) && view_Header.getVisibility() == View.GONE) {
//                    view_Header.setVisibility(View.VISIBLE);
//                    viewPager.setVisibility(View.VISIBLE);
//                    indicator.setVisibility(View.VISIBLE);
//                    find_way_page.setVisibility(View.GONE);
//                    pathOverlay.setMap(null);
////                    Log.d("??????", "");
//                }
//            }
//        });

        searchIbtn.setOnClickListener(onClickListener); // ?????? ?????? ?????????
        findWayIbtn.setOnClickListener(onClickListener);
        vFindIbtn.setOnClickListener(onClickListener);
        vEditChangeFindIbtn.setOnClickListener(onClickListener);

        findWayOverlayClearIBtn.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        name = intent.getStringExtra("?????????");
        address = intent.getStringExtra("?????????");
        k_code = intent.getLongExtra("??????", 0);
        nickName.setText(name); // ???????????? ????????? ?????????
        Glide.with(this).load(address).circleCrop().into(profile); // ???????????? ????????? ?????????

        userlist = intent.getStringExtra("userlist"); //?????? ??????
        friendlist = intent.getStringExtra("friendlist"); //?????? ??????
        promiselist = intent.getStringExtra("promiselist"); //?????? ??????

        try {
            JSONObject jsonObject = new JSONObject(userlist);
            System.out.println(userlist);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userGhost = object.getInt("K_ghost");
                System.out.println("???????????? ????????? ??????" + userGhost);

                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(friendlist);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            long userCode;
            long friendCode;
            int friendStatus;
            String friendName;
            String friendProfile;
            int friendGhost;
            double fLat, fLong;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userCode = object.getLong("F.K_code1");
                friendCode = object.getLong("F.K_code2");
                friendStatus = object.getInt("F.F_status");
                friendName = object.getString("K.K_name");
                friendProfile = object.getString("K.K_profile");
                friendGhost = object.getInt("K.K_ghost");
                fLat = object.getDouble("K.K_lat");
                fLong = object.getDouble("K.K_long");

                if (userCode == k_code) {
                    friendCodeList.add(friendCode);
                    friendNameList.add(friendName);
                    friendStatusList.add(friendStatus);
                    friendProfileList.add(friendProfile);
                    friendGhostList.add(friendGhost);
                    friendLatList.add(fLat);
                    friendLongList.add(fLong);
                }

                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!checkLocationServiceStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }

        switch (userGhost) {
            case 1:
                headerGhostBtn.setImageResource(R.drawable.ghost1);
                break;

            case 0:
                headerGhostBtn.setImageResource(R.drawable.ghost2);
                break;
        }
        headerName.setText(name);
//      headerCode.setText();
        Glide.with(this).load(address).circleCrop().into(headerProfile);


        //???????????? ??????
        viewPager = findViewById(R.id.view_pager);
        ViewPager_Item_Adapter itemAdapter = new ViewPager_Item_Adapter(this, friendNameList, friendProfileList, friendCodeList, friendStatusList);
        viewPager.setAdapter(itemAdapter);

        viewPager.setOnClickListener(onClickListener);

        indicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
        indicator.setViewPager2(viewPager);


        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i) {
                    case KeyEvent.KEYCODE_ENTER:
                        search_location();
                        break;
                }
                editText.requestFocus();
                return false;
            }
        });
        upPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if (itemAdapter.getEvent() == true) {
                    itemAdapter.getDialog().cancel();
                }
                if (newState == (SlidingUpPanelLayout.PanelState.COLLAPSED) && view_Header.getVisibility() == View.GONE) {
                    view_Header.setVisibility(View.VISIBLE);
                    if (viewSwitch == false) {
                        viewPager.setVisibility(View.VISIBLE);
//                        viewDetail.setVisibility(View.GONE);
                        indicator.setVisibility(View.VISIBLE);
//                        pathOverlay.setMap(null);
                    } else {
                        viewPager.setVisibility(View.GONE);
//                        viewDetail.setVisibility(View.VISIBLE);
                    }
                    find_way_page.setVisibility(View.GONE);
                    subway_info_window.setVisibility(View.GONE);
                    bus_info_window.setVisibility(View.GONE);
                    place_info_window.setVisibility(View.GONE);

                    bus_info_bookmarkStar.setColorFilter(Color.GRAY);
                    place_info_bookmarkStar.setColorFilter(Color.GRAY);
                    subway_info_bookmarkStar.setColorFilter(Color.GRAY);


                }
            }
        });

        startService();


//===================================================================================================

        TabLayout tabs = (TabLayout) findViewById(R.id.view_transport_table);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                bundleFw.putInt("TabPos", position);
                fw_frag.setArguments(bundleFw);

                frag_set(fw_frag);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//===================================================================================================
//
//
//                    fw_frag = (Find_Way_Frag) getSupportFragmentManager().findFragmentById(R.id.view_fw_container);// ????????? ??????????????? ??????
//                    Bundle bundle = new Bundle(1);
//                    bundle.putString("odsay","TEST");
//                    fw_frag.setArguments(bundle);
//
//
//        if(upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED&&path!=null){
//
//            view_recyclerView = (RecyclerView) findViewById(R.id.view_RecyclerView);
//
//            linearLayoutManager = new LinearLayoutManager(this);
//
//            view_recyclerView.setLayoutManager(linearLayoutManager);
//
//            fArrayList = new ArrayList<>();
//            find_way_listAdapter = new Find_way_listAdapter(fArrayList);
//            view_recyclerView.setAdapter(find_way_listAdapter);
//            Log.d("????????? ???????????????","");
//
//            Find_way_Data fwData = new Find_way_Data(9,R.color.yellow,24,R.color.black,24, 56,R.color.red);
////
////            Find_way_Data way_data = new Find_way_Data();
//        }
//        if(upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
//
//            Log.d("????????? ????????? ???","");
//            view_recyclerView = (RecyclerView) findViewById(R.id.view_RecyclerView);
//            Log.d("????????? ????????????","");
//            linearLayoutManager = new LinearLayoutManager(this);
//            Log.d("?????????????????? ?????? ??????","");
//            view_recyclerView.setLayoutManager(linearLayoutManager);
//            Log.d("???????????? ????????? ??????","");
//            fArrayList = new ArrayList<>();
//            find_way_listAdapter = new Find_way_listAdapter(fArrayList);
//            Log.d("????????? ?????? ??????","");
//            view_recyclerView.setAdapter(find_way_listAdapter);
//            Log.d("?????? ????????? ?????? ??????","");
//
//            Find_way_Data fwData = new Find_way_Data(9,R.color.yellow,24,R.color.black,24, 56,R.color.red);
//            fArrayList.add(fwData);
//            find_way_listAdapter.notifyDataSetChanged();
////
////            Find_way_Data way_data = new Find_way_Data();
//        }

    }

    @Override
    public void onBackPressed() {

        long backPressedTime = 0;
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
            upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else if (drawerEnabled) {
            drawerLayout.closeDrawer(drawerView);
        } else if (0 <= System.currentTimeMillis() && 2000 >= System.currentTimeMillis()) {
            finish();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "?????? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }


    private void drawerInit(String myAddress) {
        add = myAddress.split(" ");
        try {
            area = add[0];
        } catch (Exception e) {
            area = add[1];
        }
        try {
            if (add[1].equals("???????????????")) {
                city = add[1];
            } else {
                city = add[2];
            }
        } catch (Exception e) {

        }

        final String temURL = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=" + area + " " + city + "??????"; //???????????? ??? ??????(1)
        final String covidURL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=?????????19"; //???????????? ??? ??????(2)
        //???????????? ????????? ????????? ?????? ?????? ??????
        final Bundle bundle = new Bundle();

        //???????????? ?????? ????????? ??????
        new Thread() {
            @Override
            public void run() {
                Document doc;
                try {
                    //??? ????????? ????????? ??????
                    doc = Jsoup.connect(temURL).get();

                    //????????????
                    Elements temperature = doc.select(".temperature_text");
                    //????????????, ??????????????? ??????
                    Elements dust = doc.select(".report_card_wrap");
                    //??????
                    Elements wt = doc.select(".weather_main");

                    //??? ????????? ????????? ??????
                    doc = Jsoup.connect(covidURL).get();

                    Elements covid = doc.select(".status_info");

                    //????????? ?????? ??? ?????????
                    tem = temperature.get(0).text().substring(5);
                    allDust = dust.get(0).text();
                    String[] array = allDust.split(" ");
                    fineDust = array[1];
                    ultraFineDust = array[3];
                    weather = wt.get(0).text();
                    array = covid.get(0).text().split(" ");
                    covidNum = array[2];

                    drawer_input();

                    //10????????? ????????? ??????
                    Thread.sleep(600000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void drawer_input() {
        temText.setText(tem + "C");
        areaText.setText(city);
        covidText.setText("?????? ????????? ????????? ??? '" + covidNum + "' ???");

        switch (fineDust) {
            case "??????":
                fineText.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case "??????":
                fineText.setTextColor(getResources().getColor(R.color.green));
                break;
            case "??????":
                fineText.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "????????????":
                fineText.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                fineText.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        switch (ultraFineDust) {
            case "??????":
                ultraText.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case "??????":
                ultraText.setTextColor(getResources().getColor(R.color.green));
                break;
            case "??????":
                ultraText.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "????????????":
                ultraText.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                ultraText.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        switch (weather) {
            case "??????":
                weatherImage.setImageResource(R.drawable.sunny);
                break;
            case "??????":
            case "????????????":
                weatherImage.setImageResource(R.drawable.cloud);
                break;
            case "???":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "???":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            default:
//                weatherImage.setImageResource(R.drawable.ic_baseline_block);
                Log.d("?????? ???", weather);
        }
    }

    //????????? ????????? ?????????
    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            System.out.println("opening");
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            System.out.println("open");
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            System.out.println("close");
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    protected ArrayList setTextList() {

        ArrayList<String> itemList = new ArrayList();
        itemList.add("Page 1");
        itemList.add("Page 2");
        itemList.add("Page 3");
        itemList.add("Page 4");
        itemList.add("Page 5");

        return itemList;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    //????????? ?????? ????????? ?????? ?????????
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {//?????? ????????? ????????????, ????????? ????????? ?????? ?????????????????? ??????
            boolean check_result = true;

            //???????????? ?????? ??????????????? ??????
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {//????????? ????????????
                    check_result = false;//false??? ??????
                    break;
                }
            }

            if (check_result) {

            } else {//????????? ???????????? ????????????
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(Maps_Activity.this, "????????? ?????????????????????. ?????? ?????? ???????????? ????????? ??????????????????", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(Maps_Activity.this, "????????? ?????????????????????. ??????(??? ??????)?????? ????????? ??????????????????", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    boolean checkRunTimePermission() {//????????? ????????? ??????
        //?????? ????????? ??????
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {//???????????? ??????????????????
            return true;
        } else {//????????? ?????????
            Toast.makeText(Maps_Activity.this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
            do {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Maps_Activity.this, REQUIRED_PERMISSIONS[0])) {//?????? ????????? ??????????????? ?????????
                    ActivityCompat.requestPermissions(Maps_Activity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);//??????????????? ?????? ??????
                } else {//????????? ???????????????
                    ActivityCompat.requestPermissions(Maps_Activity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);//??????????????? ?????? ??????
                }
                hasFineLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            } while (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public String getCurrentAddress(double latitude, double longitude) {//?????? ??????
        geocoder = new Geocoder(this, Locale.getDefault());//??????????????? ?????? ???????????? ?????? ??????

        List<Address> addresses;//????????? ????????? ??????????????? ??????


        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 7);//??????????????? ???????????? ??????????????? ?????? ??????
        } catch (IOException ioException) {//?????????
            Toast.makeText(Maps_Activity.this, "???????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
            return "???????????? ????????? ????????????";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(Maps_Activity.this, "????????? GPS ??????", Toast.LENGTH_SHORT).show();
            return "????????? GPS ??????";
        }

        if (addresses == null || addresses.size() == 0) {//?????? ????????? ?????????
            Toast.makeText(this, "?????? ?????????", Toast.LENGTH_SHORT).show();
            return "?????? ?????????";
        }

        Address address = addresses.get(0);//??????????????? ????????? ??????
        return address.getAddressLine(0).toString() + "\n";//??????
    }

    private void showDialogForLocationServiceSetting() {//GPS ???????????? ??????????????? ???????????? ????????? ?????? ???????????????

        AlertDialog.Builder builder = new AlertDialog.Builder(Maps_Activity.this);//??????????????? ??????
        builder.setTitle("?????? ????????? ????????????");//????????? ??????
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n" + "?????? ????????? ???????????????????");//?????? ??????
        builder.setCancelable(true);//?????? ??????
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {//?????? ?????? ??????
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);//?????? ????????? ?????????
            }
        });

        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {//?????? ?????? ??????
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();//??????????????? ??????
            }
        });
        builder.create().show();//????????? ????????????
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//
//            case GPS_ENABLE_REQUEST_CODE:
//                if (checkLocationServiceStatus()) {
//                    if (checkLocationServiceStatus()) {
//                        Log.d("???????????? ??????", "true");
//                        checkRunTimePermission();
//                        return;
//                    }
//                }
//
//                break;
//        }
//    }

    public boolean checkLocationServiceStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void nameToPos(String sEtPosName, String eEtPosName) {
        stopService();


        List<Address> sAddressList = null;
        List<Address> eAddressList = null;
        String sLatitude = "";
        String sLongitude = "";
        String eLatitude = "";
        String eLongitude = "";

        if (sEtPosName.equals("??? ??????")) {

            sLatitude = String.valueOf(gpsTracker.getLatitude());
            sLongitude = String.valueOf(gpsTracker.getLongitude());
            eLatitude = String.valueOf(friendLat);
            eLongitude = String.valueOf(friendLong);


            sLatLngPos = new LatLng(Double.valueOf(sLatitude), Double.valueOf(sLongitude));
            eLatLngPos = new LatLng(Double.valueOf(eLatitude), Double.valueOf(eLongitude));
        } else {

            String reNameStartEditTxt = sEtPosName.replace(" ", "_");
            String reNameEndEditTxt = eEtPosName.replace(" ", "_");

            try {
                sAddressList = geocoder.getFromLocationName(reNameStartEditTxt, 10);
                eAddressList = geocoder.getFromLocationName(reNameEndEditTxt, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] sSplitStr = sAddressList.get(0).toString().split(",");
            String[] eSplitStr = eAddressList.get(0).toString().split(",");

            sLatitude = sSplitStr[10].substring(sSplitStr[10].indexOf("=") + 1);
            sLongitude = sSplitStr[12].substring(sSplitStr[12].indexOf("=") + 1);

            eLatitude = eSplitStr[10].substring(eSplitStr[10].indexOf("=") + 1);
            eLongitude = eSplitStr[12].substring(eSplitStr[12].indexOf("=") + 1);


            sLatLngPos = new LatLng(Double.valueOf(sLatitude), Double.valueOf(sLongitude));
            eLatLngPos = new LatLng(Double.valueOf(eLatitude), Double.valueOf(eLongitude));
        }

        Handler handler = new Handler();

        String finalSLongitude = sLongitude;
        String finalSLatitude = sLatitude;
        String finalELongitude = eLongitude;
        String finalELatitude = eLatitude;


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                odsayService.requestSearchPubTransPath(finalSLongitude, finalSLatitude, finalELongitude, finalELatitude, "0", "0", "0", mapFindWay.Find_way);
                handler.postDelayed(new Runnable() {                                        //handler ?????? ????????? (0.45???)????????? ??????
                    @Override
                    public void run() {
//                path = mapFindWay.getPath_s();
                        result = mapFindWay.getResult();
                        System.out.println(result + "=========================================");
//                System.out.println("-----------------------result"+result);
//                FragmentTransaction tf = getSupportFragmentManager().beginTransaction();
//                        tf.detach(fw_frag).attach(fw_frag).commit();
//                System.out.println("======================Test========================="+path[0]);

                        if (result != null) {
                            LatLngBounds latLngBounds = new LatLngBounds(sLatLngPos, eLatLngPos);
//                    Marker marker = new Marker();
//                    marker.setPosition(eLatLngPos);
//                    marker.setMap(naverMap);


                            CameraUpdate cameraUpdate = CameraUpdate.fitBounds(latLngBounds, 100, 130, 100, 1100);
//                    cameraUpdate.animate(CameraAnimation.Easing);

                            naverMap.moveCamera(cameraUpdate);

                            bundleFw.putString("odsay", result.toString());
                            bundleFw.putString("StartName", String.valueOf(sPosEdit.getText()));
                            bundleFw.putString("EndName", String.valueOf(ePosEdit.getText()));
                            fw_frag.setArguments(bundleFw);
                            frag_set(fw_frag);
                            meResult = result;


                        }
                        startService();
                    }
                }, 500);
            }
        }, 100);


//        path = mapFindWay.getPath_s();

//
//        System.out.println("======================Test========================="+getPath().toString());

//            System.out.print("\n sLati"+latitude+"sLong"+longitude+"\n");
        //odsayService.requestSearchPubTransPath("126.8881529057685","37.49185398304374",x,y,"0","0","0", mapFind_way.Find_way);

//        LatLng Pos = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
//        double[] pos = new double[2];
//        pos[0]=Double.parseDouble(sLatitude);
//        pos[1]=Double.parseDouble(sLongitude);
//        return pos;
    }


    private void search_location() {
        if (editText.length() == 0) {
            editText.requestFocus();
        } else {
            keyboardmanager.hideSoftInputFromWindow(sPosEdit.getWindowToken(), 0);
            String str = editText.getText().toString();
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocationName(
                        str,//??????
                        10 // ???????????? ?????? ??????
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(addressList.get(0).toString());
            //????????? ???????????? split
            String[] splitStr = addressList.get(0).toString().split(",");
            String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // ??????
            System.out.println(address);

            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // ??????
            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // ??????
            System.out.println(latitude);
            System.out.println(longitude);

            // ??????(??????, ??????) ??????
            LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            // ?????? ??????
            Marker marker = new Marker();
            marker.setPosition(point);
            // ?????? ??????
            marker.setMap(naverMap);

            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
            naverMap.moveCamera(cameraUpdate);
        }
    }

    public static Drawable getTintedDrawable(@NonNull final Context context,
                                             @DrawableRes int drawableRes, @ColorRes int colorRes) {
        Drawable d = ContextCompat.getDrawable(context, drawableRes);
        d = DrawableCompat.wrap(d);
        DrawableCompat.setTint(d.mutate(), ContextCompat.getColor(context, colorRes));
        return d;
    }

    public void frag_set(Find_Way_Frag fw_frag) {
        if (meResult == null && result != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.view_fw_container, fw_frag).commit();
        } else if (meResult != null || result != meResult && result != null) {
            fw_frag = (Find_Way_Frag) getSupportFragmentManager().findFragmentById(R.id.view_fw_container);

            ft.detach(fw_frag).commitNowAllowingStateLoss();
            ft.attach(fw_frag).setPrimaryNavigationFragment(fw_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String fName;
        long fCode;

        if (intent.getIntExtra("fFlag", 0) == 1) {
            fName = intent.getStringExtra("fName");
            fCode = intent.getLongExtra("fCode", 0);
            Log.d("=====1flag====",""+fName);
            wayToFriend(fName, fCode);
        } else if (intent.getIntExtra("fFlag", 0) == 2) {
            fCode = intent.getLongExtra("fCode", 0);
            Log.d("=====2flag====",""+fCode);
            locationToFriend(fCode);
        }

        if (intent.getStringExtra("jObject") != null) {
            fw_pos_path = intent.getStringExtra("jObject");
            try {
                stopService();
                JSONObject pos = new JSONObject(fw_pos_path);

                ArrayList<LatLng> total_finde_pos_array = new ArrayList<>();
                try {
//                    total_finde_pos_array.add(new LatLng());
                    mapObjectPos = pos.getJSONObject("info").getString("mapObj");
                    String split_mapObject[] = mapObjectPos.split("@");

//
                    mapObject = "0:0@" + pos.getJSONObject("info").getString("mapObj");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            odsayService.requestLoadLane(mapObject, mapFindWay.LoadLane);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    MapDraw();

                                    startService();
                                }
                            }, 450);

                        }
                    }, 200);

                } catch (Exception e) {
//                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (intent.getIntExtra("openFindWay", 0) == 1) {
            findWayIbtn.callOnClick();
        }
//        if (upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ) {
//            upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//        }

    }


    class MapFriendMarkerTread {
        public MapFriendMarkerTread() {

        }

        public void run() throws JSONException {

            try {
                friendListObject = new JSONObject(friendlist);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray friendLiArray;
            friendLiArray = friendListObject.getJSONArray("response");
            ArrayList<LatLng> friendPosArray = new ArrayList<>();
            for (int i = 0; i < friendLiArray.length(); i++) {
//                System.out.println("======================================================?????? ??????====================");

                friendPosArray.add(new LatLng(friendLiArray.getJSONObject(i).getDouble("K.K_lat"), friendLiArray.getJSONObject(i).getDouble("K.K_long")));
//                friendLiArray.getJSONObject(i).getDouble("K.K_long");
//                friendLiArray.getJSONObject(i).getDouble("K.K_lat");
            }
//            System.out.println("======================================================??????===================="+friendPosArray.size());
            for (int count = 0; count < friendPosArray.size(); count++) {
//                markersPosition.add(new LatLng(friendLiArray.getJSONObject(count).getDouble("K.K_lat"), friendLiArray.getJSONObject(count).getDouble("K.K_long")));
//                System.out.println("=====================================================????????????===================="+friendLiArray.getJSONObject(count).getString("F.K_code2"));
//                System.out.println("=====================================================??????===================="+friendLiArray.getJSONObject(count).getString("F.F_status"));
//                System.out.println("=====================================================??????===================="+count);
//                System.out.println("=====================================================????????? ??????===================="+friendLiArray.getJSONObject(count).getInt("K.K_ghost"));
                switch (friendLiArray.getJSONObject(count).getInt("F.F_status")) {
                    case 0:
//                        System.out.println("?????? ??????====================");
////                        markersPosition.add(count,friendPosArray.get(count));
//                        markersPosition.add(friendPosArray.get(count));
                        break;
                    case 1: //
//                        System.out.println("1?????? ??????====================");
                        if (friendLiArray.getJSONObject(count).getString("K.K_name").equals(name)) {
//                            System.out.println("+======+++===+++===+++==++==++=");
                            break;
                        }
                        markersPosition.add(friendPosArray.get(count));
                        friendMarker.add(new FriendMarker(friendPosArray.get(count), friendLiArray.getJSONObject(count).getString("K.K_name"), friendLiArray.getJSONObject(count).getString("K.K_profile")));
//                        markersPosition.get(count);
//                        friendMarkerNameList.add(friendLiArray.getJSONObject(count).getString("K.K_name"));
//                        System.out.println("=====================================================??????===================="+markersPosition.get(count));
//                        System.out.println("=====================================================????????????===================="+friendLiArray.getJSONObject(count).getString("F.K_code2"));
//                        System.out.println("=====================================================??????===================="+friendLiArray.getJSONObject(count).getString("K.K_name"));
//                        System.out.println("  ");
                        break;
                    case 2:
//                        System.out.println("2?????? ??????====================");
//                        markersPosition.add(friendPosArray.get(count));
                        break;

                    default:
                        break;
                }
            }

        }

    }

    public void setPlace_info_window(String placeAddress) {
        viewPager.setVisibility(View.GONE);

        view_Header.setVisibility(View.GONE);

        indicator.setVisibility(View.GONE);

        find_way_page.setVisibility(View.GONE);
        subway_info_window.setVisibility(View.GONE);

        bus_info_window.setVisibility(View.GONE);

        place_info_window.setVisibility(View.VISIBLE);

        place_name = placeAddress.split(" ");
        bustitle = "";
        try {
            bustitle = place_name[1] + " " + place_name[2] + " " + place_name[3];
        } catch (Exception e) {
            bustitle = place_name[1] + " " + place_name[2];
        }


        place_info_title.setText(bustitle);
        place_info_address.setText(placeAddress);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                startService();
            }
        }, 200);

    }

    public void setBus_info_window() {
        viewPager.setVisibility(View.GONE);

        view_Header.setVisibility(View.GONE);

        indicator.setVisibility(View.GONE);

        find_way_page.setVisibility(View.GONE);

        subway_info_window.setVisibility(View.GONE);

        place_info_window.setVisibility(View.GONE);

        bus_info_window.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bus_info_title.setText(StationName);
                String bus_num = "";

                busLists = new BusList[odsay.getBusLists().length];

                busLists = odsay.getBusLists();
                bus_ID = new int[busLists.length];
                bus_numbers = new String[busLists.length];
                for (int i = 0; i < busLists.length; i++) {
                    bus_numbers[i] = odsay.getBusLists()[i].getBusNo();
                    bus_ID[i] = odsay.getBusLists()[i].getBusID();
                    if (0 < i) {
                        bus_num += " , ";
                    }
                    bus_num += odsay.getBusLists()[i].getBusNo() + "???";

                }
                bus_info_number.setText(bus_num);
                bus_info_direction.setText(odsay.getArsID());
//                if()
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ardID = odsay.getArsID();
                        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

                        startService();
                    }
                });

            }
        }, 250);

    }

    public void setSubway_info_window() {

        viewPager.setVisibility(View.GONE);

        view_Header.setVisibility(View.GONE);

        indicator.setVisibility(View.GONE);

        find_way_page.setVisibility(View.GONE);
        bus_info_window.setVisibility(View.GONE);
        place_info_window.setVisibility(View.GONE);

        subway_info_window.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                subway_info_title.setText(StationName);
                String direction = odsay.subwayLists.getPrevOBJ_stationNme() + "-" + StationName + "-" + odsay.subwayLists.getNextOBJ_stationName();

                subway_info_direction.setText(direction);
                subway_info_left_station.setText(odsay.subwayLists.getPrevOBJ_stationNme());
                subway_info_this_station.setText(StationName);
                subway_info_right_station.setText(odsay.subwayLists.getNextOBJ_stationName());

//                odsay.getStationList()[0].getStationID();


                odsayService.requestSubwayTimeTable(String.valueOf(StationId), "12", "1", "1", subway_time.subway_timeList);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("=========requst====tryStart====", "");

                            subway_info_left_arriveSoon_name.setText("??????" + subway_time.getStationR_name()[0]);
                            subway_info_left_arriveSoon_time.setText(subway_time.getStationR_time()[0]);
                            subway_info_left_arriveNext_name.setText("??????" + subway_time.getStationR_name()[1]);
                            subway_info_left_arriveNext_time.setText(subway_time.getStationR_time()[1]);

                            subway_info_right_arriveSoon_name.setText("??????" + subway_time.getStationL_name()[0]);
                            subway_info_right_arriveSoon_time.setText(subway_time.getStationL_time()[0]);
                            subway_info_right_arriveNext_name.setText("??????" + subway_time.getStationL_name()[1]);
                            subway_info_right_arriveNext_time.setText(subway_time.getStationL_time()[1]);


                            upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                            startService();
                        } catch (Exception e) {

                        }

                    }
                }, 250);
            }
        }, 350);

    }


    public void MapDraw() {
        if (fw_pos_path != null) {


            pathOverlay.setMap(null);
            findPosArrayOne = new ArrayList<>();
            findPosArrayOne.add(sLatLngPos);

            findPosArrayOne.addAll(mapFindWay.getFindWay_LatLngArrayList());
            findPosArrayOne.add(eLatLngPos);
//            pathOverlay.setCoords(Arrays.asList(eLatLngPos,findPosArrayOne.get(0)));
//            pathOverlay.setMap(naverMap);

            pathOverlay.setCoords(findPosArrayOne);


            switch (position) {
                case 0:
                    pathOverlay.setColor(Color.RED);
                    break;
                case 1:
                    pathOverlay.setColor(Color.GREEN);
                    break;
                case 2:
                    pathOverlay.setColor(Color.BLUE);
                    break;
                default:
                    break;
            }

            pathOverlay.setMap(naverMap);

            findWayOverlayClearIBtn.setVisibility(View.VISIBLE);

        }
    }

    public void startService() {
        serviceIntent = new Intent(this, ForegroundService.class);
        startService(serviceIntent);
    }

    public void stopService() {
        serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("=======================================onStop()", "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("=========================================onStart()", "");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("============================================onRestart()", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("===========================================onDestroy()", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("=============================================onResume()", "");

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d("=====================================onResumeFragments()", "");
    }

    class BackgroundTask_location extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/locationposlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("??????????????? ??????????????? ??????" + result);
            locationlist = result;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    class BackgroundTask_Subway extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/subwaylist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            subwaylist = result;
            System.out.println("??????????????? ????????? ????????? ??????" + result);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    class BackgroundTask_Bus extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/buslist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            buslist = result;
            System.out.println("??????????????? ??????????????? ??????" + result);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    protected String uniToKsc(String uni) throws UnsupportedEncodingException {
        return new String(uni.getBytes("8859_1"), "KSC5601");
    }

    class BackgroundTask_Friend extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/friendlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {

                    stringBuilder.append(temp + "\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("========result=======" + result);
            friendlist = result;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    class BackgroundTask_Promise extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://ec2-13-124-60-158.ap-northeast-2.compute.amazonaws.com/promisslist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("?????? ?????? : " + result);
            promiselist = result;

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    public void locationToFriend(long fCode) {
        double fLat = 0, fLong = 0;

        for (int i = 0; i < friendCodeList.size(); i++) {
            if (friendCodeList.get(i).equals(fCode)) {
                fLat = friendLatList.get(i);
                fLong = friendLongList.get(i);

            }
        }
        LatLng initialPosition = new LatLng(fLat, fLong);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void wayToFriend(String fName, long fCode) {
        findWayIbtn.callOnClick();
        int size = friendCodeList.size();

        for (int i = 0; i < size; i++) {
            if (fCode == friendCodeList.get(i)) {
                friendLat = friendLatList.get(i);
                friendLong = friendLongList.get(i);
            }
        }


        sPosEdit.setText("??? ??????");
        ePosEdit.setText(fName);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vFindIbtn.callOnClick();
            }
        }, 400);
    }


    Response.Listener<String> info_window = new Response.Listener<String>() {// ************Info_Window********************
        @Override
        public void onResponse(String response) {

            try {
                System.out.println("????????? ??????" + response);
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                //???????????? ?????????

                if (success) {
                    System.out.println("???????????? ??????");
                    //???????????? ?????????
                } else {
                    System.out.println("???????????? ??????");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}