package org.example.bilabonnement.model;

public class Car {

    private int carId;
    private String regNo;
    private String vin;
    private String location;
    private String rentalStatus;
    private String imgUrl;
    private int modelId;

    public Car(){}

    public Car(int carId, String regNo, String vin, String location, String rentalStatus, String imgUrl, int modelId) {
        this.carId = carId;
        this.regNo = regNo;
        this.vin = vin;
        this.location = location;
        this.rentalStatus = rentalStatus;
        this.imgUrl = imgUrl;
        this.modelId = modelId;
    }

    public int getCar_id() {
        return carId;
    }

    public void setCar_id(int car_id) {
        this.carId = car_id;
    }

    public String getReg_no() {
        return regNo;
    }

    public void setReg_no(String reg_no) {
        this.regNo = reg_no;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModel_id(int modelId) {
        this.modelId = modelId;
    }
}
