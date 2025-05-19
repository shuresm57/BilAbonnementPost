package org.example.bilabonnement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "damage")
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int damage_id;
    private String description;
    private double price;
    private String imageUrl;


    public Damage() {}

    public Damage(int damage_id, String description, double price) {
        this.damage_id = damage_id;
        this.description = description;
        this.price = price;
    }

    public Damage(int damage_id, String description, double price, String imageUrl) {
        this.damage_id = damage_id;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
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


}
