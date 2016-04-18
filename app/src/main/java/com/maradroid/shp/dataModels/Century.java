package com.maradroid.shp.dataModels;

/**
 * Created by mara on 3/15/15.
 */
public class Century {

    private String century;
    private String interesting;
    private String centuryTag;
    private int cardImage;

    public Century(String century, String centuryTag, String interesting, int image){
        this.century = century;
        this.interesting = interesting;
        this.cardImage = image;
        this.centuryTag = centuryTag;
    }

    public String getCardCentury(){return this.century;}

    public String getInteresting(){return this.interesting;}

    public int getCardImage(){return this.cardImage;}

    public String getCenturyTag(){return this.centuryTag;}
}
