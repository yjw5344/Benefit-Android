package com.project.graduate.neartheplace.MainFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.graduate.neartheplace.Board.BoardText;
import com.project.graduate.neartheplace.R;

import java.util.List;

public class MainListAdapter extends BaseAdapter {

    private Context context;
    private List<Store> storeList;

    public MainListAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int i) {
        return storeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.listview_main, null);



        TextView listCompany = (TextView) v.findViewById(R.id.listCompany);
        TextView listBranch = (TextView) v.findViewById(R.id.listBranch);
        TextView listAddress = (TextView) v.findViewById(R.id.listAddress);
        TextView listTelephone = (TextView) v.findViewById(R.id.listTelephone);

        //todo : 이미지 뷰
        ImageView listImg = (ImageView) v.findViewById(R.id.mainImg);
        listImg.setImageResource(R.drawable.test_image);
//        setImageView(storeList.get(i).getImgSrc());

        listCompany.setText(storeList.get(i).getCompany());
        listBranch.setText(storeList.get(i).getBranch());
        listAddress.setText(storeList.get(i).getAddress());
        listTelephone.setText(storeList.get(i).getTelephone());

        return v;
    }



}
