package org.example.bilabonnement.model;

public class Car {

    private int car_id;
    private String reg_no;
    private String vin;
    private String location;
    private String rental_status;
    private String img_url;
    private int model_id;

    public Car(){}

    public Car(int car_id, String reg_no, String vin, String location, String rental_status, String img_url, int model_id) {
        this.car_id = car_id;
        this.reg_no = reg_no;
        this.vin = vin;
        this.location = location;
        this.rental_status = rental_status;
        this.img_url = img_url;
        this.model_id = model_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
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

    public String getRental_status() {
        return rental_status;
    }

    public void setRental_status(String rental_status) {
        this.rental_status = rental_status;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }
}
