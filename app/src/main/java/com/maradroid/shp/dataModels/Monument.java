package com.maradroid.shp.dataModels;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Locale;

/**
 * Created by mara on 9/29/15.
 */
public class Monument implements Comparable<Monument>{

    private static final Collator collator = Collator.getInstance(new Locale("hr_HR"));

    private String id;
    private String name;
    private String year;
    private String type;
    private String language;
    private String content;
    private String size;
    private String interesting;
    private String century;
    private String place;
    private double latitude;
    private double longitude;

    private CollationKey key;

    public Monument(String id, String name, String year, String type, String language, String content, String size, String interesting, String century, String place, double latitude, double longitude){

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
        this.latitude = latitude;
        this.longitude = longitude;

        this.key = collator.getCollationKey(name);
    }

    @Override
    public int compareTo(Monument monument) {
        return key.compareTo(monument.key);
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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
