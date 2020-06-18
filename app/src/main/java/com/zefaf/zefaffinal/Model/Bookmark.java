package com.zefaf.zefaffinal.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Bookmark {

    private int venuePic;
    private String venueName;
    private String venueAddress;
    private String venueRating;

    public Bookmark() {
    }

    public Bookmark(int venuePic, String venueName, String venueAddress, String venueRating) {
        this.venuePic = venuePic;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueRating = venueRating;
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

    public int getVenuePic() {
        return venuePic;
    }

    public void setVenuePic(int venuePic) {
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
