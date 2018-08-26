package com.project.graduate.neartheplace.Board;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.graduate.neartheplace.R;


public class BoardDialog extends Dialog {

    private Button                  craeteCloseBtn;
    private Button                  deleteTextBtn;
    private View.OnClickListener    mCreateCloseListener;
    private View.OnClickListener    mdeleteTextBtnListener;
    private BoardText               mselectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_board);

        craeteCloseBtn          = (Button) findViewById(R.id.createClose);
        deleteTextBtn           = (Button) findViewById(R.id.deleteText);
        TextView userId         = (TextView)findViewById(R.id.createBoardId);
        TextView boardTime      = (TextView)findViewById(R.id.createBoardTime);
        TextView boardTitle     = (TextView)findViewById(R.id.createBoardTitle);
        TextView boardContext   = (TextView)findViewById(R.id.createBoardContext);


        if(mselectItem.getFlag() == 0){
            deleteTextBtn.setVisibility(View.INVISIBLE);
        }

        craeteCloseBtn.setOnClickListener(mCreateCloseListener);
        deleteTextBtn.setOnClickListener(mdeleteTextBtnListener);
        boardTime.setText(mselectItem.getBoardTime());
        boardTitle.setText(mselectItem.getBoardTitle());
        userId.setText(mselectItem.getUserID());
        boardContext.setText(mselectItem.getBoardContext());
    }

    public BoardDialog(Context context, View.OnClickListener CreateCloseListener,View.OnClickListener deleteTextBtnListener, BoardText selectItem) {
        super(context);
        this.mCreateCloseListener   = CreateCloseListener;
        this.mselectItem            = selectItem;
        this.mdeleteTextBtnListener = deleteTextBtnListener;
    }

    public BoardText getMselectItem() {
        return mselectItem;
    }

    public void setMselectItem(BoardText mselectItem) {
        this.mselectItem = mselectItem;
    }
}
