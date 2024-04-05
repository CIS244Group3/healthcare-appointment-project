package com.group3project.Appointment;

import java.util.HashMap;

// import java.util.Collection;
public class Month {
    private HashMap<Integer, Day> daysOfTheMonth;

    public Month() {
        for (int i = 1; i < 31; i++) {
            this.daysOfTheMonth.put(1, new Day());
        }
    }

    public void makeMonthAvailable(int index) {
        for (int i = 1; i < 31; i++) {
            this.daysOfTheMonth.get(1).setAllUnavailable();
        }
    }
}