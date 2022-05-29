package com.example.bbic;

import static com.naver.maps.map.NaverMap.LAYER_GROUP_TRANSIT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
                System.out.println("인포윈도우에서 정류장 이름 : " + StationName);
                return StationName;
            }

        }
    }

    //버튼 클릭 리스너 클래스
    class BtnOnClickListener implements View.OnClickListener {
        private int sw = 0;

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //case를 통해 id에 따른 클릭이벤트 실행
                case R.id.menu_ibtn:
                    if (!drawerEnabled) {
                        gpsTracker = new GpsTracker(Maps_Activity.this);

                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();

                        String myAddress = getCurrentAddress(latitude, longitude);
                        String[] add = myAddress.split(" ");
                        Log.d("위치", add[1] + " " + add[2]);
                        drawerInit(myAddress);
                        drawerEnabled = true;
                    }
                    drawerLayout.openDrawer(drawerView);
                    break;
                case R.id.drawer_menu_1:
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.drawer_menu_2:
                    break;
                case R.id.drawer_menu_3:
                    Intent intent3 = new Intent(getApplicationContext(), Bookmark.class);
                    intent3.putExtra("닉네임", name);
                    intent3.putExtra("프로필", address);
                    intent3.putExtra("미세먼지", fineDust);
                    intent3.putExtra("초미세먼지", ultraFineDust);
                    intent3.putExtra("온도", tem);
                    intent3.putExtra("날씨", weather);
                    intent3.putExtra("도", area);
                    intent3.putExtra("시", city);
                    intent3.putExtra("코로나", covidNum);
                    startActivity(intent3);
                    finish();
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
                    intent5.putExtra("닉네임", name);
                    intent5.putExtra("프로필", address);
                    intent5.putExtra("미세먼지", fineDust);
                    intent5.putExtra("초미세먼지", ultraFineDust);
                    intent5.putExtra("온도", tem);
                    intent5.putExtra("날씨", weather);
                    intent5.putExtra("도", area);
                    intent5.putExtra("시", city);
                    intent5.putExtra("코로나", covidNum);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.drawer_menu_6:
                    Intent intent6 = new Intent(getApplicationContext(), Setting_Activity.class);
                    intent6.putExtra("닉네임", name);
                    intent6.putExtra("프로필", address);
                    intent6.putExtra("미세먼지", fineDust);
                    intent6.putExtra("초미세먼지", ultraFineDust);
                    intent6.putExtra("온도", tem);
                    intent6.putExtra("날씨", weather);
                    intent6.putExtra("도", area);
                    intent6.putExtra("시", city);
                    intent6.putExtra("코로나", covidNum);
                    startActivity(intent6);
                    finish();
                    break;
                case R.id.main_search_ibtn:
//                    Intent intent = new Intent(getApplicationContext(), Bookmark.class);
//                    startActivity(intent);
                    search_location();
                    break;
                case R.id.main_find_way_ibtn:

                    view_Header.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    indicator.setVisibility(View.GONE);
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
                    System.out.println("검색 버튼");

                    String sPosEt = sPosEdit.getText().toString();
                    String ePosEt = ePosEdit.getText().toString();

                    System.out.print(sPosEt + "시작 " + ePosEt + "도착");

                    try {
                        nameToPos(sPosEt, ePosEt);
                    } catch (Exception e) {
                        System.out.println("찾을수 없는 장소입니다.");
                    }
                    keyboardmanager.hideSoftInputFromWindow(sPosEdit.getWindowToken(), 0);

                case R.id.view_header_ghost_btn:

                    switch (sw) {
                        case 0:
                            sw = 1;
                            headerGhostBtn.setImageResource(R.drawable.ghost2);
                            break;

                        case 1:
                            sw = 0;
                            headerGhostBtn.setImageResource(R.drawable.ghost1);
                            break;
                    }
                    break;
                case R.id.posEdit_change_ibtn:
                    String startEdit = sPosEdit.getText().toString();
                    //String endEdit = ePosEdit.getText().toString();
                    sPosEdit.setText(ePosEdit.getText().toString());
                    ePosEdit.setText(startEdit);
                    ePosEdit.setSelection(ePosEdit.length());
                    sPosEdit.setSelection(sPosEdit.length());
                    break;
            }
        }
    }

    //위치 참조를 위한 객체
    GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private Geocoder geocoder;

    //참조를 위한 각 객체 생성
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, searchIbtn, findWayIbtn, vFindIbtn, vEditChangeFindIbtn;
    private TextView
            temText, fineText, ultraText, covidText, nickName, areaText;
    private ImageView weatherImage, profile;
    private String[] add;
    private EditText editText, sPosEdit, ePosEdit;
    private Button[] drawerMenu = new Button[6];
    private FusedLocationSource locationSource;
    private boolean drawerEnabled = false;

    private ImageView headerProfile;
    private TextView headerName, headerCode;
    private ImageView headerGhostBtn, headerSettingBtn;


    //    private JSONArray[] path;
    private JSONObject result, meResult;
    private String fw_pos_path, mapObject;
    private Find_Way_Frag fw_frag;
    private Map_Find_way mapFindWay;

    private double[] latiPos, longPos;
    private double sLatiPos, sLongPos, eLatiPos, eLongPos;
    private LatLng sLatLngPos, eLatLngPos;
    private String mapObjectPos;
    private ArrayList<LatLng> findPosArrayOne, findPosArrayTwo, findPosArrayTree, findPosArrayFour;

    private SlidingUpPanelLayout upPanelLayout;
    private Bundle bundleFw;
    private int position;
    private FragmentTransaction ft;
    private MultipartPathOverlay pathOver;
    private PathOverlay pathOverlay;


    private ViewPager2 viewPager;
    private WormDotsIndicator indicator;
    private ConstraintLayout view_userpage, view_Header, find_way_page;

    private NaverMap naverMap;

    private String allDust, weather, tem, fineDust, ultraFineDust, covidNum, name, address, area, city;
    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;


    private StationList[] StationLists;
    private static String y = "", x = "";
    private static Odsay odsay;
    private static Odsay bus_info;
    private static String StationName;
    private static int StationId;
    private static int stationClass;

    public static ODsayService odsayService;
    private InputMethodManager keyboardmanager;


    //수정할수도 있음 ==============================================
    // 현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(((CameraPosition) cameraPosition).target.latitude, cameraPosition.target.longitude);
    }

    // 선택한 마커의 위치가 가시거리(카메라가 보고있는 위치 반경 3km 내)에 있는지 확인
    public final static double REFERANCE_LAT = 1 / 109.958489129649955;
    public final static double REFERANCE_LNG = 1 / 88.74;
    public final static double REFERANCE_LAT_X3 = 3 / 109.958489129649955;
    public final static double REFERANCE_LNG_X3 = 3 / 88.74;

    public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
        boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3;
        boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3;
        return withinSightMarkerLat && withinSightMarkerLng;
    }

    // 지도상에 표시되고있는 마커들 지도에서 삭제
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


        //infoWindow.open(naverMap);//인포윈도우 클릭 시

//
//        LocationButtonView locationButtonView = findViewById(R.id.navermap_location_button);
//        locationButtonView.setMap(naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);


        naverMap.setOnMapClickListener((point, coord) -> {


            odsay = new Odsay();
            bus_info = new Odsay();
            StationName = "";
            x = "";
            y = "";
            y = String.valueOf(infoWindow.getPosition().latitude);
            x = String.valueOf(infoWindow.getPosition().longitude);


            Log.d("위치 좌표 Y", String.valueOf(infoWindow.getPosition().latitude));
            Log.d("위치 좌표 X", String.valueOf(infoWindow.getPosition().longitude));
            odsayService.requestPointSearch(x, y, "5", "1:2", odsay.pointSearch);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.out.println("배열의 크기 : " + odsay.getCount());
                    if (odsay.getCount() >= 1) {
                        StationLists = new StationList[odsay.getStationList().length];
                        StationLists = odsay.getStationList();
                        for (int i = 0; i < odsay.getCount(); i++) {

                            System.out.println("가져온 배열" + StationLists[i].getStationClass());
                            System.out.println("가져온 정류장 이름" + StationLists[i].getStationName());
                            System.out.println("가져온 정류장 아이디" + StationLists[i].getStationID());
                            System.out.println("가져온 정류장 " + StationLists[i].getX());
                            System.out.println("가져온 정류장 " + StationLists[i].getY());
                            StationName = StationLists[i].getStationName();
                            System.out.println("정류장 이름 : " + StationName + "\n" + "반목문안에서의 배열의 크기 : " + odsay.getCount());
                            StationId = StationLists[i].getStationID();
                            stationClass = StationLists[i].getStationClass();

                            if (stationClass == 1) {
                                odsayService.requestBusStationInfo(String.valueOf(StationLists[i].getStationID()), odsay.busStationInfo);
                            } else if (stationClass == 2) {
                                odsayService.requestSubwayStationInfo(String.valueOf(StationId), odsay.subwayStationInfo);
                            }

                        }
                    }

                    infoWindow.setPosition(coord);
                    Log.d("===========coord=================", coord + "");
                    infoWindow.open(naverMap);

                }
            }, 2500);


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
//                            System.out.println("가져온 배열" + busStationLists[i].getStationClass());
//                            System.out.println("가져온 정류장 이름" + busStationLists[i].getStationName());
//                            System.out.println("가져온 정류장 아이디" + busStationLists[i].getStationID());
//                            System.out.println("가져온 정류장 " + busStationLists[i].getX());
//                            System.out.println("가져온 정류장 " + busStationLists[i].getY());
//                            busStationName = busStationLists[i].getStationName();
//
//                        }
//                        //10분마다 한번씩 실행
//                        Thread.sleep(600);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();


//            if(point_search.getCount() >= 1 ){
//                System.out.println("여기 이프문");
//                busStationLists = new BusStationList[point_search.getCount()];
//                for (int i = 0; i <point_search.getCount() ; i++){
//                    busStationLists[i] = new BusStationList(point_search.getBusStationList()[i].getStationClass(), point_search.getBusStationList()[i].getStationName(),point_search.getBusStationList()[i].getStationID(), point_search.getBusStationList()[i].getX(),point_search.getBusStationList()[i].getY());
//                    System.out.println("배열 " + busStationLists[i]);
//                }
//            }else{
//                System.out.println("여기 엘스문");
//
//            }


//
//            String name = point_search.getBusStationList()[0].getStationName();
//            System.out.println("대중교통 이름 " + name);

        });

        markersPosition = new Vector<LatLng>();
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
        // 카메라 이동 되면 호출 되는 이벤트
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
                LatLng currentPosition = getCurrentPosition(naverMap);
                for (LatLng markerPosition : markersPosition) {
                    if (!withinSightMarker(currentPosition, markerPosition))
                        continue;
                    Marker marker = new Marker();
                    marker.setPosition(markerPosition);
                    marker.setMap(naverMap);
                    activeMarkers.add(marker);
                }
            }
        });
    }


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

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String myAddress = getCurrentAddress(latitude, longitude);
        String[] add = myAddress.split(" ");
        //Log.d("위치", add[1]+" "+add[2]);
        Log.d("위치 좌표", latitude + " " + longitude);
        drawerInit(myAddress);

        //도성대
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        bundleFw = new Bundle();

        ft = getSupportFragmentManager().beginTransaction();

        //이세호
        //버튼 클릭 리스너 클래스 객체 생성(클릭 이벤트를 위함)
        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //각 객체의 참조값을 넣어줌
        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity);
        drawerView = (View) findViewById(R.id.drawer_main);
        menuIbtn = (ImageButton) findViewById(R.id.menu_ibtn);
        temText = (TextView) findViewById(R.id.drawer_tem_text);
        fineText = (TextView) findViewById(R.id.drawer_fine_text);
        ultraText = (TextView) findViewById(R.id.drawer_ultra_text);
        covidText = (TextView) findViewById(R.id.drawer_covid_text);
        weatherImage = (ImageView) findViewById(R.id.drawer_weather_img);
        searchIbtn = (ImageButton) findViewById(R.id.main_search_ibtn);
        profile = (ImageView) findViewById(R.id.drawer_profile_img); // 카카오톡 프로파일 이미지
        nickName = (TextView) findViewById(R.id.drawer_profile_name); // 카카오톡 닉네임
        areaText = (TextView) findViewById(R.id.drawer_area_text);
        editText = (EditText) findViewById(R.id.main_search_et);
        findWayIbtn = (ImageButton) findViewById(R.id.main_find_way_ibtn);


        upPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slide);
        view_Header = (ConstraintLayout) findViewById(R.id.view_header);
        viewPager = (ViewPager2) findViewById(R.id.view_pager);
        indicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
        find_way_page = (ConstraintLayout) findViewById(R.id.view_find_way_lay);
        vEditChangeFindIbtn = (ImageButton) findViewById(R.id.posEdit_change_ibtn);
        vFindIbtn = (ImageButton) findViewById(R.id.view_find_way_ibtn);
        sPosEdit = (EditText) findViewById(R.id.start_pos_et);
        ePosEdit = (EditText) findViewById(R.id.end_pos_et);

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


        //레이아웃에 네비게이션 드로어 설젇
        drawerLayout.setDrawerListener(drawerListener);

        //버튼의 클릭 리스너 설정
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

//============================================================================================SlidingUpPanel
        upPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                Log.d("upPanel 내용 ", "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d("upPanel 바뀔때 내용 ", "onPanelStateChanged " + newState);
                if (newState == (SlidingUpPanelLayout.PanelState.COLLAPSED) && view_Header.getVisibility() == View.GONE) {
                    view_Header.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    indicator.setVisibility(View.VISIBLE);
                    find_way_page.setVisibility(View.GONE);
                    pathOverlay.setMap(null);
//                    Log.d("바꿈", "");
                }
            }
        });

//===================================================================================================

        TabLayout tabs = (TabLayout) findViewById(R.id.view_transport_table);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                getSupportFragmentManager().beginTransaction().attach(find_way_frag).commitAllowingStateLoss();
//                getSupportFragmentManager().beginTransaction().remove(find_way_frag);
//                getSupportFragmentManager().beginTransaction().detach(find_way_frag);
//                ft.detach(find_way_frag).attach(find_way_frag).setPrimaryNavigationFragment(find_way_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();
//                ft.detach(find_way_frag);
//                getSupportFragmentManager().beginTransaction().remove(find_way_frag);
                position = tab.getPosition();
//                Log.d("============fw_frag  bundle 추가후==========", fw_frag + "");
//                Log.d("============TabPosition==========", position + "");
                bundleFw.putInt("TabPos", position);
                fw_frag.setArguments(bundleFw);

                frag_set(fw_frag);
//                    Find_Way_Frag find_way_frag = new Find_Way_Frag();
//                    transaction.replace(R.id.view_fw_container,fw_frag);
//                    Find_Way_Frag fw_frag = (Find_Way_Frag) getSupportFragmentManager().findFragmentById(R.id.view_fw_container);// 리스트 프래그먼트 위치

//                    find_way_frag.setArguments(bundleFw);
//                fw_frag.setArguments(bundleFw);

//                fw_frag = (Find_Way_Frag) getSupportFragmentManager().findFragmentById(R.id.view_fw_container);
//
//                Log.d("============fw_frag  bundle 추가후==========", fw_frag + "");
////                getSupportFragmentManager().beginTransaction().detach(fw_frag).attach(fw_frag).setPrimaryNavigationFragment(fw_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();
////                ft.hide(fw_frag).commit();
//                ft.detach(fw_frag).commitNowAllowingStateLoss();
//                ft.attach(fw_frag).setPrimaryNavigationFragment(fw_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();
//                ft.detach(fw_frag).attach(fw_frag).setPrimaryNavigationFragment(fw_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();

                //ft.attach(find_way_frag).setPrimaryNavigationFragment(find_way_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();
//                getSupportFragmentManager().beginTransaction().attach(find_way_frag).setPrimaryNavigationFragment(find_way_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//===================================================================================================

        searchIbtn.setOnClickListener(onClickListener); // 검색 버튼 리스너
        findWayIbtn.setOnClickListener(onClickListener);
        vFindIbtn.setOnClickListener(onClickListener);
        vEditChangeFindIbtn.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        name = intent.getStringExtra("닉네임");
        address = intent.getStringExtra("프로필");
        nickName.setText(name); // 카카오톡 프로필 닉네임
        Glide.with(this).load(address).circleCrop().into(profile); // 카카오톡 프로필 이미지

        if (!checkLocationServiceStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }

        headerName.setText(name);
        Glide.with(this).load(address).circleCrop().into(headerProfile);


        //뷰페이저 설정
        viewPager = findViewById(R.id.view_pager);
        ViewpagerAdapter pagerAdapter = new ViewpagerAdapter(setTextList());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnClickListener(onClickListener);

        indicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
        indicator.setViewPager2(viewPager);

        final ImageButton ibtn = (ImageButton) viewPager.findViewById(R.id.view_item_ibtn1);

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


//                    fw_frag = (Find_Way_Frag) getSupportFragmentManager().findFragmentById(R.id.view_fw_container);// 리스트 프래그먼트 위치
//                    Bundle bundle = new Bundle(1);
//                    bundle.putString("odsay","TEST");
//                    fw_frag.setArguments(bundle);


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
//            Log.d("리스트 만들기부분","");
//
//            Find_way_Data fwData = new Find_way_Data(9,R.color.yellow,24,R.color.black,24, 56,R.color.red);
////
////            Find_way_Data way_data = new Find_way_Data();
//        }
//        if(upPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
//
//            Log.d("리스트 만들기 전","");
//            view_recyclerView = (RecyclerView) findViewById(R.id.view_RecyclerView);
//            Log.d("리스트 선언부분","");
//            linearLayoutManager = new LinearLayoutManager(this);
//            Log.d("리니어메니저 선언 부분","");
//            view_recyclerView.setLayoutManager(linearLayoutManager);
//            Log.d("레이아웃 매니저 설정","");
//            fArrayList = new ArrayList<>();
//            find_way_listAdapter = new Find_way_listAdapter(fArrayList);
//            Log.d("어뎁터 생성 부분","");
//            view_recyclerView.setAdapter(find_way_listAdapter);
//            Log.d("뷰에 어뎁터 설정 부분","");
//
//            Find_way_Data fwData = new Find_way_Data(9,R.color.yellow,24,R.color.black,24, 56,R.color.red);
//            fArrayList.add(fwData);
//            find_way_listAdapter.notifyDataSetChanged();
////
////            Find_way_Data way_data = new Find_way_Data();
//        }


// ODSay ====================================================================================================================
//        ODsayService odsayService = ODsayService.init(getApplicationContext(), "d/F477b1GZGKZgWCv8LynPEERmoxCdE1jSOojHzKNPM");
//        odsayService.setReadTimeout(5000);
//        odsayService.setConnectionTimeout(5000);
//        // 콜백 함수 구현
//        OnResultCallbackListener busStationInfo = new OnResultCallbackListener() {
//            // 호출 성공 시 실행
//            @Override
//            public void onSuccess(ODsayData odsayData, API api) {
//                try {
//                    // API Value 는 API 호출 메소드 명을 따라갑니다.
//                    if (api == API.BUS_STATION_INFO) {
//                        String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
//                        Log.d("Station name : %s", stationName);
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            // 호출 실패 시 실행
//            @Override
//            public void onError(int i, String s, API api) {
//                if (api == API.BUS_STATION_INFO) {}
//            }
//        };

    }

    public void receiveMessage(Intent intent) {

    }


    private void drawerInit(String myAddress) {
        add = myAddress.split(" ");
        area = add[0];
        city = add[1];
        final String temURL = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=" + area + " " + city + "날씨"; //웹크롤링 할 주소(1)
        final String covidURL = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&qvt=0&query=코로나19"; //웹크롤링 할 주소(2)
        //스레드간 데이터 전달을 위한 번들 생성
        final Bundle bundle = new Bundle();

        //웹크롤링 전용 스레드 생성
        new Thread() {
            @Override
            public void run() {
                Document doc;
                try {
                    //웹 사이트 데이터 설정
                    doc = Jsoup.connect(temURL).get();

                    //온도정보
                    Elements temperature = doc.select(".temperature_text");
                    //미세먼지, 초미세먼지 정보
                    Elements dust = doc.select(".report_card_wrap");
                    //날씨
                    Elements wt = doc.select(".weather_main");

                    //웹 사이트 데이터 설정
                    doc = Jsoup.connect(covidURL).get();

                    Elements covid = doc.select(".status_info");

                    //문자열 변환 및 자르기
                    tem = temperature.get(0).text().substring(5);
                    allDust = dust.get(0).text();
                    String[] array = allDust.split(" ");
                    fineDust = array[1];
                    ultraFineDust = array[3];
                    weather = wt.get(0).text();
                    array = covid.get(0).text().split(" ");
                    covidNum = array[2];

                    drawer_input();

                    //10분마다 한번씩 실행
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
        covidText.setText("전일 코로나 확진자 수 '" + covidNum + "' 명");

        switch (fineDust) {
            case "좋음":
                fineText.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case "보통":
                fineText.setTextColor(getResources().getColor(R.color.green));
                break;
            case "나쁨":
                fineText.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "매우나쁨":
                fineText.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                fineText.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        switch (ultraFineDust) {
            case "좋음":
                ultraText.setTextColor(getResources().getColor(R.color.teal_200));
                break;
            case "보통":
                ultraText.setTextColor(getResources().getColor(R.color.green));
                break;
            case "나쁨":
                ultraText.setTextColor(getResources().getColor(R.color.orange));
                break;
            case "매우나쁨":
                ultraText.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                ultraText.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        switch (weather) {
            case "맑음":
                weatherImage.setImageResource(R.drawable.sunny);
                break;
            case "흐림":
            case "구름많음":
                weatherImage.setImageResource(R.drawable.cloud);
                break;
            case "눈":
                weatherImage.setImageResource(R.drawable.snow);
                break;
            case "비":
                weatherImage.setImageResource(R.drawable.rain);
                break;
            default:
                weatherImage.setImageResource(R.drawable.ic_baseline_block);
                Log.d("날씨 명", weather);
        }
    }

    //드로어 이벤트 리스너
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
    //퍼미션 요청 결과를 받는 메소드
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {//요청 코드가 동일하고, 요청한 퍼미션 만큼 수신되었다면 실행
            boolean check_result = true;

            //퍼미션이 모두 허용됫는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {//허용이 아닐경우
                    check_result = false;//false로 저장
                    break;
                }
            }

            if (check_result) {

            } else {//거부된 퍼미션이 있을경우
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(Maps_Activity.this, "권한이 거부되었습니다. 앱을 다시 실행하여 권한을 허용해주세요", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(Maps_Activity.this, "권한이 거부되었습니다. 설정(앱 정보)에서 권한을 허용해주세요", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    boolean checkRunTimePermission() {//런타임 퍼미션 처리
        //위치 퍼미션 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {//퍼미션을 가지고있다면
            return true;
        } else {//권한이 없다면
            Toast.makeText(Maps_Activity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            do {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Maps_Activity.this, REQUIRED_PERMISSIONS[0])) {//권한 요청을 거부한적이 있을때
                    ActivityCompat.requestPermissions(Maps_Activity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);//사용자에게 권한 요청
                } else {//요청이 처음이라면
                    ActivityCompat.requestPermissions(Maps_Activity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);//사용자에게 권한 요청
                }
                hasFineLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            } while (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public String getCurrentAddress(double latitude, double longitude) {//주소 찾기
        geocoder = new Geocoder(this, Locale.getDefault());//역지오코딩 위한 지오코딩 객체 선언

        List<Address> addresses;//주소를 저장할 주소리스트 선언

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 7);//매개변수로 입력받은 경도위도로 주소 찾기
        } catch (IOException ioException) {//에러시
            Toast.makeText(Maps_Activity.this, "지오코더 서비스 사용불가", Toast.LENGTH_SHORT).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(Maps_Activity.this, "잘못된 GPS 좌표", Toast.LENGTH_SHORT).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {//나온 주소가 없을때
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_SHORT).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);//주소객체에 주소값 저장
        return address.getAddressLine(0).toString() + "\n";//리턴
    }

    private void showDialogForLocationServiceSetting() {//GPS 활성화가 안되있을때 허용하게 나오게 하는 다이알로그

        AlertDialog.Builder builder = new AlertDialog.Builder(Maps_Activity.this);//다이얼로그 생성
        builder.setTitle("위치 서비스 비활성화");//타이틀 설정
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");//내용 설정
        builder.setCancelable(true);//버튼 추가
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {//수락 버튼 설정
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);//권한 요청창 뜨게끔
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {//취소 버튼 설정
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();//다이얼로그 취소
            }
        });
        builder.create().show();//생성후 보여주기
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:
                if (checkLocationServiceStatus()) {
                    if (checkLocationServiceStatus()) {
                        Log.d("위치권한 확인", "true");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServiceStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void nameToPos(String sEtPosName, String eEtPosName) {
        List<Address> sAddressList = null;
        List<Address> eAddressList = null;
        String sLatitude = "";
        String sLongitude = "";
        String eLatitude = "";
        String eLongitude = "";
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

//            String sAddress = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); //주소

        sLatitude = sSplitStr[10].substring(sSplitStr[10].indexOf("=") + 1);
        sLongitude = sSplitStr[12].substring(sSplitStr[12].indexOf("=") + 1);

        eLatitude = eSplitStr[10].substring(eSplitStr[10].indexOf("=") + 1);
        eLongitude = eSplitStr[12].substring(eSplitStr[12].indexOf("=") + 1);

        sLatLngPos = new LatLng(Double.valueOf(sLatitude), Double.valueOf(sLongitude));
        eLatLngPos = new LatLng(Double.valueOf(eLatitude), Double.valueOf(eLongitude));

        odsayService.requestSearchPubTransPath(sLongitude, sLatitude, eLongitude, eLatitude, "0", "0", "0", mapFindWay.Find_way);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {                                        //handler 객체 딜레이 (2초)끝나면 종료
            @Override
            public void run() {
//                path = mapFindWay.getPath_s();
                result = mapFindWay.getResult();
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
                    fw_frag.setArguments(bundleFw);
                    frag_set(fw_frag);
                    meResult = result;

//                    Log.d("Position", position + "");
//                    Log.d("=========================================fw_frag=====================================", fw_frag + "");
                }

            }
        }, 500);
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
                        str,//주소
                        10 // 최대겁색 결과 개수
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(addressList.get(0).toString());
            //콤마를 기준으로 split
            String[] splitStr = addressList.get(0).toString().split(",");
            String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
            System.out.println(address);

            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
            System.out.println(latitude);
            System.out.println(longitude);

            // 좌표(위도, 경도) 생성
            LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            // 마커 생성
            Marker marker = new Marker();
            marker.setPosition(point);
            // 마커 추가
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
//            Log.d("===================fw_frag============", "" + fw_frag);
            getSupportFragmentManager().beginTransaction().add(R.id.view_fw_container, fw_frag).commit();
        } else if (meResult != null || result != meResult && result != null) {
//            Log.d("===================fw_frag============", "" + fw_frag);
            fw_frag = (Find_Way_Frag) getSupportFragmentManager().findFragmentById(R.id.view_fw_container);

            ft.detach(fw_frag).commitNowAllowingStateLoss();
            ft.attach(fw_frag).setPrimaryNavigationFragment(fw_frag).setReorderingAllowed(true).commitNowAllowingStateLoss();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d("onNewIntent()", "");
        if (intent.getStringExtra("jObject") != null) {
            fw_pos_path = intent.getStringExtra("jObject");
            try {
                JSONObject pos = new JSONObject(fw_pos_path);
                Log.d("======================jObject=================================", fw_pos_path);

                ArrayList<LatLng> total_finde_pos_array = new ArrayList<>();
                try {
//                    total_finde_pos_array.add(new LatLng());
                    mapObjectPos = pos.getJSONObject("info").getString("mapObj");
                    String split_mapObject[] = mapObjectPos.split("@");

//                    findPosArrayOne = new ArrayList<>();
//                    findPosArrayTwo = new ArrayList<>();
//                    findPosArrayTree = new ArrayList<>();
//                    findPosArrayFour = new ArrayList<>();
//
//                    findPosArrayOne.add(sLatLngPos);
//                    LatLng lastEndFindPos = new LatLng(0,0);
//
//                    for (int i = 0; i < split_mapObject.length; i++) {
//                        odsayService.requestLoadLane("0:0@" + split_mapObject[i], mapFindWay.LoadLane);
//                        int count = i;
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//
//                                switch (count) {
//                                    case 0:
//                                        findPosArrayOne.addAll(mapFindWay.getFindWay_LatLngArrayList());
//                                        lastEndFindPos= (0,0);
//                                        break;
//                                    case 1:
//                                        findPosArrayTwo.addAll(mapFindWay.getFindWay_LatLngArrayList());
//                                        break;
//                                    case 2:
//                                        findPosArrayTree.addAll(mapFindWay.getFindWay_LatLngArrayList());
//                                        break;
//                                    case 3:
//                                        findPosArrayFour.addAll(mapFindWay.getFindWay_LatLngArrayList());
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        }, 200);
//                    }
//                    pathOver = new MultipartPathOverlay();
//                    pathOver.setCoordParts(Arrays.asList(Arrays.asList(sLatLngPos,findPosArrayOne.get(0)),findPosArrayOne,findPosArrayTwo,find));
//                    findPosArrayOne.add(eLatLngPos);
//
                    mapObject = "0:0@" + pos.getJSONObject("info").getString("mapObj");
                    odsayService.requestLoadLane(mapObject, mapFindWay.LoadLane);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MapDraw();
                        }
                    }, 500);

                } catch (Exception e) {
//                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void MapDraw() {
        if (fw_pos_path != null) {
//            Log.d("=================null아니다~!~!!~!~!~======================", "");


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


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop()", "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart()", "");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("onRestart()", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy()", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
//
//            Intent intent = getIntent();
////            Log.d("======================jObject=================================",intent.getStringExtra("jObject"));
//            Log.d("onResume()","");
//            if(intent.getStringExtra("jObject")!=null){
//                fw_pos_path = intent.getStringExtra("jObject");
//                Log.d("======================jObject=================================",fw_pos_path);
//            }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d("onResumeFragments()", "");
    }
}
