package com.dkglabs.home.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Himanshu Srivastava on 12,June,2023
 * Project e_savari
 */
public class OfferCard {
    private String offerTitle;
    private String offerDescription;
    private Drawable image;

    public OfferCard() {
    }

    public OfferCard(String offerTitle, String offerDescription, Drawable image) {
        this.offerTitle = offerTitle;
        this.offerDescription = offerDescription;
        this.image = image;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
