package com.project.graduate.neartheplace.Board;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.project.graduate.neartheplace.R;


public class BoardSearchDialog  extends Dialog {

    private Button                  CloseBtn;
    private ImageButton             searchTextBtn;
    private View.OnClickListener    mCreateCloseListener;
    private View.OnClickListener    msearchTextBtnListener;
    private String                  targetText;
    private EditText                inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_board_search);

        CloseBtn        = (Button) findViewById(R.id.dialog_search_cancleBtn);
        searchTextBtn   = (ImageButton) findViewById(R.id.dialog_search_searchBtn);
        inputText       = (EditText) findViewById(R.id.dialog_search_text);

        CloseBtn.setOnClickListener(mCreateCloseListener);
        searchTextBtn.setOnClickListener(msearchTextBtnListener);
    }

    public BoardSearchDialog(Context context, View.OnClickListener CreateCloseListener, View.OnClickListener searchTextBtnListener) {
        super(context);
        this.mCreateCloseListener   = CreateCloseListener;
        this.msearchTextBtnListener = searchTextBtnListener;
    }

    public String getTargetText() {
        return inputText.getText().toString();
    }
}
