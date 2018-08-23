package com.project.graduate.neartheplace.MainFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.graduate.neartheplace.R;

public class MainDialog extends Dialog {

    private Button closeBtn;
    private View.OnClickListener closeListener;
    private Store storeDate;
    private float latitude;
    private float longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main);

        latitude = storeDate.getLatitude();
        longitude = storeDate.getLongitude();

        closeBtn = (Button) findViewById(R.id.dialog_main_cancle);

        TextView vCompany = (TextView)findViewById(R.id.dialog_main_company);
        TextView vBranch = (TextView)findViewById(R.id.dialog_main_branch);
        TextView vAddress = (TextView)findViewById(R.id.dialog_main_address);
        TextView vTelephone = (TextView)findViewById(R.id.dialog_main_telephone);

        ImageView vImg = (ImageView)findViewById(R.id.dialog_main_img);
        Glide.with(getContext())
                .load(storeDate.getImgSrc())
                .override(180,230)
                .into(vImg);

        closeBtn.setOnClickListener(closeListener);
        vCompany.setText(storeDate.getCompany());
        vBranch.setText(storeDate.getBranch());
        vAddress.setText(storeDate.getAddress());
        vTelephone.setText(storeDate.getTelephone());

    }

    public MainDialog(Context context, View.OnClickListener closeListener, Store storeDate) {
        super(context);
        this.closeListener = closeListener;
        this.storeDate = storeDate;
    }
}
