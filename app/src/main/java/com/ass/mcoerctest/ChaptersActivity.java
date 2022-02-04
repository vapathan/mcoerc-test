package com.ass.mcoerctest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.ass.mcoerctest.Adapters.ChapterListAdapter;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.repositories.ChapterRepository;
import com.ass.mcoerctest.utilities.AppExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ass.mcoerctest.constants.Constants.SUBJECT_KEY;

public class ChaptersActivity extends AppCompatActivity {

    private RecyclerView rvChapters;
    private ProgressBar mProgressBar;


    private ChapterListAdapter mChapterListAdapter;
    private ChapterRepository mChapterRepository;
    private Subject mSubject;
    private List<Chapter> mChapterList;
    private Map<Integer, Integer> chapterQuestionCount;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        mChapterRepository = ChapterRepository.getInstance(this);
        mDb = AppDatabase.getInstance(this);
        chapterQuestionCount = new HashMap<>();
        Intent intent = getIntent();

        if (intent != null) {
            mSubject = intent.getParcelableExtra(SUBJECT_KEY);
            if (mSubject != null) {
                getSupportActionBar().setTitle(mSubject.getName());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            initGui();

            initRecyclerViews();

            loadChapters();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chapter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.download:
                if(rvChapters!=null){

                    for (int i = 0; i < mChapterListAdapter.getItemCount(); ++i) {
                        ChapterListAdapter.ViewHolder holder = (ChapterListAdapter.ViewHolder) rvChapters.findViewHolderForAdapterPosition(i);

                        if(holder!=null){
                            holder.getIvDownload().callOnClick();
                        }else{
                            mChapterListAdapter.downloadQuestions(mChapterList.get(i),null, null);
                        }
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initGui() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    private void initRecyclerViews() {
        rvChapters = (RecyclerView) findViewById(R.id.rv_tests);
        rvChapters.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChapters.setLayoutManager(linearLayoutManager);
    }


    private void loadChapters() {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {

                mChapterList = mChapterRepository.getChapters(mSubject.getCode());
                if (mChapterList != null) {
                    for (Chapter chapter : mChapterList) {
                        chapter.setQuestionCount(mDb.questionDao().getChapterQuestionCount(chapter.getSubjectCode(), chapter.getId()));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChapterListAdapter = new ChapterListAdapter(getApplicationContext(), mChapterList);
                            rvChapters.setAdapter(mChapterListAdapter);
                        }
                    });

                }

            }
        });
    }


}