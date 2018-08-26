package com.project.graduate.neartheplace.MyInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.graduate.neartheplace.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MyInfoModifyActivity extends AppCompatActivity{

    private Button      mModify_finish;
    private Button      mModify_cancle;
    private EditText    mModify_name;
    private EditText    mModify_email;
    private EditText    mModify_password;
    private EditText    mModify_password2;
    private EditText    mModify_nickName;
    private RadioGroup  mModify_gender;
    private RadioButton mModify_genderSelect;
    private RadioButton mModify_male;
    private RadioButton mModify_female;
    private RadioGroup  mModify_position;
    private RadioButton mModify_positionSelect;
    private RadioButton mModify_normal;
    private RadioButton mModify_soldier;
    private Spinner     mModify_yearSpinner;
    private Spinner     mModify_monthSpinner;
    private Spinner     mModify_daySpinner;
    private Intent      intent;
    private String      userEmail;
    private String      userName;
    private String      userNickName;
    private String      userGender;
    private String      userPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_modify);

        mModify_finish          = (Button) findViewById(R.id.modify_finish);
        mModify_cancle          = (Button) findViewById(R.id.modify_cancle);
        mModify_name            = (EditText) findViewById(R.id.modify_name);
        mModify_nickName        = (EditText) findViewById(R.id.modify_nickName);
        mModify_email           = (EditText) findViewById(R.id.modify_email);
        mModify_password        = (EditText) findViewById(R.id.modify_password);
        mModify_password2       = (EditText) findViewById(R.id.modify_password2);
        mModify_yearSpinner     = (Spinner) findViewById(R.id.modify_birthYear);
        mModify_monthSpinner    = (Spinner) findViewById(R.id.modify_birthMonth);
        mModify_daySpinner      = (Spinner) findViewById(R.id.modify_birthDay);
        mModify_gender          = (RadioGroup) findViewById(R.id.modify_gender);
        mModify_genderSelect    = (RadioButton)findViewById(mModify_gender.getCheckedRadioButtonId());
        mModify_position        = (RadioGroup) findViewById(R.id.modify_position);
        mModify_positionSelect  = (RadioButton)findViewById(mModify_position.getCheckedRadioButtonId());
        mModify_male            = (RadioButton)findViewById(R.id.modify_male);
        mModify_female          = (RadioButton)findViewById(R.id.modify_female);
        mModify_normal          = (RadioButton)findViewById(R.id.modify_normal);
        mModify_soldier         = (RadioButton)findViewById(R.id.modify_soldier);

        intent = getIntent();
        userEmail = intent.getStringExtra("email");
        userName = intent.getStringExtra("name");
        userNickName = intent.getStringExtra("nickname");
        userGender = intent.getStringExtra("gender");
        userPosition = intent.getStringExtra("posi");

        mModify_name.setText(userName);
        mModify_email.setText(userEmail);
        mModify_nickName.setText(userNickName);

        if(userGender.equals("남자")){
            mModify_male.setChecked(true);
        }else if(userGender.equals("여자")){
            mModify_female.setChecked(true);
        }

        if(userPosition.equals("일반인")){
            mModify_normal.setChecked(true);
        }else if(userPosition.equals("군인")){
            mModify_soldier.setChecked(true);
        }

        // Set the Birth Day Spinner
        initSpinner();

        mModify_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo: 변경 완료(이메일, 비밀번호 등등.. 형식 확인 코드) 및 Server Connect 코드 작성
                Toast.makeText(MyInfoModifyActivity.this, "개발 예정(미구현)", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mModify_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initSpinner() {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        ArrayList<Integer> bYear = new ArrayList<Integer>();
        ArrayList<Integer> bMonth = new ArrayList<Integer>();
        ArrayList<Integer> bDay = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++) {
            bYear.add(year--);
        }
        for (int i = 1; i < 13; i++) {
            bMonth.add(i);
        }
        for (int i = 1; i < 32; i++) {
            bDay.add(i);
        }

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, bYear);
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, bMonth);
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, bDay);

        mModify_yearSpinner.setAdapter(yearAdapter);
        mModify_yearSpinner.setSelection(0);

        mModify_monthSpinner.setAdapter(monthAdapter);
        mModify_monthSpinner.setSelection(0);

        mModify_daySpinner.setAdapter(dayAdapter);
        mModify_daySpinner.setSelection(0);
    }


}
