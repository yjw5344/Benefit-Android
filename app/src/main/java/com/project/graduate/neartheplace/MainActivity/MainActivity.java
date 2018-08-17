package com.project.graduate.neartheplace.MainActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.project.graduate.neartheplace.Fragment.CustomViewPagerAdapter;
import com.project.graduate.neartheplace.R;
import com.project.graduate.neartheplace.SignIn.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton mapBtn;

    String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager(Fragment)-Setting / 하단 메뉴 세팅 ================================================================================

        // SmartTabLayout FragmentpagerAdapter 주석처리
//        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//                getSupportFragmentManager(), FragmentPagerItems.with(this)
//                .add("Info", InfoFragment.class)
//                .add("Board", BoardFragment.class)
//                .add("Main", MainFragment.class)
//                .add("Calc", CalcFragment.class)
//                .add("myInfo", MyInfoFragment.class)
//                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        CustomViewPagerAdapter mPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mPagerAdapter);
        viewPagerTab.setCustomTabView(mPagerAdapter);
        viewPagerTab.setViewPager(viewPager);
        viewPager.setCurrentItem(2);



        // Token 확인!
//        SharedPreferences preferences = getSharedPreferences("userToken", Activity.MODE_PRIVATE);
//        userToken = preferences.getString("token","NoToken");
//        Toast.makeText(MainActivity.this, userToken, Toast.LENGTH_SHORT).show();

        Log.d("MainActivity", "MainActivity Activate");




    }


}
