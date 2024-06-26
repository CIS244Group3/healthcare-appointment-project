package com.group3project.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class PastAppointment {
    private String firstName;
    private String lastName;
    private LocalDate date;
    private LocalTime time;
    private String specialty;
    private String notes;

    private int id;

    public PastAppointment(int id, LocalDate date, LocalTime time, String firstname, String lastname,
            String specialty) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.date = date;
        this.time = time;
        this.specialty = specialty;
        this.id = id;
    }

    public String getDoctor() {
        return "Dr. " + this.firstName + " " + this.lastName;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getFirst() {
        return this.firstName;
    }

    public String getLast() {
        return this.lastName;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public int getId() {
        return this.id;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}