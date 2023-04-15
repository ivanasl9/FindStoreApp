package com.example.shopslistapp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Hours {
    String Sunday;
    String Monday;
    String Tuesday;
    String Wednesday;
    String Thursday;
    String Friday;
    String Saturday;


    public String getTodaysHours() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        switch (day) {
            case "Monday":
                return this.Monday;
            case "Tuesday":
                return this.Tuesday;
            case "Wednesday":
                return this.Wednesday;
            case "Thursday":
                return this.Thursday;
            case "Friday":
                return this.Friday;
            case "Saturday":
                return this.Saturday;
            default:
                return this.Sunday;

        }
    }
}
