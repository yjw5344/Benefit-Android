package com.project.graduate.neartheplace.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.project.graduate.neartheplace.Map.MapsActivity;
import com.project.graduate.neartheplace.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainFragment extends Fragment {

    private ImageButton  mapBtn;
    private JSONObject   resultJsonObject;
    private String       userToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container ,false);


//        SendMainActivity sendMainActivity = new SendMainActivity();
//        sendMainActivity.execute();


        userToken = getArguments().getString("userToken");
//        Toast.makeText(getActivity(),userToken, Toast.LENGTH_SHORT).show();

        //todo : 코드 정리 및 현재의 위도경도 받아오는 코드 작성... UI 제작



        mapBtn = (ImageButton) view.findViewById(R.id.mapIcon);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectMapActicity = new Intent(getActivity(), MapsActivity.class );
                startActivity(connectMapActicity);
            }
        });

        return view;
    }

    public class SendMainActivity extends AsyncTask<Void, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... voids) {

            //// okHttp 설정
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/map/37.561232/126.986198")
                    .header("X-Access-Token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RAbmF2ZXIuY29tIiwibmlja25hbWUiOiJ0ZXN0MSIsImlhdCI6MTUzNDQzMDk2NCwiZXhwIjoxNTM1MDM1NzY0fQ.-7DQHqKm_izSgZVOEnmRpDx7PjYQfpB5wxNcDel-85U")
                    .build();


            Response responseClient = null;
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

