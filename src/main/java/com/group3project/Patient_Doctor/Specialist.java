package com.group3project.Patient_Doctor;

import java.util.ArrayList;

public class Specialist {
    private final String name;
    private final ArrayList<String> illnessesTreated;

    public Specialist(String name, ArrayList<String> illnessesTreated) {
        this.name = name;
        this.illnessesTreated = illnessesTreated;
    }

    public void displayInfo() {
        System.out.println("specialty: " + name);
        System.out.println("illnesses Treated: ");
        for (String illness : illnessesTreated) {
            System.out.println("-" + illness);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> dermatologyIllnesses = new ArrayList<>();
        dermatologyIllnesses.add("Eczema");
        dermatologyIllnesses.add("Psoriasis");

        Specialist dermatology = new Specialist("Dermatology", dermatologyIllnesses);

        dermatology.displayInfo();

    }
}