package com.example.homeuser.taskmanager.factories;

import com.example.homeuser.taskmanager.enums.MechanizmType;
import com.example.homeuser.taskmanager.models.*;

public class MechanizmFactory {
    public static Mechanizm createMechanizm(MechanizmType mechanizmType, String name) throws Exception {
        switch (mechanizmType) {
            case CAR:
                return new Car(name);
            case PLANE:
                return new Plane(name);
            case SHIP:
                return new Ship(name);
            default:
                throw new Exception();
        }
    }
}