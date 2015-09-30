package com.maradroid.shp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mara on 9/29/15.
 */
public class ApiSingleton {
    private static ApiSingleton ourInstance = null;
    private String JSONString;
    private Context context;
    public ArrayList<Spomenik> spomenikArray;
    public Map<String, ArrayList<Spomenik>> stoljeceMap;
    public ArrayList<Spomenik> jedanaestoStoljeceArray;
    public ArrayList<Spomenik> dvanaestoStoljeceArray;
    public ArrayList<Spomenik> trinaestoStoljeceArray;
    public ArrayList<Spomenik> cetrnaestoStoljeceArray;
    public ArrayList<Spomenik> petnaestoStoljeceArray;
    public ArrayList<Spomenik> sesnaestoStoljeceArray;
    public ArrayList<Spomenik> sedamnaestoStoljeceArray;
    public ArrayList<Spomenik> osamnaestoStoljeceArray;
    public ArrayList<Spomenik> devetnaestoStoljeceArray;
    public ArrayList<Spomenik> dvadesetoStoljeceArray;

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

        stoljeceMap = new HashMap<String, ArrayList<Spomenik>>();
        stoljeceMap.put("11", jedanaestoStoljeceArray);
        stoljeceMap.put("12", dvanaestoStoljeceArray);
        stoljeceMap.put("13", trinaestoStoljeceArray);
        stoljeceMap.put("14", cetrnaestoStoljeceArray);
        stoljeceMap.put("15", petnaestoStoljeceArray);
        stoljeceMap.put("16", sesnaestoStoljeceArray);
        stoljeceMap.put("17", sedamnaestoStoljeceArray);
        stoljeceMap.put("18", osamnaestoStoljeceArray);
        stoljeceMap.put("19", devetnaestoStoljeceArray);
        stoljeceMap.put("20", dvadesetoStoljeceArray);

        LoadJSONFromAssests();
        ParseJSON();

    }

    public void LoadJSONFromAssests(){
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

    public void ParseJSON(){

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

                stoljeceMap.get(tempSpomenik.stoljece).add(tempSpomenik);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
