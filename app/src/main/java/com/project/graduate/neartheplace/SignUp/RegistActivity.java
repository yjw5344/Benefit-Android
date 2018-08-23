package com.project.graduate.neartheplace.SignUp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.graduate.neartheplace.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistActivity extends AppCompatActivity {


    private Button              mRegist_finish;
    private Button              mRegist_cancle;
    private EditText            mRegist_name;
    private EditText            mRegist_email;
    private EditText            mRegist_password;
    private EditText            mRegist_password2;
    private EditText            mRegist_nickName;
    private RadioGroup          mRegist_gender;
    private RadioButton         mRegist_genderSelect;
    private RadioGroup          mRegist_position;
    private RadioButton         mRegist_positionSelect;
//    private Spinner             mRegist_yearSpinner;
//    private Spinner             mRegist_monthSpinner;
//    private Spinner             mRegist_daySpinner;
    private ArrayList<EditText> editTexts;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        // Set up "UI" 변수
        mRegist_finish          = (Button) findViewById(R.id.regist_finish);
        mRegist_cancle          = (Button) findViewById(R.id.regist_cancle);
        mRegist_name            = (EditText) findViewById(R.id.regist_name);
        mRegist_nickName        = (EditText) findViewById(R.id.regist_nickname);
        mRegist_email           = (EditText) findViewById(R.id.regist_email);
        mRegist_password        = (EditText) findViewById(R.id.regist_password);
        mRegist_password2       = (EditText) findViewById(R.id.regist_password2);
//        mRegist_yearSpinner     = (Spinner) findViewById(R.id.regist_birthYear);
//        mRegist_monthSpinner    = (Spinner) findViewById(R.id.regist_birthMonth);
//        mRegist_daySpinner      = (Spinner) findViewById(R.id.regist_birthDay);
        mRegist_gender          = (RadioGroup) findViewById(R.id.regist_gender);
        mRegist_genderSelect    = (RadioButton)findViewById(mRegist_gender.getCheckedRadioButtonId());
        mRegist_position        = (RadioGroup) findViewById(R.id.regist_position);
        mRegist_positionSelect  = (RadioButton)findViewById(mRegist_position.getCheckedRadioButtonId());

        editTexts = new ArrayList<EditText>();

        // Set the Birth Day Spinner
//        initSpinner();

        // Set the Regist finish Button
        mRegist_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set the Regist finish Button
        mRegist_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTexts.add(mRegist_name);
                editTexts.add(mRegist_email);
                editTexts.add(mRegist_password);
                editTexts.add(mRegist_password2);
                editTexts.add(mRegist_nickName);
                boolean nullFlag = nullCheck(editTexts);
                if(nullFlag){
                    boolean passFlag = passwordCheck(mRegist_password.getText().toString(), mRegist_password2.getText().toString());
                    if(passFlag){
                        //todo : email형식체크, 비밀번호 형식체크, 이름 및 닉네임 형식체크 ( 대소문자 6~12자, 특수문자제외 등등... )
                        //todo : 이메일, 닉네임 중복처리!

                        SendRegistUser sendRegistUser = new SendRegistUser(mRegist_name.getText().toString(), mRegist_email.getText().toString(), mRegist_password.getText().toString(),mRegist_nickName.getText().toString(), mRegist_genderSelect.getText().toString(), mRegist_positionSelect.getText().toString());
                        sendRegistUser.execute();
                        finish();
                    }
                }
            }
        });


    }

//    private void initSpinner() {
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//
//        ArrayList<Integer> bYear = new ArrayList<Integer>();
//        ArrayList<Integer> bMonth = new ArrayList<Integer>();
//        ArrayList<Integer> bDay = new ArrayList<Integer>();
//
//        for (int i = 0; i < 100; i++) {
//            bYear.add(year--);
//        }
//        for (int i = 1; i < 13; i++) {
//            bMonth.add(i);
//        }
//        for (int i = 1; i < 32; i++) {
//            bDay.add(i);
//        }
//
//        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, bYear);
//        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, bMonth);
//        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, bDay);
//
//        mRegist_yearSpinner.setAdapter(yearAdapter);
//        mRegist_yearSpinner.setSelection(0);
//
//        mRegist_monthSpinner.setAdapter(monthAdapter);
//        mRegist_monthSpinner.setSelection(0);
//
//        mRegist_daySpinner.setAdapter(dayAdapter);
//        mRegist_daySpinner.setSelection(0);
//    }

    public boolean nullCheck(ArrayList<EditText> list){

        for(int i = 0;  i < list.size() ; i++){
            if(TextUtils.isEmpty(list.get(i).getText())){
                Toast.makeText(RegistActivity.this,"빈칸을 입력하세요",Toast.LENGTH_SHORT).show();
                list.get(i).requestFocus();
                return false;
            }
        }
        return true;
    }

    public boolean passwordCheck(String pw1, String pw2){
        if(pw1.length() < 6){
            mRegist_password.requestFocus();
            Toast.makeText(RegistActivity.this,"비밀번호을 6자 이상 입력하세요",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(pw1.equals(pw2)){
                return true;
            }else{
                mRegist_password2.requestFocus();
                Toast.makeText(RegistActivity.this,"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public class SendRegistUser extends AsyncTask<Void, Void, Boolean>{

        private String  name;
        private String  email;
        private String  password;
        private String  nickname;
        private String  gender;
        private String  position;
        private String  mFlag;

        public SendRegistUser(String name, String email, String password, String nickname, String gender, String position) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.gender = gender;
            this.position = position;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("name", name)
                    .add("email", email)
                    .add("nickname", nickname)
                    .add("password", password)
                    .add("posi", position)
                    .add("mKind", "")
                    .add("mServiceStartDate", "")
                    .add("gender", gender)
                    .build();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/auth/signup")
                    .post(formBody)
                    .build();

            // async를 사용하는 경우
            Response responseClient = null;
            try {
                responseClient = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(responseClient.body().string());
                mFlag = jsonObject.getString("stat");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e("Json","JsonError");
            }

            if(mFlag.equals("success")){
                return true;
            }else if(mFlag.equals("fail")){
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            if(flag == false){
                Toast.makeText(RegistActivity.this,"중복된 사용자가있습니다",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled(Boolean flag) {
            Toast.makeText(RegistActivity.this,"회원가입 실패",Toast.LENGTH_SHORT).show();
        }
    }

}




