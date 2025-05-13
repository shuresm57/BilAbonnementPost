package org.example.bilabonnement.model;

public class Skade {

    private int damage_id;
    private String description;
    private double price;
    private String img_url;
    private int report_id;

    public Skade() {}

    public Skade(int damage_id, String description, double price, String img_url, int report_id) {
        this.damage_id = damage_id;
        this.description = description;
        this.price = price;
        this.img_url = img_url;
        this.report_id = report_id;
    }

    public int getDamage_id() {
        return damage_id;
    }

    public void setDamage_id(int damage_id) {
        this.damage_id = damage_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }
}
