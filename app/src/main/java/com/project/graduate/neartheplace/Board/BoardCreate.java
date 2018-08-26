package com.project.graduate.neartheplace.Board;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.graduate.neartheplace.Fragment.BoardFragment;
import com.project.graduate.neartheplace.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BoardCreate extends AppCompatActivity {

    private Button      cancleBtn;
    private Button      completeBtn;
    private EditText    textTitle;
    private EditText    textContext;
    private String      userToken;
    private JSONObject  resultJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_create);

        cancleBtn     = (Button)findViewById(R.id.createCancle);
        completeBtn   = (Button)findViewById(R.id.createComplete);
        textTitle     = (EditText)findViewById(R.id.createTextTitle);
        textContext   = (EditText)findViewById(R.id.createTextContext);

        Intent intent = getIntent();
        userToken = intent.getStringExtra("userToken");

        cancleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                SendBoardCreate sendBoardCreate = new SendBoardCreate(textTitle.getText().toString(), textContext.getText().toString());
                sendBoardCreate.execute();

                setResult(1);
                finish();
            }
        });

    }

    public class SendBoardCreate extends AsyncTask<Void, Void, JSONObject> {

        private String mtextTitle;
        private String mtextContext;

        public SendBoardCreate(String textTitle, String textContext) {
            this.mtextTitle = textTitle;
            this.mtextContext = textContext;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("title", mtextTitle)
                    .add("content", mtextContext)
                    .build();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/board")
                    .header("X-Access-Token",userToken)
                    .post(formBody)
                    .build();

            // async를 사용하는 경우
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
            Log.d("BoardCreate","글추가 완료");
//            Toast.makeText(BoardCreate.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(BoardCreate.this, "통신실패", Toast.LENGTH_SHORT).show();
        }
    }
}
