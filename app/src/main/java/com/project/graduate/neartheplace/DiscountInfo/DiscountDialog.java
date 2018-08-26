package com.project.graduate.neartheplace.DiscountInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.graduate.neartheplace.R;

public class DiscountDialog extends Dialog{

    private Button closeBtn;
    private View.OnClickListener closeListener;
    private DiscountInfo discountInfo;
    private TextView company;
    private TextView discountKT;
    private TextView discountSKT;
    private TextView discountLGU;
    private TextView discountArmy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_discount);

        closeBtn = (Button) findViewById(R.id.dialog_close);
        discountKT = (TextView) findViewById(R.id.dialog_dicountKT);
        discountSKT = (TextView) findViewById(R.id.dialog_dicountSKT);
        discountLGU = (TextView) findViewById(R.id.dialog_dicountLGU);
        discountArmy = (TextView) findViewById(R.id.dialog_dicountArmy);
        company = (TextView) findViewById(R.id.dialog_company);

        closeBtn.setOnClickListener(closeListener);
        discountKT.setText(discountInfo.getDiscountKT());
        discountSKT.setText(discountInfo.getDiscountSKT());
        discountLGU.setText(discountInfo.getDiscountLGU());
        company.setText(discountInfo.getCompany());

        if(discountInfo.getDiscountArmy().equals("null")){
            discountArmy.setText("혜택 없음");
        }else{
            discountArmy.setText(discountInfo.getDiscountArmy());
        }

    }

    public DiscountDialog(Context context, View.OnClickListener closeListener, DiscountInfo discountInfo) {
        super(context);
        this.closeListener = closeListener;
        this.discountInfo = discountInfo;
    }

}
