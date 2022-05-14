package com.yom.hospitalmanagementyom.model;

import java.io.Serializable;

public class Drug implements Serializable {
    private String Id, nameDrug;
    public String getId() {
        return Id;
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

}
