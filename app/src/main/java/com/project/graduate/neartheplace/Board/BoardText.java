package com.project.graduate.neartheplace.Board;

public class BoardText {

    String boardTitle;
    String boardContext;
    String boardTime;
    String userID;
    String data_id;
    int    flag;



    public BoardText(String boardTitle, String boardContext, String boardTime, String userID, String data_id, int flag) {
        this.boardTitle = boardTitle;
        this.boardContext = boardContext;
        this.boardTime = boardTime;
        this.userID = userID;
        this.data_id = data_id;
        this.flag = flag;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContext() {
        return boardContext;
    }

    public void setBoardContext(String boardContext) {
        this.boardContext = boardContext;
    }

    public String getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(String boardTime) {
        this.boardTime = boardTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
