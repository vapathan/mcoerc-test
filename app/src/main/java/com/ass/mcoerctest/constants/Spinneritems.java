package com.ass.mcoerctest.constants;

import java.util.ArrayList;
import java.util.List;

public class Spinneritems {
    public static final String Sicence = "सायन्स";
    public static final String Commerece = "कोममेरे";
    public static final String Arts = "कोममेरे";
    public static final String Medical = "मेडिकल";
    public static final String Engineering = "इंजीनीरिंग";
    public static final String OTHER = "एतर";

    private static List<String> streamList;

    public static List<String> getStreamList() {

        if (streamList == null) {
            streamList = new ArrayList<>();
            streamList.add(Sicence);
            streamList.add(Commerece);
            streamList.add(Arts);
            streamList.add(Medical);
            streamList.add(Engineering);
            streamList.add(OTHER);

        }
        return streamList;
    }
}