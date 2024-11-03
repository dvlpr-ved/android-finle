package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 07,August,2023
 * Project e_savari
 */
public class ProductResponse {

    @JsonProperty("productId")
    private String productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("productDescription")
    private String productDescription;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("ratingCount")
    private String ratingCount;
    @JsonProperty("productPrice")
    private String productPrice;
    @JsonProperty("discount")
    private String discount;
    @JsonProperty("discountPrice")
    private String discountPrice;
    @JsonProperty("productImages")
    private List<ProductImage> productImages;

    public ProductResponse() {
    }

    public ProductResponse(String productId, String productName, String productDescription, String rating, String ratingCount, String productPrice, String discount, String discountPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.productPrice = productPrice;
        this.discount = discount;
        this.discountPrice = discountPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
