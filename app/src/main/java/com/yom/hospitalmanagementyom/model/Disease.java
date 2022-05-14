package com.yom.hospitalmanagementyom.model;

import java.util.List;

public class Disease {
    String Name;
    String Id;
    List<String> drugs;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDrugs(List<String> drugs) {
        this.drugs = drugs;
    }

    public List<String> getDrugs() {
        return drugs;
    }
}
