package com.project.graduate.neartheplace.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.project.graduate.neartheplace.Board.BoardDialog;
import com.project.graduate.neartheplace.Board.BoardListAdapter;
import com.project.graduate.neartheplace.Board.BoardText;
import com.project.graduate.neartheplace.DiscountInfo.DiscountBankDialog;
import com.project.graduate.neartheplace.DiscountInfo.DiscountDialog;
import com.project.graduate.neartheplace.DiscountInfo.DiscountInfo;
import com.project.graduate.neartheplace.DiscountInfo.DiscountListAdapter;
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

public class DiscountFragment extends Fragment {

    private String              userToken;
    private Button              cultureBtn;
    private Button              bakeryBtn;
    private Button              beautyBtn;
    private Button              shopBtn;
    private Button              sportsBtn;
    private Button              cafeBtn;
    private Button              tripBtn;
    private Button              movieBtn;
    private Button              foodBtn;
    private Button              etcBtn;
    private ListView            listView;
    private DiscountListAdapter adapter;
    private DiscountDialog      dialog;
    private DiscountBankDialog  bankDialog;
    private String              category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discountinfo, container, false);

        userToken = getArguments().getString("userToken");

        cultureBtn = (Button) view.findViewById(R.id.cultureBtn);
        bakeryBtn  = (Button) view.findViewById(R.id.bakeryBtn);
        beautyBtn  = (Button) view.findViewById(R.id.beautyBtn);
        sportsBtn  = (Button) view.findViewById(R.id.sportsBtn);
        shopBtn    = (Button) view.findViewById(R.id.shopBtn);
        cafeBtn    = (Button) view.findViewById(R.id.cafeBtn);
        tripBtn    = (Button) view.findViewById(R.id.tripBtn);
        movieBtn   = (Button) view.findViewById(R.id.movieBtn);
        foodBtn    = (Button) view.findViewById(R.id.foodBtn);
        etcBtn     = (Button) view.findViewById(R.id.etcBtn);
        listView     = (ListView) view.findViewById(R.id.discountListView);

//        getDiscountInfo("문화");


        cultureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "문화";
                getDiscountInfo(category);
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "음식";
                getDiscountInfo(category);
            }
        });

        bakeryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "베이커리";
                getDiscountInfo(category);
            }
        });

        beautyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "뷰티";
                getDiscountInfo(category);
            }
        });

        sportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "스포츠";
                getDiscountInfo(category);
            }
        });

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "쇼핑";
                getDiscountInfo(category);
            }
        });

        cafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "카페";
                getDiscountInfo(category);
            }
        });

        tripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "여행";
                getDiscountInfo(category);
            }
        });

        movieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "영화";
                getDiscountInfo(category);
            }
        });

        etcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "기타";
                getDiscountInfo(category);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DiscountInfo selectItem = (DiscountInfo) adapterView.getItemAtPosition(i);
                setDialog(selectItem);
            }
        });

        return view;
    }

    public void setDialog(DiscountInfo selectItem){
        if(selectItem.getCompany().equals("금융")){
            bankDialog = new DiscountBankDialog(getActivity(),mcloseListener);
            bankDialog.setCancelable(true);
            bankDialog.getWindow().setGravity(Gravity.CENTER);
            bankDialog.show();
        }else{
            dialog = new DiscountDialog(getActivity(), closeListener, selectItem);
            dialog.setCancelable(true);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
        }
    }

    private View.OnClickListener mcloseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bankDialog.dismiss();
        }
    };

    private View.OnClickListener closeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    };

    public void getDiscountInfo(String mCategory){
        GetDiscountInfo getDiscountInfo = new GetDiscountInfo(mCategory);
        getDiscountInfo.execute();
    }

    public class GetDiscountInfo extends AsyncTask<Void, Void, JSONObject>{

        private String mcatagory;

        public GetDiscountInfo(String mcatagory) {
            this.mcatagory = mcatagory;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/benefit/category/" + mcatagory)
                    .header("X-Access-Token",userToken)
                    .build();

            Response responseClient = null;
            JSONObject resultJson = null;
            try {
                responseClient = client.newCall(request).execute();
                resultJson = new JSONObject(responseClient.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            String company;
            String telecomKT;
            String telecomSKT;
            String telecomLGU;
            String army;

            List<DiscountInfo> discountList = new ArrayList<DiscountInfo>();
            try {
                JSONArray dataList = new JSONArray(jsonObject.getString("data"));
                for(int i = 0; i < dataList.length(); i++){
                    JSONObject discountData = dataList.getJSONObject(i);
                    company = discountData.getString("company");
                    army = discountData.getString("a_benefit");
                    JSONArray teleList = new JSONArray(discountData.getString("o_benefit"));
                    telecomSKT = teleList.getJSONObject(0).getString("discount");
                    telecomKT = teleList.getJSONObject(1).getString("discount");
                    telecomLGU = teleList.getJSONObject(2).getString("discount");

                    discountList.add(new DiscountInfo(company,telecomKT,telecomSKT,telecomLGU,army));
                    if(mcatagory.equals("기타")){
                        discountList.add(new DiscountInfo("시험","혜택 없음","혜택 없음","혜택 없음","토익 : 응시료 50% 할인\n오픽 : 응시료 30% 할인"));
                        discountList.add(new DiscountInfo("금융","혜택 없음","혜택 없음","혜택 없음","혜택없음"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new DiscountListAdapter(getActivity(), discountList);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onCancelled() {
            Log.d("discountFragment","Connect Fail");
        }


    }

}
