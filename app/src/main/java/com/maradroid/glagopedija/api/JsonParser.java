package com.maradroid.glagopedija.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maradroid.glagopedija.dataModels.Century;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by mara on 10/1/16.
 */

public class JsonParser {

    private static final String CIRILICA = "cirilica.json";
    private static final String GLAGOLJICA = "glagoljica.json";

    private InputStream inputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    private ArrayList<Century> glagoljicaArray;
    private ArrayList<Century> cirilicaArray;

    public JsonParser() {
    }

    public void parse(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                readAndParse(context, GLAGOLJICA);
                readAndParse(context, CIRILICA);
                close();

                ((ParserListener) context).onParseDone(glagoljicaArray, cirilicaArray);
            }
        }).run();

    }

    private void readAndParse(Context context, String dataName) {

        byteArrayOutputStream = new ByteArrayOutputStream();
        int i;

        try {
            inputStream = context.getAssets().open(dataName);

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

        if (dataName.equals(GLAGOLJICA)) {
            glagoljicaArray = new Gson().fromJson(JSONString, type);

        } else {
            cirilicaArray = new Gson().fromJson(JSONString, type);
        }
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
