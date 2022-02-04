package com.ass.mcoerctest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.ass.mcoerctest.Adapters.QuestionViewPagerAdapter;
import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.repositories.QuestionRepository;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import static com.ass.mcoerctest.constants.Constants.CHAPTER_KEY;
import static com.ass.mcoerctest.constants.Constants.QUESTION_KEY;
import static com.ass.mcoerctest.constants.Constants.QUESTION_NUM_KEY;
import static com.ass.mcoerctest.constants.Constants.SELECTED_OPTION_KEY;

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.QuestionResponseHandler {
    private ViewPager mQuestionViewPager;
    private BottomNavigationView mBottomNavigationView;
    private ProgressBar mProgressBar;
    private QuestionViewPagerAdapter mQuestionViewPagerAdapter;

    private static int mQuestionNumber;
    private List<Integer> mQuestionIdList;

    private Chapter mChapter;
    private QuestionRepository mQuestionRepository;

    private String mSelectedOption;
    private Question mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mQuestionRepository = QuestionRepository.getInstance(this);

        Intent intent = getIntent();

        if (intent != null) {
            mChapter = intent.getParcelableExtra(CHAPTER_KEY);

            if (mChapter != null) {
                getSupportActionBar().setTitle(mChapter.getName() + " - " + mChapter.getQuestionCount());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                initGUI();

                loadQuestions();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initGUI() {

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.next:
                    goToNextQuestion();
                    break;
                case R.id.previous:
                    goToPreviousQuestion();
                    break;
                case R.id.view_answer:
                    if (mQuestion != null) {
                        Intent intent = new Intent(this, AnswerActivity.class);
                        intent.putExtra(QUESTION_KEY, mQuestion);
                        intent.putExtra(SELECTED_OPTION_KEY, mSelectedOption);
                        intent.putExtra(QUESTION_NUM_KEY, mQuestionNumber);
                        startActivity(intent);
                        break;
                    }
            }

            return true;

        });

        mQuestionViewPager = (ViewPager) findViewById(R.id.view_pager);

        mQuestionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Toast.makeText(TestActivity.this, "Scrolled page position: " + position, Toast.LENGTH_SHORT).show();
                Log.i("TAG", "Scrolled" + position);
                mQuestion = null;
                mSelectedOption = null;
                mBottomNavigationView.getMenu().findItem(R.id.view_answer).setEnabled(false);
            }

            @Override
            public void onPageSelected(int position) {
                mQuestionNumber = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadQuestions() {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UIHelper.showProgressBar(mProgressBar);
                mQuestionIdList = mQuestionRepository.getQuestionIds(mChapter.getSubjectCode(), mChapter.getId());
                if (mQuestionIdList != null) {
                    mQuestionViewPagerAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager(), mQuestionIdList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mQuestionViewPager.setAdapter(mQuestionViewPagerAdapter);
                        }
                    });
                }
                UIHelper.hideProgressBar(mProgressBar);
            }
        });

    }

    private void goToNextQuestion() {
        if (mQuestionNumber < mQuestionIdList.size() - 1) {
            mQuestionViewPager.setCurrentItem(mQuestionNumber + 1);
        }
    }

    private void goToPreviousQuestion() {
        if (mQuestionNumber > 0) {
            mQuestionViewPager.setCurrentItem(mQuestionNumber - 1);
        }
    }


    @Override
    public void updateQuestionResponse(int questionNumber, Question question, String selectedOption) {
        mQuestion = question;
        mSelectedOption = selectedOption;
        mQuestionNumber = questionNumber;

        mQuestionRepository.visitQuestion(question.getId());
        mBottomNavigationView.getMenu().findItem(R.id.view_answer).setEnabled(true);
    }
}