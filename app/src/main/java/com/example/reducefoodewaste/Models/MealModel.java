package com.example.reducefoodewaste.Models;

import java.io.Serializable;

public class MealModel implements Serializable {

  private String mealid;
  private String docid;
  private String photo;
  private String location;
  private int view;
  private String mealName;
  private String mealDeatails;
  private int besideLike;
  private String userName;
  private String added;
  private String  distance;
  private String userImage;
  private int personNumber;
  private long addedAt;
  private  String foodtype;
  private String isRequested ;
  private  int duration ;

  public MealModel(String mealid, String docid, String photo, String location,
                   int view, String mealName, String Mealdetails, int besideLike,
                   String userName, String added, String distance, int personNumber,String userImage,String foodtype,int dur) {

    this.mealid = mealid;
    this.docid = docid;
    this.photo = photo;
    this.location = location;
    this.view = view;
    this.mealName = mealName;
    this.besideLike = besideLike;
    this.userImage = userImage;
    this.userName = userName;
    this.added = added;
    this.distance = distance;
    this.personNumber = personNumber;
    this.mealDeatails = Mealdetails;
    duration=dur;
this.foodtype=foodtype;}
  //change in constructor
  public long getAddedAt() {
    return addedAt;
  }

  public void setAddedAt(long addedAt) {
    this.addedAt = addedAt;
  }
  public String getUserImage() {
    return userImage;
  }

  public void setUserImage(String userImage) {
    this.userImage = userImage;
  }

  public String getMealid() {
    return mealid;
  }

  public void setMealid(String mealid) {
    this.mealid = mealid;
  }

  public String getMealDeatails() {
    return mealDeatails;
  }

  public void setMealDeatails(String mealDeatails) {
    this.mealDeatails = mealDeatails;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getView() {
    return view;
  }

  public void setView(int view) {
    this.view = view;
  }


  public String getMealName() {
    return mealName;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }


  public String getdocid() {
    return docid;
  }

  public void setMealName(String mealName) {
    this.mealName = mealName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAdded() {
    return added;
  }

  public void setAdded(String added) {
    this.added = added;
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public int getPersonNumber() {
    return personNumber;
  }

  public void setPersonNumber(int personNumber) {
    this.personNumber = personNumber;
  }

  public String getFoodtype() {
    return foodtype;
  }

  public void setFoodtype(String foodtype) {
    this.foodtype = foodtype;
  }

  public int getBesideLike() {
    return besideLike;
  }

  public void setBesideLike(int besideLike) {
    this.besideLike = besideLike;
  }



  public void setDocid(String docid) {
    this.docid = docid;
  }

  public String getIsRequested() {
    return isRequested;
  }

  public void setIsRequested(String isRequested) {
    this.isRequested = isRequested;
  }

  @Override
  public String toString() {
    return "MealModel{" +
            "photo=" + photo +
            ", location=" + location +
            ", eye=" + view +
            ", mealName='" + mealName + '\'' +
            ", mealDeatails='" + mealDeatails + '\'' +
            ", besidehreat='" + besideLike + '\'' +
            ", userName='" + userName + '\'' +
            ", added='" + added + '\'' +
            ", distance='" + distance + '\'' +
            ", personNumber=" + personNumber +
            '}';
  }

  public MealModel() {
  }


}