package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Himanshu Srivastava on 08,August,2023
 * Project e_savari
 */
public class ProductImage {
    @JsonProperty("imageId")
    private String imageId;

    public ProductImage() {

    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
