package com.group3project.Appointment;

import java.util.HashMap;
import java.util.Collection;

public class Day {
    private HashMap<Specialty, Boolean> availability = new HashMap<Specialty, Boolean>();

    public Day() {
        this.availability.put(new Specialty("Dermatologist"), true);
        this.availability.put(new Specialty("Gynecologist"), true);
        this.availability.put(new Specialty("General Practitioner"), true);
        this.availability.put(new Specialty("ENT"), false);
        this.availability.put(new Specialty("Pulmonologist"), true);
    }

    public void setOneUnavailable(Specialty specialty) {
        this.availability.replace(specialty, false);
    }

    public void setAllUnavailable() {
        this.availability.replaceAll((k, v) -> v = false);
    }

    public Collection<Boolean> showValue() {
        return this.availability.values();
    }

    public static void main(String[] args) {
        Day test = new Day();

        System.out.println(test.showValue());
        test.setAllUnavailable();
        System.out.println(test.showValue());
    }
}

class Specialty {
    private String name;

    public Specialty(String name) {
        this.name = name;
    }

    public void displayName() {
        System.out.println(this.name);
    }
}