package com.maradroid.shp.api;

import com.maradroid.shp.dataModels.Century;
import java.util.ArrayList;

/**
 * Created by mara on 10/1/16.
 */

public interface ParserListener {
    void onParseDone(ArrayList<Century> centuryList);
}
