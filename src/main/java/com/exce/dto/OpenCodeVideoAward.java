package com.exce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;

public class OpenCodeVideoAward {

    public Integer qihao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Calendar awardTime;
    public String awardNumbers;
    public Integer awardTimeInterval;
    public final Integer delayTimeInterval = -30;
}
