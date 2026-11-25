package com.example.redbuild_ai_backend.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class LocationDTO {
    private Long idLocation;
    @NotBlank(message = "El departamento es obligatorio")
    private String department;

    @NotBlank(message = "La provincia es obligatoria.")
    private String province;

    @NotBlank(message = "El distrito es obligatorio.")
    private String district;

    private String referenceAddress;

    @DecimalMin(value = "-90.0",message = "La latitud no puede ser menor a -90")
    @DecimalMax(value = "90.0", message = "La latitud no puede ser mayor a 90")
    private Double latitude;

    @DecimalMin(value = "-180.0",message = "La longitud no puede ser menor a -180")
    @DecimalMax(value = "180.0", message = "La longitud no puede ser mayor a 180")
    private Double longitude;


    public LocationDTO() {

    }

    public LocationDTO(Long idLocation, String department, String province, String district, String referenceAddress, Double latitude, Double longitude) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
