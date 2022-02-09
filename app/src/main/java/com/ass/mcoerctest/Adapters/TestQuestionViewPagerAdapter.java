package com.ass.mcoerctest.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ass.mcoerctest.TestQuestionFragment;
import com.ass.mcoerctest.models.Question;

import java.util.List;

public class TestQuestionViewPagerAdapter extends FragmentPagerAdapter {

    private List<Question> mQuestionList;


    public TestQuestionViewPagerAdapter(@NonNull FragmentManager fm, List<Question> questionList) {
        super(fm);
        mQuestionList = questionList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        Question question = mQuestionList.get(position);
        TestQuestionFragment testQuestionFragment = TestQuestionFragment.getInstance(position, question);
        return testQuestionFragment;

    }

    @Override
    public int getCount() {
        return mQuestionList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;

    }

    public void updateQuestionResponse(int position, String updatedSelectedOption) {
        notifyDataSetChanged();
    }

}
