package com.ass.mcoerctest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ass.mcoerctest.R;
import com.ass.mcoerctest.models.Test;

import java.util.List;

import static com.ass.mcoerctest.constants.Constants.SUBJECT_KEY;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.ViewHolder> {
    private Context context;
    private List<Test> testList;

    public DashboardListAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    public void setTests(List<Test> tests) {
        testList = tests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Test test = testList.get(position);

        holder.title.setText(test.getTitle());
        //Bitmap bitmap = ImageHelper.getImageFromAssetsFile(context, test.getCode() + ".png");
        //holder.imageview.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv);
            imageview = itemView.findViewById(R.id.iv);
        }
    }
}
