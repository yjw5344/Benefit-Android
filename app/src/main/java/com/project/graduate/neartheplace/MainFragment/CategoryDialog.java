package com.project.graduate.neartheplace.MainFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.graduate.neartheplace.R;

public class CategoryDialog extends Dialog {

    private Button selectBtn;
    private Button cancleBtn;
    private View.OnClickListener selectBtnListener;
    private View.OnClickListener cancleBtnListener;
    private RadioGroup selectRadioGroup;
    public RadioButton selectRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_category);

        selectBtn = (Button)findViewById(R.id.dialog_category_select);
        cancleBtn = (Button)findViewById(R.id.dialog_category_cancle);
        selectBtn.setOnClickListener(selectBtnListener);
        cancleBtn.setOnClickListener(cancleBtnListener);
        selectRadioGroup = (RadioGroup)findViewById(R.id.dialog_category_radioGroup);
        selectRadioButton = (RadioButton)findViewById(selectRadioGroup.getCheckedRadioButtonId());
    }

    public CategoryDialog(Context context, View.OnClickListener selectBtnListener, View.OnClickListener cancleBtnListener) {
        super(context);
        this.selectBtnListener = selectBtnListener;
        this.cancleBtnListener = cancleBtnListener;
    }

    public RadioButton getSelectRadioButton() {
        return selectRadioButton;
    }

    public void setSelectRadioButton(RadioButton selectRadioButton) {
        this.selectRadioButton = selectRadioButton;
    }
}
