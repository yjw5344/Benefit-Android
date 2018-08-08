package com.project.graduate.neartheplace.MainActivity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.project.graduate.neartheplace.Fragment.CustomViewPagerAdapter;
import com.project.graduate.neartheplace.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton mapBtn;

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
    }


}
