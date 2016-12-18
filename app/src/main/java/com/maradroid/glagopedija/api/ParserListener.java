package com.maradroid.glagopedija.api;

import com.maradroid.glagopedija.dataModels.Century;
import java.util.ArrayList;

/**
 * Created by mara on 10/1/16.
 */

public interface ParserListener {
    void onParseDone(ArrayList<Century> glagoljicaArray, ArrayList<Century> cirilicaArray);
}
