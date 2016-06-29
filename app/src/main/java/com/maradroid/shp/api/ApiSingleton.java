package com.maradroid.shp.api;

import android.content.Context;
import android.content.res.AssetManager;

import com.maradroid.shp.dataModels.MapPointer;
import com.maradroid.shp.dataModels.Monument;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mara on 9/29/15.
 */
public class ApiSingleton {

    private static ApiSingleton ourInstance = null;
    private String JSONString;
    private Context context;
    private boolean isDataReady;

    private ArrayList<Monument> monumentArray;
    private ArrayList<Monument> jedanaestoStoljeceArray;
    private ArrayList<Monument> dvanaestoStoljeceArray;
    private ArrayList<Monument> trinaestoStoljeceArray;
    private ArrayList<Monument> cetrnaestoStoljeceArray;
    private ArrayList<Monument> petnaestoStoljeceArray;
    private ArrayList<Monument> sesnaestoStoljeceArray;
    private ArrayList<Monument> sedamnaestoStoljeceArray;
    private ArrayList<Monument> osamnaestoStoljeceArray;
    private ArrayList<Monument> devetnaestoStoljeceArray;
    private ArrayList<Monument> dvadesetoStoljeceArray;

    private ArrayList<MapPointer> pointersArray;

    private MonumentEvent monumentEvent;

    public static ApiSingleton getInstance() {

        if(ourInstance == null) {
            ourInstance = new ApiSingleton();
        }
        return ourInstance;
    }

    private ApiSingleton() {
    }

    public void getJSON(Context c){

        this.context = c;

        jedanaestoStoljeceArray = new ArrayList<Monument>();
        dvanaestoStoljeceArray = new ArrayList<Monument>();
        trinaestoStoljeceArray = new ArrayList<Monument>();
        cetrnaestoStoljeceArray = new ArrayList<Monument>();
        petnaestoStoljeceArray = new ArrayList<Monument>();
        sesnaestoStoljeceArray = new ArrayList<Monument>();
        sedamnaestoStoljeceArray = new ArrayList<Monument>();
        osamnaestoStoljeceArray = new ArrayList<Monument>();
        devetnaestoStoljeceArray = new ArrayList<Monument>();
        dvadesetoStoljeceArray = new ArrayList<Monument>();

        pointersArray = new ArrayList<>();

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

        monumentArray = new ArrayList<Monument>();

        JSONArray jArray = null;

        try {
            jArray = new JSONArray(JSONString);

            for(int i = 0; i < jArray.length(); i++){

                Monument tempMonument = new Monument(jArray.getJSONObject(i).getString("id"),
                        jArray.getJSONObject(i).getString("ime"),
                        jArray.getJSONObject(i).getString("godina"),
                        jArray.getJSONObject(i).getString("pismo"),
                        jArray.getJSONObject(i).getString("jezik"),
                        jArray.getJSONObject(i).getString("sadrzaj"),
                        jArray.getJSONObject(i).getString("velicina"),
                        jArray.getJSONObject(i).getString("zanimljivosti"),
                        jArray.getJSONObject(i).getString("stoljece"),
                        jArray.getJSONObject(i).getString("mjesto"),
                        jArray.getJSONObject(i).getDouble("latitude"),
                        jArray.getJSONObject(i).getDouble("longitude"));

                monumentArray.add(tempMonument);

                saveObjectToSpecificArray(tempMonument);

                if (jArray.getJSONObject(i).getDouble("latitude") != -1 && jArray.getJSONObject(i).getDouble("longitude") != -1) {

                    MapPointer tempPointer = new MapPointer(jArray.getJSONObject(i).getString("id"),
                            jArray.getJSONObject(i).getString("ime"),
                            jArray.getJSONObject(i).getDouble("latitude"),
                            jArray.getJSONObject(i).getDouble("longitude"));

                    pointersArray.add(tempPointer);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sortArrays();
    }

    private void saveObjectToSpecificArray(Monument monument) {

        switch (monument.getCentury()) {

            case "11":
                jedanaestoStoljeceArray.add(monument);
                break;

            case "12":
                dvanaestoStoljeceArray.add(monument);
                break;

            case "13":
                trinaestoStoljeceArray.add(monument);
                break;

            case "14":
                cetrnaestoStoljeceArray.add(monument);
                break;

            case "15":
                petnaestoStoljeceArray.add(monument);
                break;

            case "16":
                sesnaestoStoljeceArray.add(monument);
                break;

            case "17":
                sedamnaestoStoljeceArray.add(monument);
                break;

            case "18":
                osamnaestoStoljeceArray.add(monument);
                break;

            case "19":
                devetnaestoStoljeceArray.add(monument);
                break;

            case "20":
                dvadesetoStoljeceArray.add(monument);
                break;
        }
    }

    private void sortArrays() {

        Collections.sort(jedanaestoStoljeceArray);
        Collections.sort(dvanaestoStoljeceArray);
        Collections.sort(trinaestoStoljeceArray);
        Collections.sort(cetrnaestoStoljeceArray);
        Collections.sort(petnaestoStoljeceArray);
        Collections.sort(sesnaestoStoljeceArray);
        Collections.sort(sedamnaestoStoljeceArray);
        Collections.sort(osamnaestoStoljeceArray);
        Collections.sort(devetnaestoStoljeceArray);
        Collections.sort(dvadesetoStoljeceArray);
    }

    public ArrayList<Monument> getArrayListByCentury(String century) {

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

    public ArrayList<Monument> getMonumentArray() {
        return monumentArray;
    }

    public ArrayList<MapPointer> getPointersArray(){return pointersArray;}

    public boolean isDataReady() {
        return isDataReady;
    }

    public void setMonumentEvent(MonumentEvent event) {
        this.monumentEvent = event;
    }

    public void removeMonumentEvent() {
        this.monumentEvent = null;
    }

    public MonumentEvent getMonumentEvent() {
        return this.monumentEvent;
    }
}
