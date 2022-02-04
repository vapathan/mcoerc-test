package com.ass.mcoerctest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ass.mcoerctest.models.ScoreCard;

import static com.ass.mcoerctest.constants.Constants.SCORE_CARD_KEY;

public class ScoreCardActivity extends AppCompatActivity {

    private TextView tvTestTitle, tvStudentName, tvTotalQuestions;
    private TextView tvPhyQuestions, tvChemQuestions, tvMathQuestions;
    private TextView tvPhyAttempted, tvChemAttempted, tvMathAttempted;
    private TextView tvPhyWrong, tvChemWrong, tvMathWrong;
    private TextView tvPhyCorrect, tvChemCorrect, tvMathCorrect;


    private ImageButton ibClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        initGUI();

        getSupportActionBar().hide();
        Intent intent = getIntent();
        ScoreCard scoreCard = intent.getParcelableExtra(SCORE_CARD_KEY);
        if (scoreCard != null) {
            displayScoreCard(scoreCard);
        } else {

        }
    }

    private void initGUI() {
        tvTestTitle = (TextView) findViewById(R.id.tv_test);
        tvStudentName = (TextView) findViewById(R.id.tv_student_name);

        tvPhyQuestions = (TextView) findViewById(R.id.tv_phy_questions);
        tvPhyAttempted = (TextView) findViewById(R.id.tv_phy_attempted);
        tvPhyWrong = (TextView) findViewById(R.id.tv_phy_wrong);
        tvPhyCorrect = (TextView) findViewById(R.id.tv_phy_correct);

        tvChemQuestions = (TextView) findViewById(R.id.tv_chem_questions);
        tvChemAttempted = (TextView) findViewById(R.id.tv_chem_attempted);
        tvChemWrong = (TextView) findViewById(R.id.tv_chem_wrong);
        tvChemCorrect = (TextView) findViewById(R.id.tv_chem_correct);

        tvMathQuestions = (TextView) findViewById(R.id.tv_math_questions);
        tvMathAttempted = (TextView) findViewById(R.id.tv_math_attempted);
        tvMathWrong = (TextView) findViewById(R.id.tv_math_wrong);
        tvMathCorrect = (TextView) findViewById(R.id.tv_math_correct);

        ibClose = (ImageButton) findViewById(R.id.ib_close);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void displayScoreCard(ScoreCard scoreCard) {
        tvStudentName.setText(scoreCard.getStudentName());
        tvTestTitle.setText(scoreCard.getTestTitle());

        tvPhyQuestions.setText(String.valueOf(scoreCard.getPhyQuestions()));
        tvPhyAttempted.setText(String.valueOf(scoreCard.getPhyAttempted()));
        tvPhyWrong.setText(String.valueOf(scoreCard.getPhyAttempted() - scoreCard.getPhyCorrect()));
        tvPhyCorrect.setText(String.valueOf(scoreCard.getPhyCorrect()));

        tvChemQuestions.setText(String.valueOf(scoreCard.getChemQuestions()));
        tvChemAttempted.setText(String.valueOf(scoreCard.getChemAttempted()));
        tvChemWrong.setText(String.valueOf(scoreCard.getChemAttempted() - scoreCard.getChemCorrect()));
        tvChemCorrect.setText(String.valueOf(scoreCard.getChemCorrect()));

        tvMathQuestions.setText(String.valueOf(scoreCard.getMathQuestions()));
        tvMathAttempted.setText(String.valueOf(scoreCard.getMathAttempted()));
        tvMathWrong.setText(String.valueOf(scoreCard.getMathAttempted() - scoreCard.getMathCorrect()));
        tvMathCorrect.setText(String.valueOf(scoreCard.getMathCorrect()));

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}