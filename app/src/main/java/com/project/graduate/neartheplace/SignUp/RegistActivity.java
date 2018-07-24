package com.project.graduate.neartheplace.SignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.project.graduate.neartheplace.MainActivity.MainActivity;
import com.project.graduate.neartheplace.R;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistActivity extends AppCompatActivity {


    private Button mRegist_finish;
    private EditText mRegist_name;
    private EditText mRegist_email;
    private EditText mRegist_password;
    private EditText mRegist_password2;
    private RadioGroup mRegist_gender;
    private RadioButton mRegist_genderSelct;
    private Spinner mRegist_yearSpinner;
    private Spinner mRegist_monthSpinner;
    private Spinner mRegist_daySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        // Set up "UI" 변수
        mRegist_finish = (Button) findViewById(R.id.regist_finish);
        mRegist_name = (EditText) findViewById(R.id.regist_name);
        mRegist_email = (EditText) findViewById(R.id.regist_email);
        mRegist_password = (EditText) findViewById(R.id.regist_password);
        mRegist_password2 = (EditText) findViewById(R.id.regist_password2);
        mRegist_yearSpinner = (Spinner) findViewById(R.id.regist_birthYear);
        mRegist_monthSpinner = (Spinner) findViewById(R.id.regist_birthMonth);
        mRegist_daySpinner = (Spinner) findViewById(R.id.regist_birthDay);
        mRegist_gender = (RadioGroup) findViewById(R.id.regist_gender);
        mRegist_genderSelct = (RadioButton)findViewById(mRegist_gender.getCheckedRadioButtonId());

        // Set the Birth Day Spinner
        initSpinner();

        // Set the Regist finish Button
        mRegist_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectMainActicity = new Intent(RegistActivity.this, MainActivity.class );
                startActivity(connectMainActicity);
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

        mRegist_yearSpinner.setAdapter(yearAdapter);
        mRegist_yearSpinner.setSelection(0);

        mRegist_monthSpinner.setAdapter(monthAdapter);
        mRegist_monthSpinner.setSelection(0);

        mRegist_daySpinner.setAdapter(dayAdapter);
        mRegist_daySpinner.setSelection(0);
    }

}




