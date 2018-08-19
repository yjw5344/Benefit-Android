package com.project.graduate.neartheplace.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.project.graduate.neartheplace.R;


public class CustomViewPagerAdapter extends FragmentPagerAdapter implements SmartTabLayout.TabProvider {

    private final Context context;

    public enum CustomTab {
        TAB_1(0, "할인정보", R.drawable.icon_customtab_saleinfo),
        TAB_2(1, "게시판", R.drawable.icon_customtab_board),
        TAB_3(2, "홈", R.drawable.icon_customtab_main),
        TAB_4(3, "달력", R.drawable.icon_customtab_calc),
        TAB_5(4, "내 정보", R.drawable.icon_customtab_myinfo);

        public int index;
        public String title;
        public int resourceId;
        public Fragment fragment;

        CustomTab(int index, String title, int resourceId) {
            this.index = index;
            this.title = title;
            this.resourceId = resourceId;
        }
    }

    public CustomViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return CustomTab.values()[position].fragment;
    }

    @Override
    public int getCount() {
        return CustomTab.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CustomTab.values()[position].title;
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.activity_main_customtab, null);

        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (v != null) {
            ImageView icon = (ImageView) v.findViewById(R.id.customIcon);
            TextView tmp = (TextView) v.findViewById(R.id.customText);
            icon.setImageResource(CustomTab.values()[position].resourceId);
            tmp.setText(CustomTab.values()[position].title);
        }

        return v;
    }
}
