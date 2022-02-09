package com.ass.mcoerctest.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ass.mcoerctest.R;
import com.ass.mcoerctest.TestActivity;
import com.ass.mcoerctest.models.Test;

import java.util.List;

import static com.ass.mcoerctest.constants.Constants.TEST_KEY;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {
    private Context context;
    private List<Test> mTestList;

    public TestListAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.mTestList = testList;
    }

    public void setTests(List<Test> testList) {
        mTestList = testList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_test_list, parent, false);
        return new TestListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Test test = mTestList.get(position);

        holder.tvTestTitle.setText(test.getCourseName() + " - " + test.getCourseCode());
        holder.tvDateTime.setText(test.getDate());
        holder.tvDuration.setText(test.getTimeDuration());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirm");
                builder.setMessage(Html.fromHtml("Choose Yes to start this test. <br/> <hr> <br/>  <font color='#FF0000' >*Tap on option to choose the correct answer.</font>"));
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, TestActivity.class);
                        intent.putExtra(TEST_KEY, test);
                        view.getContext().startActivity(intent);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestTitle, tvDuration, tvDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTestTitle = (TextView) itemView.findViewById(R.id.tv_test_title);
            tvDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
        }
    }


}
