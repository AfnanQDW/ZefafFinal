package com.zefaf.zefaffinal.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Bookmark {

    private String uid;
    private String venuePic;
    private String venueName;
    private String venueAddress;
    private String venueRating;

    public Bookmark() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("venuePic", venuePic);
        result.put("venueName", venueName);
        result.put("venueAddress", venueAddress);
        result.put("venueRating", venueRating);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVenuePic() {
        return venuePic;
    }

    public void setVenuePic(String venuePic) {
        this.venuePic = venuePic;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueRating() {
        return venueRating;
    }

    public void setVenueRating(String venueRating) {
        this.venueRating = venueRating;
    }

}
