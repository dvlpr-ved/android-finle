package com.dkglabs.model.response;

/**
 * Created by Himanshu Srivastava on 21,June,2023
 * Project e_savari
 */
public class PartnerResponse {

    private String partnerName;
    private String partnerAddress;
    private String partnerImage;
    private String partnerPhone;
    private Double lat;
    private Double lng;

    public PartnerResponse(String partnerName, String partnerAddress, String partnerImage, String partnerPhone, Double lat, Double lng) {
        this.partnerName = partnerName;
        this.partnerAddress = partnerAddress;
        this.partnerImage = partnerImage;
        this.partnerPhone = partnerPhone;
        this.lat = lat;
        this.lng = lng;
    }

    public PartnerResponse() {
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }

    public String getPartnerImage() {
        return partnerImage;
    }

    public void setPartnerImage(String partnerImage) {
        this.partnerImage = partnerImage;
    }

    public String getPartnerPhone() {
        return partnerPhone;
    }

    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
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
