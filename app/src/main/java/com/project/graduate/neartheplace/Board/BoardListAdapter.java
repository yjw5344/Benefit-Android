package com.project.graduate.neartheplace.Board;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.graduate.neartheplace.R;

import java.util.List;

public class BoardListAdapter extends BaseAdapter{

    private Context context;
    private List<BoardText> textList;

    public BoardListAdapter(Context context, List<BoardText> textList) {
        this.context = context;
        this.textList = textList;
    }

    @Override
    public int getCount() {
        return textList.size();
    }

    @Override
    public Object getItem(int i) {
        return textList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.listview_board, null);
        TextView boardTitle = (TextView) v.findViewById(R.id.boardTitle);
        TextView boardContext = (TextView) v.findViewById(R.id.boardContext);
        TextView boardId = (TextView) v.findViewById(R.id.boardId);
        TextView boardTime = (TextView) v.findViewById(R.id.boardTime);

        boardTitle.setText(textList.get(i).getBoardTitle());
        boardContext.setText(textList.get(i).getBoardContext());
        boardId.setText(textList.get(i).getUserID());
        boardTime.setText(textList.get(i).getBoardTime());

        v.setTag(textList.get(i).getUserID());
        return v;
    }
}
