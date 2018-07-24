package com.project.graduate.neartheplace.Board;

public class BoardText {

    String boardTitle;
    String boardContext;
    String boardTime;
    String userID;

    public BoardText(String boardTitle, String boardContext, String boardTime, String userID) {
        this.boardTitle = boardTitle;
        this.boardContext = boardContext;
        this.boardTime = boardTime;
        this.userID = userID;
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
}
