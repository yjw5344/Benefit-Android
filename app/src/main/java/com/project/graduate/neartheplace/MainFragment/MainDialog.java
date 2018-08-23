package com.project.graduate.neartheplace.MainFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.graduate.neartheplace.Board.BoardText;
import com.project.graduate.neartheplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainDialog extends Dialog {

    private Button closeBtn;
    private View.OnClickListener closeListener;
    private Store storeDate;
    private String userToken;
    private TextView vKT;
    private TextView vSKT;
    private TextView vLGU;
    private TextView vArmy;
    private TextView vCompany;
    private TextView vBranch ;
    private TextView vAddress;
    private TextView vTelephone;
    private String army;
    private String SKT;
    private String KT;
    private String LGU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main);


        closeBtn = (Button) findViewById(R.id.dialog_main_cancle);

        vCompany = (TextView)findViewById(R.id.dialog_main_company);
        vBranch = (TextView)findViewById(R.id.dialog_main_branch);
        vAddress = (TextView)findViewById(R.id.dialog_main_address);
        vTelephone = (TextView)findViewById(R.id.dialog_main_telephone);
        vKT = (TextView)findViewById(R.id.dialog_main_KT);
        vSKT = (TextView)findViewById(R.id.dialog_main_SKT);
        vLGU = (TextView)findViewById(R.id.dialog_main_LGU);
        vArmy = (TextView)findViewById(R.id.dialog_main_army);

        closeBtn.setOnClickListener(closeListener);

        GetMainDialogDiscountInfo getMainDialogDiscountInfo = new GetMainDialogDiscountInfo(storeDate);
        getMainDialogDiscountInfo.execute();

    }

    public MainDialog(Context context, View.OnClickListener closeListener, Store storeDate, String userToken) {
        super(context);
        this.closeListener = closeListener;
        this.storeDate = storeDate;
        this.userToken = userToken;
    }


    public class GetMainDialogDiscountInfo extends AsyncTask<Void, Void, JSONObject>{

        Double mlatitude;
        Double mlongitude;
        Store selectItem;


        public GetMainDialogDiscountInfo(Store selectItem) {
            this.mlatitude = selectItem.getLatitude();
            this.mlongitude = selectItem.getLongitude();
            this.selectItem = selectItem;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/benefit/store/" + mlatitude + "/" + mlongitude)
                    .header("X-Access-Token",userToken)
                    .build();

            Response responseClient = null;
            JSONObject result = null;
            try {
                responseClient = client.newCall(request).execute();
                result = new JSONObject(responseClient.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            vCompany.setText(storeDate.getCompany());
            vBranch.setText(storeDate.getBranch());
            vAddress.setText(storeDate.getAddress());
            vTelephone.setText(storeDate.getTelephone());

            try {
                JSONArray teleList = new JSONArray(jsonObject.getString("o_benefit"));
                vSKT.setText(teleList.getJSONObject(0).getString("discount"));
                vKT.setText(teleList.getJSONObject(1).getString("discount"));
                vLGU.setText(teleList.getJSONObject(2).getString("discount"));
                if(jsonObject.getString("a_benefit").equals("null")){
                    vArmy.setText("혜택 없음");
                }else{
                    vArmy.setText(jsonObject.getString("a_benefit"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ImageView vImg = (ImageView)findViewById(R.id.dialog_main_img);
            Glide.with(getContext())
                    .load(storeDate.getImgSrc())
                    .override(180,230)
                    .into(vImg);
        }

        @Override
        protected void onCancelled() {
            Log.d("DialgMain","Sever Error");

        }

    }


}
