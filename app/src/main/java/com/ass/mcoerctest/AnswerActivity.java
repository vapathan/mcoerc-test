package com.ass.mcoerctest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ass.mcoerctest.models.Question;

import io.github.sidvenu.mathjaxview.MathJaxView;

import static com.ass.mcoerctest.constants.Constants.QUESTION_KEY;
import static com.ass.mcoerctest.constants.Constants.QUESTION_NUM_KEY;
import static com.ass.mcoerctest.constants.Constants.SELECTED_OPTION_KEY;

public class AnswerActivity extends AppCompatActivity {

    private TextView tvQuestionNo, tvSelectedOption, tvCorrectOption;
    private ImageView ivQuestion;


    private MathJaxView tvQuestion, tvCorrectAnswer, tvExplanation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        getSupportActionBar().setTitle("Answer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initGUI();

        Intent intent = getIntent();
        if (intent != null) {
            int questionNumber = intent.getIntExtra(QUESTION_NUM_KEY, 0);
            String selectedOption = intent.getStringExtra(SELECTED_OPTION_KEY);
            Question question = intent.getParcelableExtra(QUESTION_KEY);

            if (selectedOption != null && question != null) {
                tvQuestionNo.setText(questionNumber + 1 + ".");
                tvQuestion.setText(question.getTitle(true));
                tvCorrectOption.setText(question.getCorrectOption());
                tvCorrectAnswer.setText( question.getCorrectAnswer(question.getCorrectOption()));
                tvExplanation.setText(question.getExplanation(true));
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
        tvQuestionNo = (TextView) findViewById(R.id.tv_question_no);
        tvCorrectOption = (TextView) findViewById(R.id.tv_correct_option);
        tvQuestion = (MathJaxView) findViewById(R.id.tv_question_title);
        tvCorrectAnswer = (MathJaxView) findViewById(R.id.tv_correct_answer);
        tvExplanation = (MathJaxView) findViewById(R.id.tv_explanation);

    }
}