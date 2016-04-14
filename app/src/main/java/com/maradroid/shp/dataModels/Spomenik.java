package com.maradroid.shp.dataModels;

/**
 * Created by mara on 9/29/15.
 */
public class Spomenik {

    private String id, ime, godina, pismo, jezik, sadrzaj, velicina, zanimljivosti, stoljece, mjesto;

    public Spomenik(String id, String ime, String godina, String pismo, String jezik, String sadrzaj, String velicina, String zanimljivosti, String stoljece, String mjesto){

        this.id = id;
        this.ime = ime;
        this.godina = godina;
        this.pismo = pismo;
        this.jezik = jezik;
        this.sadrzaj = sadrzaj;
        this.velicina = velicina;
        this.zanimljivosti = zanimljivosti;
        this.stoljece = stoljece;
        this.mjesto = mjesto;
    }

    public String getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getGodina() {
        return godina;
    }

    public String getPismo() {
        return pismo;
    }

    public String getJezik() {
        return jezik;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public String getVelicina() {
        return velicina;
    }

    public String getZanimljivosti() {
        return zanimljivosti;
    }

    public String getStoljece() {
        return stoljece;
    }

    public String getMjesto() {
        return mjesto;
    }
}
