package com.example.myapplication.Model;

public class ChatListModel {
    private String uid, lastMess, username;
    private Long lastDate;

    public ChatListModel(String uid, String lastMess, String username, Long lastDate) {
        this.uid = uid;
        this.lastMess = lastMess;
        this.username = username;
        this.lastDate = lastDate;
    }

    public ChatListModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastMess() {
        return lastMess;
    }

    public void setLastMess(String lastMess) {
        this.lastMess = lastMess;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLastDate() {
        return lastDate;
    }

    public void setLastDate(Long lastDate) {
        this.lastDate = lastDate;
    }
}
