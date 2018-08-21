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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.project.graduate.neartheplace.Board.BoardDialog;
import com.project.graduate.neartheplace.Board.BoardListAdapter;
import com.project.graduate.neartheplace.MainFragment.CategoryDialog;
import com.project.graduate.neartheplace.MainFragment.DistanceDialog;
import com.project.graduate.neartheplace.MainFragment.MainDialog;
import com.project.graduate.neartheplace.MainFragment.MainListAdapter;
import com.project.graduate.neartheplace.Map.MapsActivity;
import com.project.graduate.neartheplace.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainFragment extends Fragment {

    private Button mainCategoryBtn;
    private Button mainDistanceBtn;
    private ImageButton mainSearchBtn;
    private ImageButton mainMapBtn;
    private String userToken;
    private ListView listView;
    private MainListAdapter adapter;
    private MainDialog mainDialog;
    private CategoryDialog categoryDialog;
    private DistanceDialog distanceDialog;
    private String selectDistance;
    private String selectCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container ,false);

        userToken = getArguments().getString("userToken");

        mainCategoryBtn = (Button) view.findViewById(R.id.mainCategoryBtn);
        mainDistanceBtn = (Button) view.findViewById(R.id.mainDistanceBtn);
        mainSearchBtn = (ImageButton) view.findViewById(R.id.mainSearchBtn);
        mainMapBtn = (ImageButton) view.findViewById(R.id.mainMapBtn);

        mainCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCategoryDialog();
            }
        });

        mainDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        return view;
    }

    public void makeCategoryDialog() {
        categoryDialog = new CategoryDialog(getActivity(), categorySelectBtn, categoryCloseBtn);
        categoryDialog.setCancelable(true);
        categoryDialog.getWindow().setGravity(Gravity.CENTER);
        categoryDialog.show();
    }

    private View.OnClickListener categorySelectBtn = new View.OnClickListener() {
        public void onClick(View v) {
            selectCategory = categoryDialog.getSelectRadioButton().toString();
            categoryDialog.dismiss();
            Toast.
        }
    };

    private View.OnClickListener categoryCloseBtn = new View.OnClickListener() {
        public void onClick(View v) {
            categoryDialog.dismiss();
        }
    };

    public void getStoreData(){
        SendMainActivity sendMainActivity = new SendMainActivity();
        sendMainActivity.execute();
    }

    public class SendMainActivity extends AsyncTask<Void, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... voids) {

            //// okHttp 설정
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/map/37.561232/126.986198")
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
//            Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            Log.d("Main", "JSon");

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity(), "통신실패", Toast.LENGTH_SHORT).show();
            Log.d("Main", "ServerConnect Error");
        }
    }
}

