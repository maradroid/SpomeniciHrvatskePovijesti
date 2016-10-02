package com.maradroid.shp.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maradroid.shp.dataModels.Century;
import com.maradroid.shp.dataModels.Monument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by mara on 10/1/16.
 */

public class JsonParser {

    private InputStream inputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    private ArrayList<Century> centuryList;

    public JsonParser() {
    }

    public void parse(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                readAndParse(context);
                close();

                ((ParserListener) context).onParseDone(centuryList);
            }
        }).run();

    }

    private void readAndParse(Context context) {

        byteArrayOutputStream = new ByteArrayOutputStream();
        int i;

        try {
            inputStream = context.getAssets().open("podatci.json");

            if (inputStream != null) {
                i = inputStream.read();

                while (i != -1)
                {
                    byteArrayOutputStream.write(i);
                    i = inputStream.read();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        String JSONString = byteArrayOutputStream.toString();

        Type type = new TypeToken<ArrayList<Century>>() {}.getType();
        centuryList = new Gson().fromJson(JSONString, type);
    }

    private void close() {

        if (inputStream != null && byteArrayOutputStream != null) {

            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
