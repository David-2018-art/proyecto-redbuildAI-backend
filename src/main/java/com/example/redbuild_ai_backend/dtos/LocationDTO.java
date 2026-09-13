package com.example.redbuild_ai_backend.dtos;

import jakarta.persistence.Column;

public class LocationDTO {
    private Long idLocation;
    private String department;
    private String province;
    private String district;
    private String referenceAddress;
    private double latitude;
    private double longitude;

    public LocationDTO() {

    }

    public LocationDTO(Long idLocation, String department, String province, String district, String referenceAddress, double latitude, double longitude) {
        this.idLocation = idLocation;
        this.department = department;
        this.province = province;
        this.district = district;
        this.referenceAddress = referenceAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getReferenceAddress() {
        return referenceAddress;
    }

    public void setReferenceAddress(String referenceAddress) {
        this.referenceAddress = referenceAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
