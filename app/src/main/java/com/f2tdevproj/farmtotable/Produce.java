package com.f2tdevproj.farmtotable;

public class Produce {
    public String itemId;
    public String farmname;
    public String name;
    public String description;
    public int stock;
    public String pricepercollection;
    public String category;
    public int itemQuantityChosen;
    public int minorder;
    public String collectiontype;

    public Produce(){}

    @Override
    public String toString() {
        return "Produce{" +
                "itemId='" + itemId + '\'' +
                ", farmname='" + farmname + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", pricepercollection='" + pricepercollection + '\'' +
                ", category='" + category + '\'' +
                ", itemQuantityChosen=" + itemQuantityChosen +
                ", minorder=" + minorder +
                ", collectiontype='" + collectiontype + '\'' +
                '}';
    }

    public Produce(String itemId, String farmname, String name, String description, int stock, String pricepercollection, String category, int itemQuantityChosen, int minorder, String collectiontype) {
        this.itemId = itemId;
        this.farmname = farmname;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.pricepercollection = pricepercollection;
        this.category = category;
        this.itemQuantityChosen = itemQuantityChosen;
        this.minorder = minorder;
        this.collectiontype = collectiontype;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public String getCollectiontype() {
        return collectiontype;
    }

    public void setCollectiontype(String collectiontype) {
        this.collectiontype = collectiontype;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPricepercollection() {
        return pricepercollection;
    }

    public void setPricepercollection(String pricepercollection) {
        this.pricepercollection = pricepercollection;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getItemQuantityChosen() {
        return itemQuantityChosen;
    }

    public void setItemQuantityChosen(int itemQuantityChosen) {
        this.itemQuantityChosen = itemQuantityChosen;
    }

    public int getMinorder() {
        return minorder;
    }

    public void setMinorder(int minorder) {
        this.minorder = minorder;
    }
}
