package com.ass.mcoerctest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.TestQuestion;

import io.github.sidvenu.mathjaxview.MathJaxView;

public class TestQuestionFragment extends Fragment implements View.OnClickListener {

    private TextView tvQuestionNo;
    private ImageView ivQuestion;


    private TextView tvOptionNoA, tvOptionNoB, tvOptionNoC, tvOptionNoD;
    private MathJaxView tvOptionA, tvOptionB, tvOptionC, tvOptionD, tvQuestion;
    private ImageView ivOptionA, ivOptionB, ivOptionC, ivOptionD;

    private TextView tvAnswer, tvViewSolution;
    private ImageView ivAnswer;

    private LinearLayout llayoutA, llayoutB, llayoutC, llayoutD;


    public static final String QUESTION_NUMBER = "question_number";
    public static final String QUESTION = "question";
    public static final String TEST_QUESTION = "test_question";
    public static final String QUESTION_ID = "question_id";
    public static final String SELECTED_OPTION = "selected_option";

    private int mQuestionId;
    private int mQuestionNum;
    private Question mQuestion;
    private TestQuestion mTestQuestion;
    private LinearLayout[] optionLayouts;

    private QuestionResponseHandler mQuestionResponseHandler;

    public interface QuestionResponseHandler {
        void updateQuestionResponse(int questionNumber, Question question, String selectedOption);
    }


    // TODO: Rename and change types and number of parameters
    public static TestQuestionFragment getInstance(int questionNumber, Question question, TestQuestion testQuestion) {
        TestQuestionFragment questionFragment = new TestQuestionFragment();
        Bundle args = new Bundle();
        args.putInt(QUESTION_NUMBER, questionNumber);
        args.putParcelable(QUESTION, question);
        args.putParcelable(TEST_QUESTION, testQuestion);
        questionFragment.setArguments(args);
        return questionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mQuestionNum = getArguments().getInt(QUESTION_NUMBER);
            mQuestion = getArguments().getParcelable(QUESTION);
            mTestQuestion = getArguments().getParcelable(TEST_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initGui(view);

        if (mQuestion != null && mTestQuestion != null) {
            displayQuestion(mQuestion, mTestQuestion);
        }

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mQuestionResponseHandler = (QuestionResponseHandler) getActivity();
    }

    private void initGui(View view) {

        //layout_instruction
        tvQuestionNo = (TextView) view.findViewById(R.id.tv_question_no);


        //layout_ question
        tvQuestion = (MathJaxView) view.findViewById(R.id.tv_question_title);
        ivQuestion = (ImageView) view.findViewById(R.id.iv_question_title);

        //layout_options
        tvOptionA = (MathJaxView) view.findViewById(R.id.tv_option_A);
        tvOptionB = (MathJaxView) view.findViewById(R.id.tv_option_B);
        tvOptionC = (MathJaxView) view.findViewById(R.id.tv_option_C);
        tvOptionD = (MathJaxView) view.findViewById(R.id.tv_option_D);

        ivOptionA = (ImageView) view.findViewById(R.id.iv_option_A);
        ivOptionB = (ImageView) view.findViewById(R.id.iv_option_B);
        ivOptionC = (ImageView) view.findViewById(R.id.iv_option_C);
        ivOptionD = (ImageView) view.findViewById(R.id.iv_option_D);

        tvOptionNoA = (TextView) view.findViewById(R.id.tv_option_A_no);
        tvOptionNoB = (TextView) view.findViewById(R.id.tv_option_B_no);
        tvOptionNoC = (TextView) view.findViewById(R.id.tv_option_C_no);
        tvOptionNoD = (TextView) view.findViewById(R.id.tv_option_D_no);

        llayoutA = (LinearLayout) view.findViewById(R.id.llayout_A);
        llayoutB = (LinearLayout) view.findViewById(R.id.llayout_B);
        llayoutC = (LinearLayout) view.findViewById(R.id.llayout_C);
        llayoutD = (LinearLayout) view.findViewById(R.id.llayout_D);

        optionLayouts = new LinearLayout[]{llayoutA, llayoutB, llayoutC, llayoutD};

        llayoutA.setOnClickListener(this);
        llayoutB.setOnClickListener(this);
        llayoutC.setOnClickListener(this);
        llayoutD.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_A:
                selectOption(R.id.llayout_A);
                mTestQuestion.setAttempted(true);
                mTestQuestion.setSelectedOption("A");
                mQuestionResponseHandler.updateQuestionResponse(mQuestionNum, mQuestion, "A");
                break;
            case R.id.llayout_B:
                selectOption(R.id.llayout_B);
                mTestQuestion.setAttempted(true);
                mTestQuestion.setSelectedOption("B");
                mQuestionResponseHandler.updateQuestionResponse(mQuestionNum, mQuestion, "B");
                break;
            case R.id.llayout_C:
                selectOption(R.id.llayout_C);
                mTestQuestion.setAttempted(true);
                mTestQuestion.setSelectedOption("c");
                mQuestionResponseHandler.updateQuestionResponse(mQuestionNum, mQuestion, "C");
                break;
            case R.id.llayout_D:
                mTestQuestion.setAttempted(true);
                mTestQuestion.setSelectedOption("D");
                selectOption(R.id.llayout_D);
                mQuestionResponseHandler.updateQuestionResponse(mQuestionNum, mQuestion, "D");
                break;
        }

    }

    private void displayQuestion(Question question, TestQuestion mTestQuestion) {
        //display question
        tvQuestionNo.setText(mQuestionNum + 1 + ".");
        tvQuestion.setText(question.getTitle(true));
        tvOptionA.setText(question.getOptA(true));
        tvOptionB.setText(question.getOptB(true));
        tvOptionC.setText(question.getOptC(true));
        tvOptionD.setText(question.getOptD(true));

        if (mTestQuestion.isAttempted()) {
            switch (mTestQuestion.getSelectedOption()) {
                case "A":
                    selectOption(R.id.llayout_A);
                    break;
                case "B":
                    selectOption(R.id.llayout_B);
                    break;
                case "C":
                    selectOption(R.id.llayout_C);
                    break;
                case "D":
                    selectOption(R.id.llayout_D);
                    break;
            }
        }
    }


    private void selectOption(int selectedOptionLayoutId) {
        for (LinearLayout layout : optionLayouts) {
            if (layout.getId() == selectedOptionLayoutId) {
                layout.setBackgroundColor(getContext().getResources().getColor(R.color.teal_700));
            } else {
                layout.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }
        }
    }


}