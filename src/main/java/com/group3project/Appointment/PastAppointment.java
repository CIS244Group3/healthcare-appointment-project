package com.group3project.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class PastAppointment {
    private String doctor;
    private LocalDate date;
    private LocalTime time;
    private String specialty;

    public PastAppointment(LocalDate date, LocalTime time, String firstname, String lastname, String specialty) {
        this.doctor = firstname + " " + lastname;
        this.date = date;
        this.time = time;
        this.specialty = specialty;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

}