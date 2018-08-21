package com.project.graduate.neartheplace.MainFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.graduate.neartheplace.R;

public class DistanceDialog extends Dialog {

    private Button selectBtn;
    private Button cancleBtn;
    private View.OnClickListener selectBtnListener;
    private View.OnClickListener cancleBtnListener;
    private RadioGroup selectRadioGroup;
    private RadioButton selectRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_distance);

        selectBtn = (Button)findViewById(R.id.dialog_distance_select);
        cancleBtn = (Button)findViewById(R.id.dialog_distance_cancle);
        selectBtn.setOnClickListener(selectBtnListener);
        cancleBtn.setOnClickListener(cancleBtnListener);
        selectRadioGroup = (RadioGroup)findViewById(R.id.dialog_distance_radioGroup);
        selectRadioButton = (RadioButton)findViewById(selectRadioGroup.getCheckedRadioButtonId());
    }

    public DistanceDialog(Context context, View.OnClickListener selectBtnListener, View.OnClickListener cancleBtnListener) {
        super(context);
        this.selectBtnListener = selectBtnListener;
        this.cancleBtnListener = cancleBtnListener;
    }
}
