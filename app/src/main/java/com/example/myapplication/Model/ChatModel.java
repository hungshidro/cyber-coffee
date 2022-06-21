package com.example.myapplication.Model;

public class ChatModel {
    String id,text,senderID;
    Long dateMillis;

    public ChatModel(String id, String text, String senderID, Long dateMillis) {
        this.id = id;
        this.text = text;
        this.senderID = senderID;
        this.dateMillis = dateMillis;
    }

    public ChatModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public Long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(Long dateMillis) {
        this.dateMillis = dateMillis;
    }
}
