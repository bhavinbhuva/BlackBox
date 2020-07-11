package com.example.blackbox;

public class scannerlistitem {


        private String date;
        private String docname;

    public scannerlistitem(String date, String docname) {
        this.date = date;
        this.docname = docname;
    }

    public String getDate() {
        return date;
    }

    public String getDocname() {
        return docname;
    }
}
