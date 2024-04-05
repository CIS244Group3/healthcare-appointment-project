package com.group3project.Patient_Doctor;

public class Pharmacy {
    private String name;
    private String address;
    // Other pharmacy-specific attributes could be put here

    public Pharmacy(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Method to display pharmacy details
    public void displayDetails() {
        System.out.println("Pharmacy Name: " + name);
        System.out.println("Address: " + address);
    }
}
