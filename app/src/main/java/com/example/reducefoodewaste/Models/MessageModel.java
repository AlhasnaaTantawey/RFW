package com.example.reducefoodewaste.Models;

public class MessageModel {
    private String date;
    private int imgProfile;
    private String nameperson1;
    private String mess1;
    private int imgMess1;
    private String timestamp1;
    private int imgProfile2;
    private String nameperson2;
    private String mess2;
    private int imgMess2;
    private String timestamp2;

    public MessageModel() {
    }

    public MessageModel(String date, int imgProfile, String nameperson1, String mess1, int imgMess1, String timestamp1, int imgProfile2, String nameperson2, String mess2, int imgMess2, String timestamp2) {
        this.date = date;
        this.imgProfile = imgProfile;
        this.nameperson1 = nameperson1;
        this.mess1 = mess1;
        this.imgMess1 = imgMess1;
        this.timestamp1 = timestamp1;
        this.imgProfile2 = imgProfile2;
        this.nameperson2 = nameperson2;
        this.mess2 = mess2;
        this.imgMess2 = imgMess2;
        this.timestamp2 = timestamp2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(int imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getNameperson1() {
        return nameperson1;
    }

    public void setNameperson1(String nameperson1) {
        this.nameperson1 = nameperson1;
    }

    public String getMess1() {
        return mess1;
    }

    public void setMess1(String mess1) {
        this.mess1 = mess1;
    }

    public int getImgMess1() {
        return imgMess1;
    }

    public void setImgMess1(int imgMess1) {
        this.imgMess1 = imgMess1;
    }

    public String getTimestamp1() {
        return timestamp1;
    }

    public void setTimestamp1(String timestamp1) {
        this.timestamp1 = timestamp1;
    }

    public int getImgProfile2() {
        return imgProfile2;
    }

    public void setImgProfile2(int imgProfile2) {
        this.imgProfile2 = imgProfile2;
    }

    public String getNameperson2() {
        return nameperson2;
    }

    public void setNameperson2(String nameperson2) {
        this.nameperson2 = nameperson2;
    }

    public String getMess2() {
        return mess2;
    }

    public void setMess2(String mess2) {
        this.mess2 = mess2;
    }

    public int getImgMess2() {
        return imgMess2;
    }

    public void setImgMess2(int imgMess2) {
        this.imgMess2 = imgMess2;
    }

    public String getTimestamp2() {
        return timestamp2;
    }

    public void setTimestamp2(String timestamp2) {
        this.timestamp2 = timestamp2;
    }
}
