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

/**
 * Created by mara on 9/29/15.
 */
public class ApiSingleton {
    private static ApiSingleton ourInstance = null;
    private String JSONString;
    private Context context;
    public ArrayList<Spomenik> spomenikArray;

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

                spomenikArray.add(new Spomenik(jArray.getJSONObject(i).getString("id"),
                        jArray.getJSONObject(i).getString("ime"),
                        jArray.getJSONObject(i).getString("godina"),
                        jArray.getJSONObject(i).getString("pismo"),
                        jArray.getJSONObject(i).getString("jezik"),
                        jArray.getJSONObject(i).getString("sadrzaj"),
                        jArray.getJSONObject(i).getString("velicina"),
                        jArray.getJSONObject(i).getString("zanimljivosti")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
