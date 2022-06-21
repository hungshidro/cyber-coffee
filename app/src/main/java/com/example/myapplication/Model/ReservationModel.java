package com.example.myapplication.Model;

import java.io.Serializable;

public class ReservationModel implements Serializable {
    private String seat,id;
    private String typeCom, typeRoom;
    private Long timeMillis;
    private int fee;

    public ReservationModel() {
    }

    public ReservationModel(String seat, String typeCom, String typeRoom, Long timeMillis, int fee) {
        this.seat = seat;
        this.typeCom = typeCom;
        this.typeRoom = typeRoom;
        this.timeMillis = timeMillis;
        this.fee = fee;
    }

    public ReservationModel(SeatModel seat,Long timeMillis,int fee){
        this.seat = seat.getSeat();
        this.typeCom = seat.getTypeCom();
        this.typeRoom = seat.getTypeRoom();
        this.timeMillis = timeMillis;
        this.fee = fee;
    }

    public ReservationModel(String seat, String id, String typeCom, String typeRoom, Long timeMillis, int fee) {
        this.seat = seat;
        this.id = id;
        this.typeCom = typeCom;
        this.typeRoom = typeRoom;
        this.timeMillis = timeMillis;
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
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

    public Long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
