package com.yom.hospitalmanagementyom.model;

import java.io.Serializable;

public class Drug implements Serializable {
    private String Id, nameDrug,Profile,price, details;
    public String getId() {
        return Id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNameDrug() {
        return nameDrug;
    }

    public void setNameDrug(String nameDrug) {
        this.nameDrug = nameDrug;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }


}
