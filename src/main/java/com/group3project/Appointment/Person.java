package com.group3project.Appointment;

import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Person {
    private SimpleIntegerProperty appointments;
    private LocalTime time;
    // private SimpleIntegerProperty time;
    private LocalDate date;

    public Person(Integer appointments, LocalTime time, LocalDate date) {
        this.appointments = new SimpleIntegerProperty(appointments);
        this.time = time;
        this.date = date;
    }

    public Integer getAppointments() {

        return appointments.get();
    }

    public void setAppointments(SimpleIntegerProperty appointments) {
        this.appointments = appointments;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
