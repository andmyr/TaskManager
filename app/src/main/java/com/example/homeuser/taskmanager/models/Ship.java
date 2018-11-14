package com.example.homeuser.taskmanager.models;

public class Ship extends Mechanizm {
    private final String name;

    public Ship(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
