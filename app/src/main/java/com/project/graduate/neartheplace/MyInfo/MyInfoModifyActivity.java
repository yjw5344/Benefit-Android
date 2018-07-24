package com.project.graduate.neartheplace.MyInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.project.graduate.neartheplace.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MyInfoModifyActivity extends AppCompatActivity{

    private Spinner mModify_yearSpinner;
    private Spinner mModify_monthSpinner;
    private Spinner mModify_daySpinner;
    private Button mModify_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_modify);

        mModify_yearSpinner = (Spinner) findViewById(R.id.modify_birthYear);
        mModify_monthSpinner = (Spinner) findViewById(R.id.modify_birthMonth);
        mModify_daySpinner = (Spinner) findViewById(R.id.modify_birthDay);
        mModify_finish = (Button)findViewById(R.id.modify_finish);


        // Set the Birth Day Spinner
        initSpinner();


        mModify_finish.setOnClickListener(new View.OnClickListener() {
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
