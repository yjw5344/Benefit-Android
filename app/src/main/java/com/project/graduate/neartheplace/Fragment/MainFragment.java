package com.project.graduate.neartheplace.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.project.graduate.neartheplace.MainFragment.CategoryDialog;
import com.project.graduate.neartheplace.MainFragment.DistanceDialog;
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

    private Button          mainCategoryBtn;
    private Button          mainDistanceBtn;
    private ImageButton     mainSearchBtn;
    private ImageButton     mainMapBtn;
    private String          userToken;
    private ListView        listView;
    private MainListAdapter adapter;
    private MainDialog      mainDialog;
    private CategoryDialog  categoryDialog;
    private DistanceDialog  distanceDialog;
    private String          selectDistance;
    private String          selectCategory;
    private float latitude;
    private float longitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container ,false);

        userToken = getArguments().getString("userToken");

        //todo : 위도 경도 받아오기

        mainCategoryBtn = (Button) view.findViewById(R.id.mainCategoryBtn);
        mainDistanceBtn = (Button) view.findViewById(R.id.mainDistanceBtn);
        mainSearchBtn   = (ImageButton) view.findViewById(R.id.mainSearchBtn);
        mainMapBtn      = (ImageButton) view.findViewById(R.id.mainMapBtn);
        listView        = (ListView) view.findViewById(R.id.mainListview);

        //처음 데이터 받아오기
        selectDistance = "1km";
        selectCategory = "음식";
        latitude = 0;
        longitude = 0;
        GetStoreData();

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

        mainSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"구현 예정입니다",Toast.LENGTH_SHORT).show();
            }
        });

        mainMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectMapActicity = new Intent(getActivity(), MapsActivity.class );
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

    public void makeMainDialog(Store selectItem){
        mainDialog = new MainDialog(getActivity(), mainCloseBtn, selectItem);
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
            GetStoreData();
        }
    };

    private View.OnClickListener categoryCloseBtn = new View.OnClickListener() {
        public void onClick(View v) {
            categoryDialog.dismiss();
        }
    };

    public void makeDistanceDialog(){
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
            GetStoreData();
        }
    };

    private View.OnClickListener distanceCloseBtn = new View.OnClickListener() {
        public void onClick(View v) {
            distanceDialog.dismiss();
        }
    };

    public void GetStoreData(){
        GetStoreInfo getStoreInfo = new GetStoreInfo(selectCategory,selectDistance,latitude,longitude);
        getStoreInfo.execute();
    }

    public class GetStoreInfo extends AsyncTask<Void, Void, JSONObject>{

        String mCategory;
        float mDistance;
        float mLatidude;
        float mLongitude;

        public GetStoreInfo(String selectCategory, String selectDistance, float latitude, float longitude) {
            this.mCategory = selectCategory;
            this.mLatidude = latitude;
            this.mLongitude = longitude;

            if(selectDistance.equals("300m")){
                this.mDistance = 300;
            }else if(selectDistance.equals("500m")){
                this.mDistance = 500;
            }else if(selectDistance.equals("1km")){
                this.mDistance = 1000;
            }else if(selectDistance.equals("3km")){
                this.mDistance = 3000;
            }
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/map/37.561232/126.986198/"+mCategory+"?distance=" + mDistance)
                    .header("X-Access-Token",userToken)
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
            try {
                JSONArray dataList = new JSONArray(jsonObject.getString("data"));
                for(int i = 0 ; i < dataList.length() ; i++){
                    JSONObject targetData = dataList.getJSONObject(i);
                    storeList.add(new Store( targetData.getString("company").toString(), targetData.getString("branch").toString(),targetData.getString("address").toString(),targetData.getString("telephone").toString(), targetData.getString("category").toString(),targetData.getString("img").toString(), Float.parseFloat(targetData.getString("latitude").toString()) , Float.parseFloat(targetData.getString("longitude").toString()) ));
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
            Log.d("Main", "ServerConnect Error");
        }
    }
}

