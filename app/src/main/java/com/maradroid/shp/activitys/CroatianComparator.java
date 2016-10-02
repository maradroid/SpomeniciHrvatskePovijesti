package com.maradroid.shp.activitys;

import com.maradroid.shp.dataModels.Monument;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by mara on 10/1/16.
 */

public class CroatianComparator implements Comparator<Monument> {

    private static final Collator collator = Collator.getInstance(new Locale("hr_HR"));

    @Override
    public int compare(Monument lhs, Monument rhs) {
        return collator.compare(lhs.getName(), rhs.getName());
    }
}
