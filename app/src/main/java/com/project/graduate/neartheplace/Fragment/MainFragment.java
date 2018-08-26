package com.project.graduate.neartheplace.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.graduate.neartheplace.MainFragment.CategoryDialog;
import com.project.graduate.neartheplace.MainFragment.DistanceDialog;
import com.project.graduate.neartheplace.MainFragment.LocationForm;
import com.project.graduate.neartheplace.MainFragment.MainDialog;
import com.project.graduate.neartheplace.MainFragment.MainListAdapter;
import com.project.graduate.neartheplace.MainFragment.Store;
import com.project.graduate.neartheplace.Map.MapsActivity;
import com.project.graduate.neartheplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE_PERMISSIONS = 1;
    private Button mainCategoryBtn;
    private Button mainDistanceBtn;
    private ImageButton mainMapBtn;
    private String userToken;
    private ListView listView;
    private MainListAdapter adapter;
    private MainDialog mainDialog;
    private CategoryDialog categoryDialog;
    private DistanceDialog distanceDialog;
    private String selectDistance;
    private String selectCategory;
    private Double latitude;
    private Double longitude;
    private List<LocationForm> locationList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        userToken = getArguments().getString("userToken");

        mainCategoryBtn = (Button) view.findViewById(R.id.mainCategoryBtn);
        mainDistanceBtn = (Button) view.findViewById(R.id.mainDistanceBtn);
        mainMapBtn = (ImageButton) view.findViewById(R.id.mainMapBtn);
        listView = (ListView) view.findViewById(R.id.mainListview);

        // 초기 데이터 default 설정!
        selectDistance = "1km";
        selectCategory = "음식";
        latitude = 0.0;
        longitude = 0.0;

        getLocation();

        mainCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCategoryDialog();
            }
        });

        mainDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDistanceDialog();
            }
        });

        mainMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectMapActicity = new Intent(getActivity(), MapsActivity.class);
                connectMapActicity.putParcelableArrayListExtra("location", (ArrayList<? extends Parcelable>) locationList);
                connectMapActicity.putExtra("latitude", latitude);
                connectMapActicity.putExtra("longitude", longitude);
                startActivity(connectMapActicity);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Store selectItem = (Store) adapterView.getItemAtPosition(i);
                makeMainDialog(selectItem);
            }
        });

        return view;
    }

    public void makeMainDialog(Store selectItem) {
        mainDialog = new MainDialog(getActivity(), mainCloseBtn, selectItem, userToken);
        mainDialog.setCancelable(true);
        mainDialog.getWindow().setGravity(Gravity.CENTER);
        mainDialog.show();
    }

    private View.OnClickListener mainCloseBtn = new View.OnClickListener() {
        public void onClick(View v) {
            mainDialog.dismiss();
        }
    };

    public void makeCategoryDialog() {
        categoryDialog = new CategoryDialog(getActivity(), categorySelectBtn, categoryCloseBtn);
        categoryDialog.setCancelable(true);
        categoryDialog.getWindow().setGravity(Gravity.CENTER);
        categoryDialog.show();
    }

    private View.OnClickListener categorySelectBtn = new View.OnClickListener() {
        public void onClick(View v) {
            categoryDialog.setSelectRadioButton();
            selectCategory = categoryDialog.getSelectRadioButton();
            categoryDialog.dismiss();
            getLocation();
        }
    };

    private View.OnClickListener categoryCloseBtn = new View.OnClickListener() {
        public void onClick(View v) {
            categoryDialog.dismiss();
        }
    };

    public void makeDistanceDialog() {
        distanceDialog = new DistanceDialog(getActivity(), distanceSelectBtn, distanceCloseBtn);
        distanceDialog.setCancelable(true);
        distanceDialog.getWindow().setGravity(Gravity.CENTER);
        distanceDialog.show();
    }

    private View.OnClickListener distanceSelectBtn = new View.OnClickListener() {
        public void onClick(View v) {
            distanceDialog.setSelectRadioButton();
            selectDistance = distanceDialog.getSelectRadioButton();
            distanceDialog.dismiss();
            getLocation();
        }
    };

    private View.OnClickListener distanceCloseBtn = new View.OnClickListener() {
        public void onClick(View v) {
            distanceDialog.dismiss();
        }
    };

    public void GetStoreData(Double mlatitude, Double mlogitude) {

        latitude = mlatitude;
        longitude = mlogitude;

        GetStoreInfo getStoreInfo = new GetStoreInfo(selectCategory, selectDistance, latitude, longitude);
        getStoreInfo.execute();
    }

    public class GetStoreInfo extends AsyncTask<Void, Void, JSONObject> {

        String mCategory;
        float mDistance;
        Double mLatitude;
        Double mLongitude;

        public GetStoreInfo(String selectCategory, String selectDistance, Double latitude, Double longitude) {
            this.mCategory = selectCategory;
            this.mLatitude = latitude;
            this.mLongitude = longitude;

            if (selectDistance.equals("300m")) {
                this.mDistance = 300;
            } else if (selectDistance.equals("500m")) {
                this.mDistance = 500;
            } else if (selectDistance.equals("1km")) {
                this.mDistance = 1000;
            } else if (selectDistance.equals("3km")) {
                this.mDistance = 3000;
            }
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/map/" + mLatitude + "/" + mLongitude + "/" + mCategory + "?distance=" + mDistance)
//                    .url("http://13.125.61.58:9090/map/" + "37.561206" + "/" + "126.986192" + "/" + mCategory +"?distance=" + mDistance)
                    .header("X-Access-Token", userToken)
                    .build();

            Response responseClient = null;
            JSONObject resultJsonObject = null;
            try {
                responseClient = client.newCall(request).execute();
                resultJsonObject = new JSONObject(responseClient.body().string());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            List<Store> storeList = new ArrayList<Store>();
            locationList = new ArrayList<LocationForm>();
            try {
                JSONArray dataList = new JSONArray(jsonObject.getString("data"));
                for (int i = 0; i < dataList.length(); i++) {
                    JSONObject targetData = dataList.getJSONObject(i);
                    storeList.add(new Store(targetData.getString("company").toString(), targetData.getString("branch").toString(), targetData.getString("address").toString(), targetData.getString("telephone").toString(), targetData.getString("category").toString(), targetData.getString("img").toString(), Double.parseDouble(targetData.getString("latitude").toString()), Double.parseDouble(targetData.getString("longitude").toString())));
                    locationList.add(new LocationForm(Double.parseDouble(targetData.getString("latitude").toString()), Double.parseDouble(targetData.getString("longitude").toString()), targetData.getString("company").toString(), targetData.getString("branch").toString(), targetData.getString("address").toString()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new MainListAdapter(getActivity(), storeList);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity(), "통신실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void getLocation() {

        // fusedLocationAPI 사용을 위한 준비
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // if문 -> 퍼미션 권한 확인!
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 퍼미션 권한이 없으면 실행

            // 퍼미션 request 요청 ( Activity일 경우, Fragment일 경우 주의! -> Fragment일 경우 ActivityCompat.requestPermissions 사용하면 안됨.. OnRequestPermissionResult 호출 불가 )
            requestPermissions( new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);

            // 퍼미션 권한 확인 다이얼로그 종료
            return ;
        }else{
            // 퍼미션 권한이 있을 경우 실행
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        GetStoreData(location.getLatitude(),location.getLongitude());
                    }else{
                        Log.d(" Error","위치가져오기 실패");
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }else{
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                GetStoreData(location.getLatitude(),location.getLongitude());
                            }
                        }
                    });
                }
        }
    }

}