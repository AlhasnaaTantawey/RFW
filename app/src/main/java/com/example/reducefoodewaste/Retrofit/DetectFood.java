package com.example.reducefoodewaste.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetectFood {
    @SerializedName("food_types")
    @Expose
    private List<FoodTypes> food_types;

    public List<FoodTypes> getFood_types() {
        return food_types;
    }

    public void setFood_types(List<FoodTypes> FoodTypes) {
        this.food_types = FoodTypes;
    }

    @Override
    public String toString() {
        return "DetectFood{" +
                "food_types=" + food_types +
                '}';
    }
}
