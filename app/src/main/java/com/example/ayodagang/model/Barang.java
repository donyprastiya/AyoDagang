package com.example.ayodagang.model;

public class Barang {

    private String brand;
    private String categori;
    private String img;
    private String name;
    private String qty;
    private String sub_catagory;
    private String sub_category2;

    public Barang(String brand, String img, String name, String qty, String sub_catagory) {
        this.brand = brand;
        this.img = img;
        this.name = name;
        this.qty = qty;
        this.sub_catagory = sub_catagory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategori() {
        return categori;
    }

    public void setCategori(String categori) {
        this.categori = categori;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSub_catagory() {
        return sub_catagory;
    }

    public void setSub_catagory(String sub_catagory) {
        this.sub_catagory = sub_catagory;
    }

    public String getSub_category2() {
        return sub_category2;
    }

    public void setSub_category2(String sub_category2) {
        this.sub_category2 = sub_category2;
    }
}
