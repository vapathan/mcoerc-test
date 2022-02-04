package com.ass.mcoerctest.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ass.mcoerctest.QuestionsActivity;
import com.ass.mcoerctest.R;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.repositories.QuestionRepository;

import java.util.HashMap;
import java.util.List;

import static com.ass.mcoerctest.constants.Constants.CHAPTER_KEY;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ViewHolder> {
    private Context context;
    private List<Chapter> mChapterList;
    private QuestionRepository mQuestionRepository;
    private AppDatabase mDb;
    private int questionCount;
    public HashMap<Integer, RecyclerView.ViewHolder> holderHashMap = new HashMap<>();
    public ChapterListAdapter(Context context, List<Chapter> mChapterList) {
        this.context = context;
        this.mChapterList = mChapterList;
        mQuestionRepository = QuestionRepository.getInstance(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_chapter, parent, false);
        return new ChapterListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Chapter chapter = mChapterList.get(position);

        holderHashMap.put(position,holder);

        holder.tvChapterName.setText(chapter.getSrNum() + ". " + chapter.getName());
        holder.tvQuestionCount.setText(String.valueOf(chapter.getQuestionCount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chapter.getQuestionCount() > 0) {
                    Intent intent = new Intent(view.getContext(), QuestionsActivity.class);
                    intent.putExtra(CHAPTER_KEY, chapter);
                    view.getContext().startActivity(intent);
                } else {
                    confirmDownloadDialog(view, chapter, holder.ivDownload, holder.progressBar);
                }

            }
        });

        holder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadQuestions(chapter, holder.ivDownload, holder.progressBar);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mChapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvChapterName, tvQuestionCount;
        ImageView ivDownload;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChapterName = (TextView) itemView.findViewById(R.id.tv_test_title);
            tvQuestionCount = (TextView) itemView.findViewById(R.id.tv_question_count);
            ivDownload = (ImageView) itemView.findViewById(R.id.iv_download);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        }

        public ImageView getIvDownload(){
            return ivDownload;
        }

    }

    private void confirmDownloadDialog(View view, Chapter chapter, ImageView imageView, ProgressBar progressBar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.download_questions);
        builder.setMessage("Please click on Ok to download questions for this chapter");
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                downloadQuestions(chapter, imageView, progressBar);

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void downloadQuestions(Chapter chapter, ImageView imageView, ProgressBar progressBar) {
        mQuestionRepository.getQuestionList(chapter, imageView, progressBar, this);
    }



    public void updateChapterQuestionCount(Chapter chapter, int questionCount) {
        chapter.setQuestionCount(questionCount);
    }


}
