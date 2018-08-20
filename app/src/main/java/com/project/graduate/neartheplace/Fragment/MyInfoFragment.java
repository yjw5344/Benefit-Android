package com.project.graduate.neartheplace.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.graduate.neartheplace.MyInfo.MyInfoModifyActivity;
import com.project.graduate.neartheplace.R;
import com.project.graduate.neartheplace.SignIn.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class MyInfoFragment extends Fragment {

    private Button   modifyBtn;
    private Button   logoutBtn;
    private String   userToken;
    private TextView mEmail;
    private TextView mName;
    private TextView mNickName;
    private TextView mGender;
    private TextView mPosition;
    private Intent   connectModifyActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_my_info, container, false);

        userToken = getArguments().getString("userToken");
        connectModifyActivity = new Intent(getActivity(), MyInfoModifyActivity.class);


        mEmail = (TextView) view.findViewById(R.id.myInfoEmail);
        mName = (TextView) view.findViewById(R.id.myInfoName);
        mNickName = (TextView) view.findViewById(R.id.myInfoNick);
        mGender = (TextView) view.findViewById(R.id.myInfoGender);
        mPosition = (TextView) view.findViewById(R.id.myInfoPosition);

        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.execute();

        modifyBtn = (Button) view.findViewById(R.id.myInfoModify);
        logoutBtn = (Button) view.findViewById(R.id.myInfoLogout);

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(connectModifyActivity);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userToken", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("token");
                editor.commit();

                Intent logout = new Intent(getActivity(), LoginActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logout);
            }
        });

        return view;
    }

    public class GetUserInfo extends AsyncTask<Void, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/mypage")
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
            JSONObject userInfoJson = new JSONObject();
            try {
                //todo: 사용자 생일의 경우 아직 미구현
                userInfoJson = jsonObject.getJSONObject("data");
                mEmail.setText(userInfoJson.getString("email"));
                mName.setText(userInfoJson.getString("name"));
                mNickName.setText(userInfoJson.getString("nickname"));
                mGender.setText(userInfoJson.getString("gender"));
                mPosition.setText(userInfoJson.getString("posi"));

                connectModifyActivity.putExtra("email",userInfoJson.getString("email"));
                connectModifyActivity.putExtra("name",userInfoJson.getString("name"));
                connectModifyActivity.putExtra("nickname",userInfoJson.getString("nickname"));
                connectModifyActivity.putExtra("gender",userInfoJson.getString("gender"));
                connectModifyActivity.putExtra("posi",userInfoJson.getString("posi"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity(),"나의정보가져오기 실패",Toast.LENGTH_SHORT).show();

        }
    }

}
