package com.example.hairdo.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Offer {
    public String offerId;
    public String title;
    public String description;
    public String validUntil;

    public Offer() {
    }

    public Offer(String offerId, String title, String description, String validUntil) {
        this.offerId = offerId;
        this.title = title;
        this.description = description;
        this.validUntil = validUntil;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("offer_id", offerId);
        result.put("title", title);
        result.put("desc", description);
        result.put("valid_until", validUntil);

        return result;
    }

}
