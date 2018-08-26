package com.project.graduate.neartheplace.DiscountInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.project.graduate.neartheplace.R;

public class DiscountBankDialog extends Dialog {

    private Button mcloseBtn;
    private View.OnClickListener mcloseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_discount_bank);

        mcloseBtn = (Button) findViewById(R.id.dialog_bank_close);
        mcloseBtn.setOnClickListener(mcloseListener);
    }

    public DiscountBankDialog(@NonNull Context context, View.OnClickListener mcloseListener) {
        super(context);
        this.mcloseListener = mcloseListener;
    }
}
