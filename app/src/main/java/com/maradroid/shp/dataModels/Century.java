package com.maradroid.shp.dataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mara on 3/15/15.
 */
public class Century {

    @Expose
    @SerializedName("stoljece")
    private String century;
    @Expose
    @SerializedName("slika")
    private String image;
    @Expose
    @SerializedName("predstavnici")
    private String representatives;
    @Expose
    @SerializedName("spomenici")
    private ArrayList<Monument> monumentList;

    /*public Century(String century, String centuryTag, String interesting, int image){
        this.century = century;
        this.interesting = interesting;
        this.cardImage = image;
        this.centuryTag = centuryTag;
    }*/

    public Century() {
    }

    public String getCentury() {
        return century;
    }

    public ArrayList<Monument> getMonumentList() {
        return monumentList;
    }

    public String getRepresentatives() {
        return representatives;
    }

    public String getImage() {
        return image;
    }
}
