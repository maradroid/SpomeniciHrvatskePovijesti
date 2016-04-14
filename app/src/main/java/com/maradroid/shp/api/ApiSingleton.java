package com.maradroid.shp.api;

import android.content.Context;
import android.content.res.AssetManager;

import com.maradroid.shp.dataModels.Spomenik;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by mara on 9/29/15.
 */
public class ApiSingleton {

    private static ApiSingleton ourInstance = null;
    private String JSONString;
    private Context context;
    private boolean isDataReady;

    private ArrayList<Spomenik> spomenikArray;
    private ArrayList<Spomenik> jedanaestoStoljeceArray;
    private ArrayList<Spomenik> dvanaestoStoljeceArray;
    private ArrayList<Spomenik> trinaestoStoljeceArray;
    private ArrayList<Spomenik> cetrnaestoStoljeceArray;
    private ArrayList<Spomenik> petnaestoStoljeceArray;
    private ArrayList<Spomenik> sesnaestoStoljeceArray;
    private ArrayList<Spomenik> sedamnaestoStoljeceArray;
    private ArrayList<Spomenik> osamnaestoStoljeceArray;
    private ArrayList<Spomenik> devetnaestoStoljeceArray;
    private ArrayList<Spomenik> dvadesetoStoljeceArray;

    private SpomenikEvent spomenikEvent;

    public static ApiSingleton getInstance() {

        if(ourInstance == null) {
            ourInstance = new ApiSingleton();
        }
        return ourInstance;
    }

    public static ApiSingleton getNewInstance() {

        ourInstance = new ApiSingleton();
        return ourInstance;
    }

    private ApiSingleton() {
    }

    public void getJSON(Context c){

        this.context = c;

        jedanaestoStoljeceArray = new ArrayList<Spomenik>();
        dvanaestoStoljeceArray = new ArrayList<Spomenik>();
        trinaestoStoljeceArray = new ArrayList<Spomenik>();
        cetrnaestoStoljeceArray = new ArrayList<Spomenik>();
        petnaestoStoljeceArray = new ArrayList<Spomenik>();
        sesnaestoStoljeceArray = new ArrayList<Spomenik>();
        sedamnaestoStoljeceArray = new ArrayList<Spomenik>();
        osamnaestoStoljeceArray = new ArrayList<Spomenik>();
        devetnaestoStoljeceArray = new ArrayList<Spomenik>();
        dvadesetoStoljeceArray = new ArrayList<Spomenik>();

        /*stoljeceMap = new HashMap<String, ArrayList<Spomenik>>();
        stoljeceMap.put("11", jedanaestoStoljeceArray);
        stoljeceMap.put("12", dvanaestoStoljeceArray);
        stoljeceMap.put("13", trinaestoStoljeceArray);
        stoljeceMap.put("14", cetrnaestoStoljeceArray);
        stoljeceMap.put("15", petnaestoStoljeceArray);
        stoljeceMap.put("16", sesnaestoStoljeceArray);
        stoljeceMap.put("17", sedamnaestoStoljeceArray);
        stoljeceMap.put("18", osamnaestoStoljeceArray);
        stoljeceMap.put("19", devetnaestoStoljeceArray);
        stoljeceMap.put("20", dvadesetoStoljeceArray);*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadJSONFromAssests();
                parseJSON();
                isDataReady = true;
            }
        }).run();
    }

    private void loadJSONFromAssests(){

        AssetManager am = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = am.open("podatci.json");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;

        try {
            i = inputStream.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        JSONString = byteArrayOutputStream.toString();

    }

    private void parseJSON(){

        spomenikArray = new ArrayList<Spomenik>();

        JSONArray jArray = null;

        try {
            jArray = new JSONArray(JSONString);

            for(int i = 0; i < jArray.length(); i++){

                Spomenik tempSpomenik = new Spomenik(jArray.getJSONObject(i).getString("id"),
                        jArray.getJSONObject(i).getString("ime"),
                        jArray.getJSONObject(i).getString("godina"),
                        jArray.getJSONObject(i).getString("pismo"),
                        jArray.getJSONObject(i).getString("jezik"),
                        jArray.getJSONObject(i).getString("sadrzaj"),
                        jArray.getJSONObject(i).getString("velicina"),
                        jArray.getJSONObject(i).getString("zanimljivosti"),
                        jArray.getJSONObject(i).getString("stoljece"),
                        jArray.getJSONObject(i).getString("mjesto"));

                spomenikArray.add(tempSpomenik);

                saveObjectToSpecificArray(tempSpomenik);

                //stoljeceMap.get(tempSpomenik.stoljece).add(tempSpomenik);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sortArrays();
    }

    private void saveObjectToSpecificArray(Spomenik spomenik) {

        switch (spomenik.getStoljece()) {

            case "11":
                jedanaestoStoljeceArray.add(spomenik);
                break;

            case "12":
                dvanaestoStoljeceArray.add(spomenik);
                break;

            case "13":
                trinaestoStoljeceArray.add(spomenik);
                break;

            case "14":
                cetrnaestoStoljeceArray.add(spomenik);
                break;

            case "15":
                petnaestoStoljeceArray.add(spomenik);
                break;

            case "16":
                sesnaestoStoljeceArray.add(spomenik);
                break;

            case "17":
                sedamnaestoStoljeceArray.add(spomenik);
                break;

            case "18":
                osamnaestoStoljeceArray.add(spomenik);
                break;

            case "19":
                devetnaestoStoljeceArray.add(spomenik);
                break;

            case "20":
                dvadesetoStoljeceArray.add(spomenik);
                break;
        }
    }

    private void sortArrays() {

        sort(jedanaestoStoljeceArray);
        sort(dvanaestoStoljeceArray);
        sort(trinaestoStoljeceArray);
        sort(cetrnaestoStoljeceArray);
        sort(petnaestoStoljeceArray);
        sort(sesnaestoStoljeceArray);
        sort(sedamnaestoStoljeceArray);
        sort(osamnaestoStoljeceArray);
        sort(devetnaestoStoljeceArray);
        sort(dvadesetoStoljeceArray);
    }

    private void sort(ArrayList<Spomenik> arrayList) {

        Collections.sort(arrayList, new Comparator<Spomenik>() {

            public int compare(Spomenik spomenik1, Spomenik spomenik2) {
                return spomenik1.getIme().compareTo(spomenik2.getIme());
            }
        });
    }

    public ArrayList<Spomenik> getArrayListByCentury(String century) {

        switch (century) {

            case "11":
                return jedanaestoStoljeceArray;

            case "12":
                return dvanaestoStoljeceArray;

            case "13":
                return trinaestoStoljeceArray;

            case "14":
                return cetrnaestoStoljeceArray;

            case "15":
                return petnaestoStoljeceArray;

            case "16":
                return sesnaestoStoljeceArray;

            case "17":
                return sedamnaestoStoljeceArray;

            case "18":
                return osamnaestoStoljeceArray;

            case "19":
                return devetnaestoStoljeceArray;

            case "20":
                return dvadesetoStoljeceArray;
        }

        return null;
    }

    public ArrayList<Spomenik> getDvadesetoStoljeceArray() {
        return dvadesetoStoljeceArray;
    }

    public ArrayList<Spomenik> getDevetnaestoStoljeceArray() {
        return devetnaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getOsamnaestoStoljeceArray() {
        return osamnaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getSedamnaestoStoljeceArray() {
        return sedamnaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getSesnaestoStoljeceArray() {
        return sesnaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getPetnaestoStoljeceArray() {
        return petnaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getCetrnaestoStoljeceArray() {
        return cetrnaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getTrinaestoStoljeceArray() {
        return trinaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getDvanaestoStoljeceArray() {
        return dvanaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getJedanaestoStoljeceArray() {
        return jedanaestoStoljeceArray;
    }

    public ArrayList<Spomenik> getSpomenikArray() {
        return spomenikArray;
    }

    public boolean isDataReady() {
        return isDataReady;
    }

    public void setSpomenikEvent(SpomenikEvent event) {
        this.spomenikEvent = event;
    }

    public void removeSpomenikEvent() {
        this.spomenikEvent = null;
    }

    public SpomenikEvent getSpomenikEvent() {
        return this.spomenikEvent;
    }
}
