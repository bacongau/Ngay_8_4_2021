package com.example.ngay_8_4_2021.model;

public class Clothes {
    private String id;
    private String name;
    private int price;
    private String category;
    private String logoUrl;
    private int numberSave;

    public Clothes(String id, String name, int price, String category, String logoUrl, int numberSave) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.logoUrl = logoUrl;
        this.numberSave = numberSave;
    }

    public Clothes() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getNumberSave() {
        return numberSave;
    }

    public void setNumberSave(int numberSave) {
        this.numberSave = numberSave;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", numberSave=" + numberSave +
                '}';
    }
}
