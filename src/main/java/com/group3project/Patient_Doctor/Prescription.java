package com.group3project.Patient_Doctor;

public class Prescription {
    private String patientName;
    private String doctorName;
    private String medication;
    private int quantity;
    private String directions;
    // Additional prescription details could be put here

    public Prescription(String patientName, String doctorName, String medication, int quantity, String directions) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.medication = medication;
        this.quantity = quantity;
        this.directions = directions;
    }

    // Getter and setter methods
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    // Method to display prescription details
    public void displayDetails() {
        System.out.println("Patient Name: " + patientName);
        System.out.println("Doctor Name: " + doctorName);
        System.out.println("Medication: " + medication);
        System.out.println("Quantity: " + quantity);
        System.out.println("Directions: " + directions);

    }
}