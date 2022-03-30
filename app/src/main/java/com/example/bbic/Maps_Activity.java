package com.example.bbic;

import static com.naver.maps.map.NaverMap.LAYER_GROUP_TRAFFIC;
import static com.naver.maps.map.NaverMap.LAYER_GROUP_TRANSIT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

//ver 0.0.1
public class Maps_Activity extends AppCompatActivity implements OnMapReadyCallback {

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
                return getContext().getString(R.string.format_info_window_on_map,
                        infoWindow.getPosition().latitude, infoWindow.getPosition().longitude);
            }
        }
    }

    //버튼 클릭 리스너 클래스
    class BtnOnClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //case를 통해 id에 따른 클릭이벤트 실행
                case R.id.menu_ibtn:
                    if(!drawerEnabled) {
                        gpsTracker = new GpsTracker(Maps_Activity.this);

                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();

                        String myAddress = getCurrentAddress(latitude, longitude);
                        String[] add = myAddress.split(" ");
                        Log.d("위치", add[1]+" "+add[2]);
                        drawerInit(myAddress);
                        drawerEnabled=true;
                    }
                    drawerLayout.openDrawer(drawerView);
                    break;
                case R.id.drawer_menu_1:
                    Log.d("클릭", "onClick: ");
                    drawerLayout.closeDrawer(drawerView);
                    break;
                case R.id.drawer_menu_2:
                    Intent intent2 = new Intent(getApplicationContext(), Subway.class);
                    intent2.putExtra("닉네임", name);
                    intent2.putExtra("프로필", address);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.drawer_menu_3:
                    System.out.println("click");
                    Intent intent3 = new Intent(getApplicationContext(), Bookmark.class);
                    intent3.putExtra("닉네임", name);
                    intent3.putExtra("프로필", address);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.drawer_menu_4:
                    break;
                case R.id.drawer_menu_5:
                    Intent intent5 = new Intent(getApplicationContext(), FP.class);
                    intent5.putExtra("닉네임", name);
                    intent5.putExtra("프로필", address);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.drawer_menu_6:
                    Intent intent6 = new Intent(getApplicationContext(), Setting_Activity.class);
                    intent6.putExtra("닉네임", name);
                    intent6.putExtra("프로필", address);
                    startActivity(intent6);
                    finish();
                    break;
                case R.id.main_search_ibtn:
//                    Intent intent = new Intent(getApplicationContext(), Bookmark.class);
//                    startActivity(intent);
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
    private float x = 0, y= 0;

    //참조를 위한 각 객체 생성
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menuIbtn, searchIbtn;
    private TextView
            temText, fineText, ultraText, covidText, nickName, areaText;
    private ImageView weatherImage, profile;
    private String name, address;
    private EditText editText;
    private Button[] drawerMenu = new Button[6];
    private FusedLocationSource locationSource;
    private boolean drawerEnabled = false;

    private NaverMap naverMap;
    private List<BusStationList> busStationList;



    private String allDust, weather, tem, fineDust, ultraFineDust, covidNum;
    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;

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
        for (Marker activeMarker: activeMarkers) {
            activeMarker.setMap(null);
        }
        activeMarkers = new Vector<Marker>();
    }



    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        geocoder = new Geocoder(this);
        this.naverMap = naverMap;
        naverMap.setLayerGroupEnabled(LAYER_GROUP_TRANSIT,true);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        LatLng initialPosition = new LatLng(37.506855, 127.066242);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setLogoGravity(Gravity.RIGHT|Gravity.BOTTOM);

        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setPosition(new LatLng(37.5666102, 126.9783881));
        infoWindow.setAdapter(new InfoWindowAdapter(this));
        infoWindow.setOnClickListener(overlay -> {
            infoWindow.close();
            return true;
        });
        infoWindow.open(naverMap);
//
//        LocationButtonView locationButtonView = findViewById(R.id.navermap_location_button);
//        locationButtonView.setMap(naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);


        naverMap.setOnMapClickListener((point, coord) -> {
            infoWindow.setPosition(coord);
            infoWindow.open(naverMap);
        });

        markersPosition = new Vector<LatLng>();
        for (int x = 0; x < 100; ++x) {
            for (int y = 0; y < 100; ++y) {
                markersPosition.add(new LatLng(
                        initialPosition.latitude - (REFERANCE_LAT * x),
                        initialPosition.longitude + (REFERANCE_LNG * y)
                ));
                markersPosition.add(new LatLng(
                        initialPosition.latitude + (REFERANCE_LAT * x),
                        initialPosition.longitude - (REFERANCE_LNG * y)
                ));
                markersPosition.add(new LatLng(
                        initialPosition.latitude + (REFERANCE_LAT * x),
                        initialPosition.longitude + (REFERANCE_LNG * y)
                ));
                markersPosition.add(new LatLng(
                        initialPosition.latitude - (REFERANCE_LAT * x),
                        initialPosition.longitude - (REFERANCE_LNG * y)
                ));
            }
        }
        // 카메라 이동 되면 호출 되는 이벤트
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
                LatLng currentPosition = getCurrentPosition(naverMap);
                for (LatLng markerPosition: markersPosition) {
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

        //도성대
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);



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
        editText = (EditText) findViewById(R.id.main_search_et); // 검색창

        drawerMenu[0] = (Button) findViewById(R.id.drawer_menu_1);
        drawerMenu[1] = (Button) findViewById(R.id.drawer_menu_2);
        drawerMenu[2] = (Button) findViewById(R.id.drawer_menu_3);
        drawerMenu[3] = (Button) findViewById(R.id.drawer_menu_4);
        drawerMenu[4] = (Button) findViewById(R.id.drawer_menu_5);
        drawerMenu[5] = (Button) findViewById(R.id.drawer_menu_6);

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

        searchIbtn.setOnClickListener(onClickListener); // 검색 버튼 리스너

        Intent intent = getIntent();
        name = intent.getStringExtra("닉네임");
        address = intent.getStringExtra("프로필");
        nickName.setText(name); // 카카오톡 프로필 닉네임
        Glide.with(this).load(address).circleCrop().into(profile); // 카카오톡 프로필 이미지

        if(!checkLocationServiceStatus()){
            showDialogForLocationServiceSetting();
        }else{
            checkRunTimePermission();
        }

        //뷰페이저 설정
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(setTextList());
        viewPager.setAdapter(adapter);


        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch(i){
                    case  KeyEvent.KEYCODE_ENTER:
                        if(editText.length() == 0){
                            editText.requestFocus();
                            break;
                        }else{
                            String str = editText.getText().toString();
                            List<Address> addressList = null;
                            try{
                                addressList = geocoder.getFromLocationName(
                                        str,//주소
                                        10 // 최대겁색 결과 개수
                                );
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            System.out.println(addressList.get(0).toString());
                            //콤마를 기준으로 split
                            String []splitStr = addressList.get(0).toString().split(",");
                            String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
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
                    break;
                }
                editText.requestFocus();
                return false;
            }
        });

        ODsayService odsayService = ODsayService.init(getApplicationContext(), "d/F477b1GZGKZgWCv8LynPEERmoxCdE1jSOojHzKNPM");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
        // 콜백 함수 구현
        OnResultCallbackListener busStationInfo = new OnResultCallbackListener() {
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.BUS_STATION_INFO) {
                        String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
                        Log.d("Station name : %s", stationName);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.BUS_STATION_INFO) {}
            }
        };
        OnResultCallbackListener pointSearch = new OnResultCallbackListener() {
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.POINT_SEARCH) {
//                        JSONObject jsonObject = new JSONObject();
//                        JSONArray jsonArray = jsonObject.getJSONArray("result");
//                        int count = 0;
//
//                        while(count < jsonArray.length()){
//                            JSONObject object = jsonArray.getJSONObject(count);
//
//                            String stationName = object.getString("stationName");
//
//                            BusStationList List =new BusStationList(stationName);
//
//                            busStationList.add(List);
//                            count++;
//                        }

                        int count = odsayData.getJson().getJSONObject("result").getInt("count");
                        //String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
                        Log.d("Station count : %s", String.valueOf(count));

                        JSONArray station = odsayData.getJson().getJSONObject("result").getJSONArray("station");
                        Log.d("station info:", String.valueOf(station));
                        Log.d("station count:", String.valueOf(station.length()));
                        String stationName1  = station.getJSONObject(0).getString("stationName");
                        Log.d("stationName1:", stationName1);
                        for (int i = 0; i < station.length(); i++){
                                    String info  = station.getString(i);
                                    String stationName  = station.getJSONObject(i).getString("stationName");
                                    Log.d("stationName:", stationName);
                        }

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.POINT_SEARCH) {}
            }
        };


        // API 호출
        odsayService.requestBusStationInfo("107475", busStationInfo);
        odsayService.requestPointSearch("126.84807","37.5454","250","1:2",pointSearch);




//        String getBusstationXmlData(){
//            StringBuilder buffer = new StringBuilder();
//
//            String serviceKey = "dLt4yggklN2Ka%2FyGP%2Fp2OqpFavssgoVTbnYH2o6KUw3X4rgn27JKQrFm%2BYr6klQsN3Gt1pWZO41Kr1viTL86Ww%3D%3D";
//            String gpsLati = String.valueOf(x);
//            String gpsLong = String.valueOf(y);
//            String numOfRows = "10";
//            String pageNo = "1";
//            String queryUrl = "http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getCrdntPrxmtSttnList?serviceKey=" + serviceKey + "&gpsLati=" + gpsLati + "&gpsLong=" + gpsLong + "&numOfRows=" + numOfRows + "&pageNo=" + pageNo + "&_type=xml";
//
//            try{
//                URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
//                InputStream is= url.openStream(); //url위치로 입력스트림 연결
//
//                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
//                XmlPullParser xpp= factory.newPullParser();
//                xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
//
//                String tag;
//                xpp.next();
//                int eventType= xpp.getEventType();
//
//                while(eventType != XmlPullParser.END_DOCUMENT){
//                    switch(eventType){
//                      case  XmlPullParser.START_DOCUMENT:
//                        break;
//                      case XmlPullParser.START_TAG:
//                            tag= xpp.getName();//태그 이름 얻어오기
//
//                            if(tag.equals("item")) ;
//                            else if(tag.equals("nodeid")) {
//                                buffer.append("정류소 ID : ");
//                                xpp.next();
//                                buffer.append(xpp.getText());
//                                buffer.append("\n\n");
//                            }
//                            else if(tag.equals("nodenm")) {
//                                buffer.append("정류소 명 : ");
//                                xpp.next();
//                                buffer.append(xpp.getText());
//                                buffer.append("\n\n");
//                            }
//                            else if(tag.equals("citycode")) {
//                                buffer.append("도시코드 : ");
//                                xpp.next();
//                                buffer.append(xpp.getText() +"층");
//                                buffer.append("\n\n");
//                            }
//                            break;
//                      case XmlPullParser.TEXT:
//                          break;
//                      case XmlPullParser.END_TAG:
//                            tag= xpp.getName();
//                            if(tag.equals("item")) buffer.append("\n");
//                            break;
//
//                    }
//                    eventType= xpp.next();
//                }
//            }catch (Exception e){
//
//            }
//            return buffer.toString();//StringBuffer 문자열 객체 반환
//        }
    }

    private void drawerInit(String myAddress){
        String[] add = myAddress.split(" ");
        final String temURL = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query="+add[1]+" "+add[2]+"날씨"; //웹크롤링 할 주소(1)
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
                    areaText.setText(add[2]);

                    //번들에 문자열 포장
                    bundle.putString("temperature", tem);
                    bundle.putString("fine", fineDust);
                    bundle.putString("ultra", ultraFineDust);
                    bundle.putString("weather", weather);
                    bundle.putString("covid", covidNum);
                    Message msg = handler.obtainMessage();

                    //메세지내용 번들로 지정
                    msg.setData(bundle);

                    //핸들러에 메세지 전달
                    handler.sendMessage(msg);

                    //10분마다 한번씩 실행
                    Thread.sleep(600000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            drawer_input();
        }
    };

    private void drawer_input() {
        temText.setText(tem + "C");

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

        covidText.setText("전일 코로나 확진자 수 '" + covidNum + "' 명");
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
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[]grandResults) {
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length){//요청 코드가 동일하고, 요청한 퍼미션 만큼 수신되었다면 실행
            boolean check_result = true;

            //퍼미션이 모두 허용됫는지 체크
            for (int result : grandResults){
                if(result != PackageManager.PERMISSION_GRANTED){//허용이 아닐경우
                    check_result = false;//false로 저장
                    break;
                }
            }



            if(check_result){

            }else{//거부된 퍼미션이 있을경우
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,REQUIRED_PERMISSIONS[0])||ActivityCompat.shouldShowRequestPermissionRationale(this,REQUIRED_PERMISSIONS[1])){

                    Toast.makeText(Maps_Activity.this, "권한이 거부되었습니다. 앱을 다시 실행하여 권한을 허용해주세요", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(Maps_Activity.this, "권한이 거부되었습니다. 설정(앱 정보)에서 권한을 허용해주세요", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    void checkRunTimePermission(){//런타임 퍼미션 처리
        //위치 퍼미션 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this,Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Maps_Activity.this,Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {//퍼미션을 가지고있다면

        }else{//권한이 없다면
            if(ActivityCompat.shouldShowRequestPermissionRationale(Maps_Activity.this,REQUIRED_PERMISSIONS[0])){//권한 요청을 거부한적이 있을때
                Toast.makeText(Maps_Activity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(Maps_Activity.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);//사용자에게 권한 요청
            }else{//요청이 처음이라면
                ActivityCompat.requestPermissions(Maps_Activity.this, REQUIRED_PERMISSIONS,PERMISSIONS_REQUEST_CODE);//사용자에게 권한 요청
            }
        }
    }

    public String getCurrentAddress(double latitude, double longitude){//주소 찾기
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());//역지오코딩 위한 지오코딩 객체 선언

        List<Address> addresses;//주소를 저장할 주소리스트 선언

        try{
            addresses = geocoder.getFromLocation(latitude,longitude,7);//매개변수로 입력받은 경도위도로 주소 찾기
        }catch (IOException ioException){//에러시
            Toast.makeText(Maps_Activity.this, "지오코더 서비스 사용불가", Toast.LENGTH_SHORT).show();
            return "지오코더 서비스 사용불가";
        }catch (IllegalArgumentException illegalArgumentException){
            Toast.makeText(Maps_Activity.this, "잘못된 GPS 좌표", Toast.LENGTH_SHORT).show();
            return "잘못된 GPS 좌표";
        }

        if(addresses == null || addresses.size() == 0){//나온 주소가 없을때
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_SHORT).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);//주소객체에 주소값 저장
        return address.getAddressLine(0).toString()+"\n";//리턴
    }

    private void showDialogForLocationServiceSetting(){//GPS 활성화가 안되있을때 허용하게 나오게 하는 다이알로그

        AlertDialog.Builder builder = new AlertDialog.Builder(Maps_Activity.this);//다이얼로그 생성
        builder.setTitle("위치 서비스 비활성화");//타이틀 설정
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"+"위치 설정을 수정하실래요?");//내용 설정
        builder.setCancelable(true);//버튼 추가
        builder.setPositiveButton("설정",new DialogInterface.OnClickListener(){//수락 버튼 설정
            @Override
            public void onClick(DialogInterface dialog, int id){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode){

            case GPS_ENABLE_REQUEST_CODE:
                if(checkLocationServiceStatus()){
                    if(checkLocationServiceStatus()){
                        Log.d("위치권한 확인","true");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServiceStatus(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}