package com.group3project.Patient_Doctor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Doctor {
    private String name;
    private String specialty;
    private int id;
    private HashMap<LocalTime, Boolean> availabilityForDay;

    public Doctor(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.availabilityForDay = new HashMap<LocalTime, Boolean>();
        this.populateAvailability(this.availabilityForDay);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.specialty;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public int getId() {
        return this.id;
    }

    public HashMap<LocalTime, Boolean> getAvailability() {
        return this.availabilityForDay;
    }

    private void populateAvailability(HashMap<LocalTime, Boolean> map) {
        map.put(LocalTime.of(9, 00), true);
        map.put(LocalTime.of(9, 15), true);
        map.put(LocalTime.of(9, 30), true);
        map.put(LocalTime.of(9, 45), true);
        map.put(LocalTime.of(10, 00), true);
        map.put(LocalTime.of(10, 15), true);
        map.put(LocalTime.of(10, 30), true);
        map.put(LocalTime.of(10, 45), true);
        map.put(LocalTime.of(11, 00), true);
        map.put(LocalTime.of(11, 15), true);
        map.put(LocalTime.of(11, 30), true);
        map.put(LocalTime.of(11, 45), true);
        map.put(LocalTime.of(13, 30), true);
        map.put(LocalTime.of(13, 45), true);
        map.put(LocalTime.of(14, 00), true);
        map.put(LocalTime.of(14, 15), true);
        map.put(LocalTime.of(14, 30), true);
        map.put(LocalTime.of(14, 45), true);
        map.put(LocalTime.of(15, 00), true);
        map.put(LocalTime.of(15, 15), true);
        map.put(LocalTime.of(15, 30), true);
        map.put(LocalTime.of(15, 45), true);

    }

    public void makeTimeUnavailable(LocalTime time) {
        this.availabilityForDay.replace(time, false);
        System.out.println("in here mayne");

        for (Map.Entry<LocalTime, Boolean> entry : this.availabilityForDay.entrySet()) {
            LocalTime key = entry.getKey();
            // if (key == time) {
            // reachedCurrentTime = true;
            // continue;
            // }

            if (key.minusMinutes(15).compareTo(time) == 0 || key.minusMinutes(30).compareTo(time) == 0
                    || key.minusMinutes(45).compareTo(time) == 0 || key.plusMinutes(15).compareTo(time) == 0
                    || key.plusMinutes(30).compareTo(time) == 0 || key.plusMinutes(45).compareTo(time) == 0) {
                this.availabilityForDay.replace(key, false);
                System.out.println("something was made false!");

            }

        }

    }

}
