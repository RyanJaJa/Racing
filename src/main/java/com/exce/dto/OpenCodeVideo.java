package com.exce.dto;

import java.util.Calendar;

public class OpenCodeVideo {

    private Calendar now = Calendar.getInstance();
    public OpenCodeVideoAward current = new OpenCodeVideoAward();
    public OpenCodeVideoAward next = new OpenCodeVideoAward();

    public long getTime() {
        return now.getTimeInMillis() / 1000;
    }
}
