package com.maradroid.glagopedija.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mara on 3/15/15.
 */
public class Century implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.century);
        dest.writeString(this.image);
        dest.writeString(this.representatives);
        dest.writeTypedList(this.monumentList);
    }

    protected Century(Parcel in) {
        this.century = in.readString();
        this.image = in.readString();
        this.representatives = in.readString();
        this.monumentList = in.createTypedArrayList(Monument.CREATOR);
    }

    public static final Parcelable.Creator<Century> CREATOR = new Parcelable.Creator<Century>() {
        @Override
        public Century createFromParcel(Parcel source) {
            return new Century(source);
        }

        @Override
        public Century[] newArray(int size) {
            return new Century[size];
        }
    };
}
