package com.project.graduate.neartheplace.MainActivity;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.project.graduate.neartheplace.Fragment.BoardFragment;
import com.project.graduate.neartheplace.Fragment.CalcFragment;
import com.project.graduate.neartheplace.Fragment.DisCountInfoFragment;
import com.project.graduate.neartheplace.Fragment.MainFragment;
import com.project.graduate.neartheplace.Fragment.MyInfoFragment;
import com.project.graduate.neartheplace.R;


public class MainActivity extends FragmentActivity{

    FragmentTabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        host.setup(this, getSupportFragmentManager(), R.id.content);


        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1"); // 구분자
        tabSpec1.setIndicator("할인정보"); // 탭 이름
        host.addTab(tabSpec1, DisCountInfoFragment.class, null);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2");
        tabSpec2.setIndicator("게시판");
        host.addTab(tabSpec2, BoardFragment.class, null);

        TabHost.TabSpec tabSpec3 = host.newTabSpec("tab3");
        tabSpec3.setIndicator("메인");
        host.addTab(tabSpec3, MainFragment.class, null);

        TabHost.TabSpec tabSpec4 = host.newTabSpec("tab4");
        tabSpec4.setIndicator("달력");
        host.addTab(tabSpec4, CalcFragment.class, null);

        TabHost.TabSpec tabSpec5= host.newTabSpec("tab5");
        tabSpec5.setIndicator("내 정보");
        host.addTab(tabSpec5, MyInfoFragment.class, null);

        // Tab 배경 색 설정!
//        host.getTabWidget().getChildAt(0)
//                .setBackgroundColor(Color.parseColor("#3C989E"));
//        host.getTabWidget().getChildAt(1)
//                .setBackgroundColor(Color.parseColor("#5DB5A4"));
//        host.getTabWidget().getChildAt(2)
//                .setBackgroundColor(Color.parseColor("#F4CDA5"));
//        host.getTabWidget().getChildAt(3)
//                .setBackgroundColor(Color.parseColor("#F57A82"));

        host.setCurrentTab(2);

        // 선택된 Tab 글씩 색 변경
//        TextView temp = (TextView) host.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
//        temp.setTextColor(Color.parseColor("#000000"));
//
//        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) { // 탭 변경시 리스너
//                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
//                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); // 탭에 있는 TextView 지정후
//                    if (i == host.getCurrentTab())
//                        tv.setTextColor(Color.parseColor("#000000")); // 탭이 선택되어 있으면 FontColor를 검정색으로
//                    else
//                        tv.setTextColor(Color.parseColor("#ffffff")); // 선택되지 않은 탭은 하얀색으로.
//                }
//            }
//        });

    }


}
