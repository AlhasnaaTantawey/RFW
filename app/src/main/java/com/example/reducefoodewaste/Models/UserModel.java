package com.example.reducefoodewaste.Models;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    String  name;
    String password,confirmpassword,gmail;
    String id;
    String userId;
    String imageUrl;
   int fav ;

    public UserModel(String id, String name, String password, String confirmpassword, String userId, String gmail, int fav,String user_image) {
        this.id=id;
        this.name = name;
        this.password = password;
        this.confirmpassword = confirmpassword;
       this.userId=userId;
        this.gmail = gmail;
        this.fav=fav;
        this.imageUrl = user_image;
    }

    public String getName() {
        return name;
    }

    public UserModel() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  static UserModel fromfirebaseuser(FirebaseUser firebaseUser){
        return  new UserModel(firebaseUser.getUid(),firebaseUser.getDisplayName(),"","",String.valueOf(System.currentTimeMillis()),firebaseUser.getEmail(),0,"");
    }
}
