package com.project.graduate.neartheplace.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.graduate.neartheplace.Board.BoardCreate;
import com.project.graduate.neartheplace.Board.BoardDialog;
import com.project.graduate.neartheplace.Board.BoardListAdapter;
import com.project.graduate.neartheplace.Board.BoardSearchDialog;
import com.project.graduate.neartheplace.Board.BoardText;
import com.project.graduate.neartheplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BoardFragment extends Fragment {


    private String              userToken;
    private ListView            listView;
    private BoardListAdapter    adapter;
    private BoardDialog         dialog;
    private BoardSearchDialog   searchDialog;
    private ImageButton         createBtn;
    private ImageButton         refreshBtn;
    private ImageButton         searchBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        createBtn    = (ImageButton) view.findViewById(R.id.createBoardBtn);
        refreshBtn   = (ImageButton) view.findViewById(R.id.boardRefresh);
        searchBtn    = (ImageButton) view.findViewById(R.id.boardSearch);
        listView     = (ListView) view.findViewById(R.id.board);
        userToken    = getArguments().getString("userToken");

        refreshText();

        // 글 보기(다이얼로그)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BoardText selectItem = (BoardText) adapterView.getItemAtPosition(i);
                Dialog(selectItem);
            }
        });

        // 글 작성하기 버튼 클릭
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connectMakeActicity = new Intent(getActivity(), BoardCreate.class);
                connectMakeActicity.putExtra("userToken",userToken);
                startActivityForResult(connectMakeActicity,0);
            }
        });


        // 글 새로고침 클릭
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshText();
            }
        });

        // 글 검색 클릭
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDialog();
            }
        });

        //todo : 글 수정 ( 나중에 시간나면 구현 !!! )

        return view;
    }

    public void Dialog(BoardText selectItem) {
        dialog = new BoardDialog(getActivity(), CreateCloseListener, deleteTextBtnListener, selectItem);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    private View.OnClickListener CreateCloseListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    private View.OnClickListener deleteTextBtnListener = new View.OnClickListener() {
        public void onClick(View v) {

            DeleteBoardText deleteBoardText = new DeleteBoardText(dialog.getMselectItem().getData_id().toString());
            deleteBoardText.execute();

            refreshText();

            dialog.dismiss();
        }
    };

    public void SearchDialog(){
        searchDialog = new BoardSearchDialog(getActivity(), searchCloseBtnListener, searchSearchBtnListener);
        searchDialog.setCancelable(true);
        searchDialog.getWindow().setGravity(Gravity.CENTER);
        searchDialog.show();

    }

    private View.OnClickListener searchCloseBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            searchDialog.dismiss();
        }
    };

    private View.OnClickListener searchSearchBtnListener = new View.OnClickListener() {
        public void onClick(View v) {

            //todo : JSON 수정
            Toast.makeText(getActivity(),"작동",Toast.LENGTH_SHORT).show();

//            SearchBoardText searchBoardText = new SearchBoardText(searchDialog.getTargetText());
//            searchBoardText.execute();

            searchDialog.dismiss();
        }
    };

    public class GetBoardText extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/board")
                    .header("X-Access-Token",userToken)
                    .build();

            Response responseClient = null;
            JSONObject getJson = null;
            try {
                responseClient = client.newCall(request).execute();
                getJson = new JSONObject(responseClient.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getJson;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            List<BoardText> boardList = new ArrayList<BoardText>();
            try {
                JSONArray textList = new JSONArray(jsonObject.getString("data"));
                for(int i = 0; i < textList.length(); i++){
                    JSONObject textData = textList.getJSONObject(i);
                    boardList.add(new BoardText(textData.getString("title"),textData.getString("content"),textData.getString("writetime"), textData.getString("author"),textData.getString("_id").toString(), textData.getInt("flag")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new BoardListAdapter(getActivity(), boardList);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onCancelled() {
            Log.d("BoardFragment_GetText","BoardFragment Connect Fail");
//            Toast.makeText(getActivity(), "통신실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            refreshText();
        }
    }

    public void refreshText(){
        GetBoardText refreshText = new GetBoardText();
        refreshText.execute();
    }

    public class DeleteBoardText extends AsyncTask<Void, Void, JSONObject> {

        private String m_id;

        public DeleteBoardText(String m_id) {
            this.m_id = m_id;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/board/" + m_id)
                    .header("X-Access-Token",userToken)
                    .delete()
                    .build();

            Response responseClient = null;
            JSONObject resultJson = null;
            try {
                responseClient = client.newCall(request).execute();
                resultJson = new JSONObject(responseClient.body().toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            // todo : 응답 결과 확인.. ( 중요한부분 X, 결과 JSON 주의할 것! )
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity(), "삭제실패", Toast.LENGTH_SHORT).show();
        }
    }

    public class SearchBoardText extends AsyncTask<Void, Void, JSONObject> {

        String target;

        public SearchBoardText(String target) {
            this.target = target;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("text", target)
                    .build();

            Request request = new Request.Builder()
                    .url("http://13.125.61.58:9090/board/search")
                    .header("X-Access-Token",userToken)
                    .post(formBody)
                    .build();

            Response responseClient = null;
            JSONObject getJson = null;
            try {
                responseClient = client.newCall(request).execute();
                getJson = new JSONObject(responseClient.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getJson;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            List<BoardText> boardList = new ArrayList<BoardText>();
            try {
                JSONArray textList = new JSONArray(jsonObject.getString("data"));
                for(int i = 0; i < textList.length(); i++){
                    JSONObject textData = textList.getJSONObject(i);
                    boardList.add(new BoardText(textData.getString("title"),textData.getString("content"),textData.getString("writetime"), textData.getString("author"),textData.getString("_id").toString(), textData.getInt("flag")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new BoardListAdapter(getActivity(), boardList);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onCancelled() {
            Log.d("BoardFragment_GetText","BoardFragment Connect Fail");
//            Toast.makeText(getActivity(), "통신실패", Toast.LENGTH_SHORT).show();
        }
    }

}
