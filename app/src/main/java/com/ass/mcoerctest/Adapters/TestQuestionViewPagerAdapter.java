package com.ass.mcoerctest.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ass.mcoerctest.TestQuestionFragment;
import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.TestQuestion;

import java.util.List;

public class TestQuestionViewPagerAdapter extends FragmentPagerAdapter {

    private List<TestQuestion> mTestQuestionList;


    public TestQuestionViewPagerAdapter(@NonNull FragmentManager fm, List<TestQuestion> testQuestionList) {
        super(fm);
        mTestQuestionList = testQuestionList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        TestQuestion testQuestion = mTestQuestionList.get(position);
        Question question = testQuestion.getQuestion();
        TestQuestionFragment testQuestionFragment = TestQuestionFragment.getInstance(position, question, testQuestion);
        return testQuestionFragment;

    }

    @Override
    public int getCount() {
        return mTestQuestionList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;

    }

    public void updateQuestionResponse(int position, String updatedSelectedOption) {
        notifyDataSetChanged();
    }

}
