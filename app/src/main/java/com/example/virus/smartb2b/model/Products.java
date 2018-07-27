package com.example.virus.smartb2b.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Products implements Serializable{

    @Exclude private String id;
    private String brand;
    private String product_name;
    private  String category;
    private int price;
    private int quantity;
    public Products(){

    }

    public Products(String brand, String product_name, String category, int price, int quantity) {
        this.brand = brand;
        this.product_name = product_name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
