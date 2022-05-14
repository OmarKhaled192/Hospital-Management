package com.yom.hospitalmanagementyom.listeners;

import com.yom.hospitalmanagementyom.model.Diesess;
import com.yom.hospitalmanagementyom.model.Disease;
import com.yom.hospitalmanagementyom.model.Drug;

import java.util.List;

public interface SearchListener {
    void finishGetDrugs(List<Drug> drugs);
}
