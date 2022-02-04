package com.ass.mcoerctest.utilities;

import java.util.List;

public class ListHandler {

    public static String[] GetStringArray(List<String> arr)
    {
        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // Iterating and converting to String
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String)obj;
        }

        return str;
    }
}
