package com.maradroid.shp;

/**
 * Created by mara on 3/15/15.
 */
public class Stoljece {

    private String cardStoljece, cardZanimljivosti, stoljeceTag;
    private int cardImage;

    public Stoljece(String stoljece, String stoljeceTag, String zanimljivosti, int image){
        this.cardStoljece = stoljece;
        this.cardZanimljivosti = zanimljivosti;
        this.cardImage = image;
        this.stoljeceTag = stoljeceTag;
    }

    public String getCardStoljece(){return this.cardStoljece;}
    public String getCardZanimljivosti(){return this.cardZanimljivosti;}
    public int getCardImage(){return this.cardImage;}
    public String getStoljeceTag(){return this.stoljeceTag;}
}
