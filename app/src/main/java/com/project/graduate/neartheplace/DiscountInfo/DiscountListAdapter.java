package com.project.graduate.neartheplace.DiscountInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.graduate.neartheplace.R;

import java.util.List;

public class DiscountListAdapter extends BaseAdapter {

    private Context context;
    private List<DiscountInfo> discountList;

    public DiscountListAdapter(Context context, List<DiscountInfo> discountList) {
        this.context = context;
        this.discountList = discountList;
    }

    @Override
    public int getCount() {
        return discountList.size();
    }

    @Override
    public Object getItem(int i) {
        return discountList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.listview_discount, null);
        TextView discountCompany = (TextView) v.findViewById(R.id.dicountCompany);
        discountCompany.setText(discountList.get(i).getCompany());

        v.setTag(discountList.get(i).getCompany());
        return v;
    }
}
