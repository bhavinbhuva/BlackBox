package com.example.blackbox;

public class Userclass {

    String  name,mobileno,panno,adharno,email,pass,userid;

    public Userclass() {
    }

    public Userclass(String name, String mobileno, String panno, String adharno, String email, String pass,String  userid) {
        this.name = name;
        this.mobileno = mobileno;
        this.panno = panno;
        this.adharno = adharno;
        this.email = email;
        this.pass = pass;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getAdharno() {
        return adharno;
    }

    public void setAdharno(String adharno) {
        this.adharno = adharno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
