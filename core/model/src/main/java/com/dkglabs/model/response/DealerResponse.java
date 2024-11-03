package com.dkglabs.model.response;

/**
 * Created by Himanshu Srivastava on 21,June,2023
 * Project e_savari
 */
public class DealerResponse {

    private String dealerName;
    private String dealerAddress;
    private String dealerImage;
    private String dealerPhone;
    private Double lat;
    private Double lng;

    public DealerResponse(String dealerName, String dealerAddress, String dealerImage, String dealerPhone, Double lat, Double lng) {
        this.dealerName = dealerName;
        this.dealerAddress = dealerAddress;
        this.dealerImage = dealerImage;
        this.dealerPhone = dealerPhone;
        this.lat = lat;
        this.lng = lng;
    }

    public DealerResponse() {
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealerImage() {
        return dealerImage;
    }

    public void setDealerImage(String dealerImage) {
        this.dealerImage = dealerImage;
    }

    public String getDealerPhone() {
        return dealerPhone;
    }

    public void setDealerPhone(String dealerPhone) {
        this.dealerPhone = dealerPhone;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
