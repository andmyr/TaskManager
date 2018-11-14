package com.example.homeuser.taskmanager.models;

public class Car extends Mechanizm {
    private final String name;

    public Car(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
