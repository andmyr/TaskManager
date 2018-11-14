package com.example.homeuser.taskmanager.models;

import java.util.List;

public class MechanizmsItemsList {

    private String name;

    private List<Mechanizm> mechanizmList = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mechanizm> getMechanizmList() {
        return mechanizmList;
    }

    public void setMechanizmList(List<Mechanizm> mechanizmList) {
        this.mechanizmList = mechanizmList;
    }
}
