package com.project.graduate.neartheplace.Board;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.graduate.neartheplace.R;

public class BoardDialog extends Dialog {

    private Button craeteCloseBtn;
    private View.OnClickListener mCreateCloseListener;
    private BoardText mselectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_dialog);

        craeteCloseBtn = (Button) findViewById(R.id.createClose);
        craeteCloseBtn.setOnClickListener(mCreateCloseListener);
        TextView userId = (TextView)findViewById(R.id.createBoardId);
        TextView boardTime = (TextView)findViewById(R.id.createBoardTime);
        TextView boardTitle  = (TextView)findViewById(R.id.createBoardTitle);
        TextView boardContext  = (TextView)findViewById(R.id.createBoardContext);
        boardTime.setText(mselectItem.getBoardTime());
        boardTitle.setText(mselectItem.getBoardTitle());
        userId.setText(mselectItem.getUserID());
        boardContext.setText(mselectItem.getBoardContext());
    }

    public BoardDialog(Context context, View.OnClickListener CreateCloseListener, BoardText selectItem) {
        super(context);
        this.mCreateCloseListener = CreateCloseListener;
        this.mselectItem = selectItem;
    }
}
