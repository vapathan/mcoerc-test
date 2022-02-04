package com.ass.mcoerctest.SampleData;


import com.ass.mcoerctest.models.DashboardListModel;
import com.ass.mcoerctest.R;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<DashboardListModel> getSampledata() {
        List<DashboardListModel> itemdata = new ArrayList<DashboardListModel>();

        itemdata.add(new DashboardListModel(R.drawable.ic_exams,"Maths"));
        itemdata.add(new DashboardListModel(R.drawable.ic_exams,"English"));
        itemdata.add(new DashboardListModel(R.drawable.ic_exams,"Hindi"));
        itemdata.add(new DashboardListModel(R.drawable.ic_exams,"Science"));
        itemdata.add(new DashboardListModel(R.drawable.ic_exams,"Social Studies"));
        itemdata.add(new DashboardListModel(R.drawable.ic_exams,"Sanskrit"));



        return itemdata;
    }
}
