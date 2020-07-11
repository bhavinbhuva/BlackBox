package com.example.blackbox;

public class mettinglistitem {

    private String date;
    private String time;
    private String query;
    private String reply;


    public mettinglistitem(String date, String time, String query, String reply) {
        this.date = date;
        this.time = time;
        this.query = query;
        this.reply = reply;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getQuery() {
        return query;
    }

    public String getReply() {
        return reply;
    }
}
