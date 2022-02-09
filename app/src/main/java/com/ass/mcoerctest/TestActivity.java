package com.ass.mcoerctest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ass.mcoerctest.Adapters.TestQuestionViewPagerAdapter;
import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.dialogs.TestSubmitConfirmDialogFragment;
import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.ScoreCard;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Test;
import com.ass.mcoerctest.repositories.QuestionRepository;
import com.ass.mcoerctest.repositories.StudentRepository;
import com.ass.mcoerctest.repositories.TestRepository;
import com.ass.mcoerctest.services.ApiResponse;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.DateTimeHelper;
import com.ass.mcoerctest.utilities.ui.UIHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ass.mcoerctest.constants.Constants.SCORE_CARD_KEY;
import static com.ass.mcoerctest.constants.Constants.TEST_KEY;

public class TestActivity extends AppCompatActivity implements TestQuestionFragment.QuestionResponseHandler, TestSubmitConfirmDialogFragment.ConfirmDialogListener {
    private ViewPager mQuestionViewPager;
    private BottomNavigationView mBottomNavigationView;
    private ProgressBar mProgressBar;
    private TestQuestionViewPagerAdapter mTestQuestionViewPagerAdapter;
    private TextView tvTimer, tvSubmitTest;

    private Test mTest;
    private long startTime;
    private TestRepository mTestRepository;
    private List<Question> mTestQuestionList;
    private QuestionRepository mQuestionRepository;
    private CountDownTimer mCountDownTimer;
    private static int mQuestionNumber;
    private long timeSpent;
    private static boolean isTestPaused;
    private ScoreCard scoreCard;
    private RetrofitApi mRetrofitApi;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();

        mRetrofitApi = retrofitApiClient.getRetrofitApi();
        mQuestionRepository = QuestionRepository.getInstance(this);
        mTestRepository = TestRepository.getInstance(this);

        Intent intent = getIntent();

        if (intent != null) {

            AppExecutor.getInstance().dbExecutor().execute(() -> {
                student = StudentRepository.getInstance(this).getStudent();
            });

            mTest = intent.getParcelableExtra(TEST_KEY);

            if (mTest != null ) {
                getSupportActionBar().setTitle(mTest.getTitle());
                getSupportActionBar().hide();
                //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                initGUI();
                loadAndStartTest();

            } else {
                finish();
            }
        }
    }

    private void initGUI() {

        tvTimer = (TextView) findViewById(R.id.tv_timer);
        tvSubmitTest = (TextView) findViewById(R.id.tv_submit);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        tvSubmitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubmitTestDialog();
            }
        });


        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.next:
                    goToNextQuestion();
                    break;
                case R.id.previous:
                    goToPreviousQuestion();
                    break;
                /*case R.id.pause_play:
                    item.setVisible(false);
                    if (isTestPaused) {
                        resumeTimer();
                        item.setIcon(R.drawable.ic_pause);
                        item.setTitle(R.string.pause);
                    } else {
                        pauseTest(1);
                        item.setIcon(R.drawable.ic_resume);
                        item.setTitle(R.string.resume);
                    }
                    break;*/
            }

            return true;

        });

        mQuestionViewPager = (ViewPager) findViewById(R.id.view_pager);

        mQuestionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Toast.makeText(TestActivity.this, "Scrolled page position: " + position, Toast.LENGTH_SHORT).show();
                Log.i("TAG", "Scrolled" + position);

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

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to stop  and submit this test?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pauseTest(1);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }

    private void loadQuestions() {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UIHelper.showProgressBar(mProgressBar);
                mTestQuestionList = mQuestionRepository.getQuestions(mTest.getId());
                if (mTestQuestionList != null && mTestQuestionList.size() > 0) {
                    mTestQuestionViewPagerAdapter = new TestQuestionViewPagerAdapter(getSupportFragmentManager(), mTestQuestionList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mQuestionViewPager.setAdapter(mTestQuestionViewPagerAdapter);
                            mQuestionViewPager.setCurrentItem(0);
                            //create timer for test
                            createTimer();
                            //start timer
                            startTimer();
                        }
                    });
                } else {
                   // getQuestionList(mTest.getId(), mProgressBar);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error occurred while starting the test. Please try again.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }
                UIHelper.hideProgressBar(mProgressBar);
            }
        });
    }

    private void getQuestionList(int testId, ProgressBar progressBar) {
        //Get Questions data from remote server
        final List<Question>[] questionList = new List[]{new ArrayList<>()};

        //Animations.blink(mContext, imageView);
        if (progressBar != null) {
            UIHelper.showProgressBar(progressBar);
        }
        Call<Question[]> call = mRetrofitApi.getQuestions(Api.API_KEY, testId);
        call.enqueue(new Callback<Question[]>() {
            @Override
            public void onResponse(Call<Question[]> call, Response<Question[]> response) {
                questionList[0] = Arrays.asList(response.body());
                Log.i("INFO", "TTT : " + questionList[0].toString());
                mQuestionRepository.saveQuestions(testId, questionList[0]);
                mTestQuestionList = questionList[0];
                if (progressBar != null) {
                    UIHelper.hideProgressBar(progressBar);
                }
            }

            @Override
            public void onFailure(Call<Question[]> call, Throwable t) {
                questionList[0] = null;
                Log.i("INFO", t.getMessage());
                if (progressBar != null) {
                    UIHelper.hideProgressBar(progressBar);
                }
            }
        });
        // imageView.clearAnimation();
    }


    private void loadAndStartTest() {
        loadQuestions();

    }

    private void goToNextQuestion() {
        if (mQuestionNumber < mTestQuestionList.size() - 1) {
            mQuestionViewPager.setCurrentItem(mQuestionNumber + 1);
        }
    }

    private void goToPreviousQuestion() {
        if (mQuestionNumber > 0) {
            mQuestionViewPager.setCurrentItem(mQuestionNumber - 1);
        }
    }


    private void createTimer() {
        //Create and initialize timer for test
        long seconds = 0;
        if (mTest.getCurrentStatus() == 0 || mTest.getCurrentStatus() == 2) {
            //if test is not started yet or fresh attempt is there
            seconds = DateTimeHelper.getSeconds(mTest.getTimeDuration());
        } else {
            //if test is in paused state and going to be resume
            // seconds = DateTimeHelper.getSeconds(mTest.getTimeDuration()) - mTest.getTimeSpent() / 1000;
        }

        startTime = seconds * 1000;
        mCountDownTimer = new CountDownTimer(startTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timeSpent = startTime - millisUntilFinished;
                String text = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 12,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                tvTimer.setText(text);
            }

            public void onFinish() {
                tvTimer.setText("done!");
                submitTest();
            }
        };
    }

    private void createTimer(final long milliSeconds) {
        //Create and initialize timer for test

        mCountDownTimer = new CountDownTimer(milliSeconds, 1000) {

            public void onTick(long millisUntilFinished) {
                timeSpent = milliSeconds - millisUntilFinished;
                String text = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 12,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                tvTimer.setText(text);
            }

            public void onFinish() {
                tvTimer.setText("done!");
                submitTest();
            }
        };
    }

    private void startTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }
    }

    private void showSubmitTestDialog() {
        pauseTimer();
        TestSubmitConfirmDialogFragment testSubmitConfirmDialogFragment = new TestSubmitConfirmDialogFragment();
        testSubmitConfirmDialogFragment.setCancelable(false);
        testSubmitConfirmDialogFragment.show(getSupportFragmentManager(), "Confirm");

    }

    private void pauseTimer() {
        //pause the test timer
        mCountDownTimer.cancel();
    }

    private void pauseTest(int status) {
        //pause the test timer and set of status to in progress
        pauseTimer();
        isTestPaused = true;
        //mTest.setTimeSpent(mTest.getTimeSpent() + timeSpent);
        //mTest.setCurrentStatus(status);
        //mTest.setCurrentQuestionNumber(mQuestionNumber);
        //mTestRepository.updateTestStatus(mTest);
    }

    private void resumeTimer() {
        createTimer(startTime - timeSpent);
        startTimer();
        isTestPaused = false;
    }

    private void submitTest() {
        scoreCard = new ScoreCard();
        if (student != null) {
            scoreCard.setStudentName(student.getName());
            // scoreCard.setStudentId(student.getPrn());
            // scoreCard.prepareScoreCard(mTest, mTestQuestionList);
           /* mTest.setScore("P: " + scoreCard.getPhyCorrect() + "/" + scoreCard.getPhyQuestions() +
                    " C: " + scoreCard.getChemCorrect() + "/" + scoreCard.getChemQuestions() +
                    " M: " + scoreCard.getMathCorrect() + "/" + scoreCard.getMathQuestions());*/
            uploadScoreCard(scoreCard);
        }
    }


    @Override
    public void updateQuestionResponse(int questionNumber, Question question, String selectedOption) {
        mQuestionNumber = questionNumber;
        if (selectedOption != null) {

            //mark question as attempted;
            mTestRepository.markTestQuestionAsAttempted(mTest.getId(), question.getId(), selectedOption);
        }
    }

    @Override
    public void onConfirm(String confirmation) {
        if (confirmation.equals("Yes")) {
            submitTest();
        }
    }

    public void uploadScoreCard(ScoreCard scoreCard) {
        Gson gson = new Gson();
        String scoreCardDetails = gson.toJson(scoreCard);

        Call<ApiResponse> call = mRetrofitApi.saveScoreCard(Api.API_KEY, scoreCardDetails);
        UIHelper.showProgressBar(mProgressBar);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getStatus() == 201) {
                        pauseTest(2);
                        Intent intent = new Intent(getApplicationContext(), ScoreCardActivity.class);
                        intent.putExtra(SCORE_CARD_KEY, scoreCard);
                        intent.putExtra("TEST", "test");
                        finish();
                        startActivity(intent);
                    } else {
                        showError();
                    }
                } else {
                    showError();
                }
                UIHelper.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                UIHelper.hideProgressBar(mProgressBar);
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(TestActivity.this, "Could not submit test. Please try again!", Toast.LENGTH_LONG).show();
    }
}