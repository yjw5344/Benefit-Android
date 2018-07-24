package com.project.graduate.neartheplace.SignUp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.project.graduate.neartheplace.R;

public class SignUp_CustomDialog extends Dialog{

    private Button regist_button;
    private Button naver_button;
    private Button cancle_button;

    private View.OnClickListener mRegistListener;
    private View.OnClickListener mNaverListener;
    private View.OnClickListener mCancleListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdialog_login);

        regist_button = (Button) findViewById(R.id.loginCustomDialog_self);
        naver_button = (Button) findViewById(R.id.loginCustomDialog_naver);
        cancle_button = (Button) findViewById(R.id.loginCustomDialog_cancle);

        regist_button.setOnClickListener(mRegistListener);
        naver_button.setOnClickListener(mNaverListener);
        cancle_button.setOnClickListener(mCancleListener);
    }

    public SignUp_CustomDialog(Context context, View.OnClickListener registListener, View.OnClickListener naverListener, View.OnClickListener cancleListener) {
        super(context);
        this.mRegistListener = registListener;
        this.mNaverListener = naverListener;
        this.mCancleListener = cancleListener;
    }

}

// LoginActivity Dialog UI