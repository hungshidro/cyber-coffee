package com.example.myapplication.Model;

import java.io.Serializable;

public class SeatModel implements Serializable {
    private String seat,id;
    private String typeCom, typeRoom, status;
    boolean enable;

    public SeatModel() {
        enable = true;
    }

    public String getSeat() {
        return seat;
    }

    public SeatModel(String seat, String typeCom, String typeRoom, String status, boolean enable) {
        this.seat = seat;
        this.typeCom = typeCom;
        this.typeRoom = typeRoom;
        this.status = status;
        this.enable = enable;
    }

    public SeatModel(String seat, String id, String typeCom, String typeRoom, String status, boolean enable) {
        this.seat = seat;
        this.id = id;
        this.typeCom = typeCom;
        this.typeRoom = typeRoom;
        this.status = status;
        this.enable = enable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTypeCom() {
        return typeCom;
    }

    public void setTypeCom(String typeCom) {
        this.typeCom = typeCom;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
