package com.zefaf.zefaffinal.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Reservation {

    private String uid;
    private String venueName;
    private String userName;
    private String venueAddress;
    private String venuePrice;
    private String reservationDate;
    private int reservationStatus;

    public Reservation() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("venueName", venueName);
        result.put("venueAddress", venueAddress);
        result.put("venuePrice", venuePrice);
        result.put("reservationDate", reservationDate);
        result.put("reservationStatus", reservationStatus);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenuePrice() {
        return venuePrice;
    }

    public void setVenuePrice(String venuePrice) {
        this.venuePrice = venuePrice;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(int reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
