package org.example.bilabonnement.model;

import java.util.Objects;

public class Car {

    private int carId;
    private String regNo;
    private String vin;
    private String location;
    private String rentalStatus;
    private String imgUrl;
    private int modelId;
    private int price;
    private String brand;
    private String model;
    private int odometer;
    private int downPayment;
    private int monthlyFee;

    public Car(){}

    public Car(int carId, String regNo, String vin, String location, String rentalStatus, String imgUrl, int modelId, int price, String brand, String model) {
        this.carId = carId;
        this.regNo = regNo;
        this.vin = vin;
        this.location = location;
        this.rentalStatus = rentalStatus;
        this.imgUrl = imgUrl;
        this.modelId = modelId;
        this.price = price;
        this.brand = brand;
        this.model = model;
    }

    public int getPrice(){
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
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

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public int getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && modelId == car.modelId && Objects.equals(regNo, car.regNo) && Objects.equals(vin, car.vin) && Objects.equals(location, car.location) && Objects.equals(rentalStatus, car.rentalStatus) && Objects.equals(imgUrl, car.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, regNo, vin, location, rentalStatus, imgUrl, modelId);
    }
}
