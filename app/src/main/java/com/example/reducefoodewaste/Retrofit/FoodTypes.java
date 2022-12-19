package com.example.reducefoodewaste.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodTypes {

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("probs")
    @Expose
    private Double probs;

    public Double getProbs() {
        return probs;
    }

    public void setProbs(Double probs) {
        this.probs = probs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FoodTypes{" +
                "name='" + name + '\'' +
                '}';
    }
}
