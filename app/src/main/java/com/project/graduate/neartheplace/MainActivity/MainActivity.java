package com.project.graduate.neartheplace.MainActivity;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.widget.TabHost;
import com.project.graduate.neartheplace.Fragment.BoardFragment;
import com.project.graduate.neartheplace.Fragment.DiscountFragment;
import com.project.graduate.neartheplace.Fragment.CalendarFragment;
import com.project.graduate.neartheplace.Fragment.MainFragment;
import com.project.graduate.neartheplace.Fragment.MyInfoFragment;
import com.project.graduate.neartheplace.R;


public class MainActivity extends FragmentActivity{

    FragmentTabHost host;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Load token
        SharedPreferences preferences = getSharedPreferences("userToken", MODE_PRIVATE);
        userToken = preferences.getString("token","0");
        Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
        bundle.putString("userToken", userToken);

        // FragmentTab
        host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        host.setup(this, getSupportFragmentManager(), R.id.content);


        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1"); // 구분자
        tabSpec1.setIndicator("할인"); // 탭 이름
        host.addTab(tabSpec1, DiscountFragment.class, bundle);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2");
        tabSpec2.setIndicator("게시판");
        host.addTab(tabSpec2, BoardFragment.class, bundle);

        TabHost.TabSpec tabSpec3 = host.newTabSpec("tab3");
        tabSpec3.setIndicator("메인");
        host.addTab(tabSpec3, MainFragment.class, bundle);

        TabHost.TabSpec tabSpec4 = host.newTabSpec("tab4");
        tabSpec4.setIndicator("달력");
        host.addTab(tabSpec4, CalendarFragment.class, bundle);

        TabHost.TabSpec tabSpec5= host.newTabSpec("tab5");
        tabSpec5.setIndicator("내 정보");
        host.addTab(tabSpec5, MyInfoFragment.class, bundle);


        //todo: 차후에 UI 정리 !!
        // Tab 배경 색 설정!
//        host.getTabWidget().getChildAt(0)
//                .setBackgroundColor(Color.parseColor("#B0C4DE"));
//        host.getTabWidget().getChildAt(1)
//                .setBackgroundColor(Color.parseColor("#B0C4DE"));
//        host.getTabWidget().getChildAt(2)
//                .setBackgroundColor(Color.parseColor("#B0C4DE"));
//        host.getTabWidget().getChildAt(3)
//                .setBackgroundColor(Color.parseColor("#B0C4DE"));
//        host.getTabWidget().getChildAt(4)
//                .setBackgroundColor(Color.parseColor("#B0C4DE"));

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
