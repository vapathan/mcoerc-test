package com.ass.mcoerctest.constants;

import java.util.ArrayList;
import java.util.List;

public class  Gender {
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String OTHER = "other";

    private static List<String> genderList;

    public static List<String> getGenderList() {
        if (genderList == null) {
           genderList=new ArrayList<>();
           genderList.add(MALE);
           genderList.add(FEMALE);
           genderList.add(OTHER);

        }
        return genderList;
    }


}
