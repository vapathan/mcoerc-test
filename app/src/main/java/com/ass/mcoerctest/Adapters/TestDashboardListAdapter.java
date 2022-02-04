package com.ass.mcoerctest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ass.mcoerctest.R;
import com.ass.mcoerctest.TestListActivity;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.utilities.ImageHelper;

import java.util.List;

import static com.ass.mcoerctest.constants.Constants.SUBJECT_KEY;

public class TestDashboardListAdapter extends RecyclerView.Adapter<TestDashboardListAdapter.ViewHolder> {
    private Context context;
    private List<Subject> subjectList;

    public TestDashboardListAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Subject subject = subjectList.get(position);

        holder.title.setText(subject.getName());
        Bitmap bitmap = ImageHelper.getImageFromAssetsFile(context, subject.getCode() + ".png");
        holder.imageview.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TestListActivity.class);
                intent.putExtra(SUBJECT_KEY, subject);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
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
