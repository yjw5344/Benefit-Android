package com.project.graduate.neartheplace.Board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.graduate.neartheplace.R;

public class BoardCreate extends AppCompatActivity {

    private Button cancleBtn;
    private Button completeBtn;
    private EditText textTitle;
    private EditText textContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_create);

        cancleBtn = (Button)findViewById(R.id.createCancle);
        completeBtn = (Button)findViewById(R.id.createComplete);
        textTitle = (EditText)findViewById(R.id.createTextTitle);
        textContext= (EditText)findViewById(R.id.createTextContext);

        cancleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //todo : 서버 전송 코드 구현 && 리스트뷰 갱신
                Toast.makeText(BoardCreate.this, "등록", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
