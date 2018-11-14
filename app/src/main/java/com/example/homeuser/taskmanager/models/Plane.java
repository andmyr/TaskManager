package com.example.homeuser.taskmanager.models;

public class Plane extends Mechanizm {
    private final String name;

    public Plane(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
