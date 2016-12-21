package com.rackian.model.configuration;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate extends GregorianCalendar {
    
    public MyDate() {
        super();
    }
    
    public MyDate(long timeInMiliseconds) {
        this.setTimeInMillis(timeInMiliseconds);
    }
    
    public MyDate(String date) {
        String[] partDates = date.split(" ");
        String[] dates = partDates[0].split("/");
        String[] times = partDates[1].split(":");
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]) - 1;
        int day = Integer.parseInt(dates[0]);
        int hours = Integer.parseInt(times[0]);
        int minutes = Integer.parseInt(times[1]);
        this.set(year, month, day, hours, minutes);
    }
    
    @Override
    public String toString() {
        String date = "";
        date += get(Calendar.DATE) + "/" + (get(Calendar.MONTH) + 1) + "/" + get(Calendar.YEAR);
        date += " " + get(Calendar.HOUR_OF_DAY) + ":" + get(Calendar.MINUTE);
        return date;
    }
    
}
