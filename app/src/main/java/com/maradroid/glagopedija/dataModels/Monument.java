package com.maradroid.glagopedija.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mara on 9/29/15.
 */
public class Monument implements Parcelable {

    @Expose
    private String id;
    @Expose
    @SerializedName("ime")
    private String name;
    @Expose
    @SerializedName("godina")
    private String year;
    @Expose
    @SerializedName("pismo")
    private String type;
    @Expose
    @SerializedName("jezik")
    private String language;
    @Expose
    @SerializedName("sadrzaj")
    private String content;
    @Expose
    @SerializedName("velicina")
    private String size;
    @Expose
    @SerializedName("zanimljivosti")
    private String interesting;
    @Expose
    @SerializedName("stoljece")
    private String century;
    @Expose
    @SerializedName("mjesto")
    private String place;

    public Monument(String id, String name, String year, String type, String language, String content, String size, String interesting, String century, String place){

        this.id = id;
        this.name = name;
        this.year = year;
        this.type = type;
        this.language = language;
        this.content = content;
        this.size = size;
        this.interesting = interesting;
        this.century = century;
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }

    public String getContent() {
        return content;
    }

    public String getSize() {
        return size;
    }

    public String getInteresting() {
        return interesting;
    }

    public String getCentury() {
        return century;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.year);
        dest.writeString(this.type);
        dest.writeString(this.language);
        dest.writeString(this.content);
        dest.writeString(this.size);
        dest.writeString(this.interesting);
        dest.writeString(this.century);
        dest.writeString(this.place);
    }

    protected Monument(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.year = in.readString();
        this.type = in.readString();
        this.language = in.readString();
        this.content = in.readString();
        this.size = in.readString();
        this.interesting = in.readString();
        this.century = in.readString();
        this.place = in.readString();
    }

    public static final Parcelable.Creator<Monument> CREATOR = new Parcelable.Creator<Monument>() {
        @Override
        public Monument createFromParcel(Parcel source) {
            return new Monument(source);
        }

        @Override
        public Monument[] newArray(int size) {
            return new Monument[size];
        }
    };
}
