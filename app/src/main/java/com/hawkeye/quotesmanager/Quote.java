package com.hawkeye.quotesmanager;

/**
 * Created by CRM on 7/31/2017.
 */

public class Quote {
    String author = "";
    int day = 0;
    String type = "";
    String value = "";
    public Quote(String author,int day,String type, String value) {
        this.author = author;
        this.day = day;
        this.type = type;
        this.value = value;
    }

}
