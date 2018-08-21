package com.project.graduate.neartheplace.MainFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.graduate.neartheplace.R;

public class MainDialog extends Dialog {

    private Button closeBtn;
    private View.OnClickListener closeListener;
    private Store storeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main);

        closeBtn = (Button) findViewById(R.id.dialog_main_cancle);

        //todo : 이미지뷰
        TextView vCompany = (TextView)findViewById(R.id.dialog_main_company);
        TextView vBranch = (TextView)findViewById(R.id.dialog_main_branch);
        TextView vCategory = (TextView)findViewById(R.id.dialog_main_category);
        TextView vAddress = (TextView)findViewById(R.id.dialog_main_address);

        closeBtn.setOnClickListener(closeListener);
        vCompany.setText(storeDate.getCompany());
        vBranch.setText(storeDate.getBranch());
        vCategory.setText(storeDate.getCategory());
        vAddress.setText(storeDate.getAddress());

    }

    public MainDialog(@NonNull Context context, Button closeBtn, View.OnClickListener closeListener, Store storeDate) {
        super(context);
        this.closeBtn = closeBtn;
        this.closeListener = closeListener;
        this.storeDate = storeDate;
    }
}
