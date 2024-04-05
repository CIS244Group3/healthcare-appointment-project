package com.group3project.Patient_Doctor;

import java.util.ArrayList;

public class Specialty {
        private String name;
        private ArrayList<String> illnessesTreated;

        public Specialty(String name, ArrayList<String> illnessesTreated) {
                this.name = name;
                this.illnessesTreated = illnessesTreated;
        }

        // This displays the info when used
        public void displayInfo() {
                System.out.println("Speciality: " + name);
                System.out.println("Illnesses Treated: ");
                // test_loop:
                for (String illness : illnessesTreated) {
                        System.out.println("-" + illness);
                }
        }

        public void type() {
                System.out.println("Specialist type");
        }

        public static void main(String[] args) {

                // this is where the list of illness will be grouped with the specialist

                ArrayList<String> dermatologyIllnesses = new ArrayList<>();
                dermatologyIllnesses.add("Eczema");
                dermatologyIllnesses.add("Psoriasis");

                Specialty dermatology = new Specialty("Dermatology", dermatologyIllnesses);
                dermatology.displayInfo();

        }
}