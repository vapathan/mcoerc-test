package com.ass.mcoerctest.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.ass.mcoerctest.QuestionFragment;

import java.util.List;

public class QuestionViewPagerAdapter extends FragmentPagerAdapter {

    private List<Integer> mQuestionIdList;


    public QuestionViewPagerAdapter(@NonNull FragmentManager fm, List<Integer> questionIdList) {
        super(fm);
        mQuestionIdList = questionIdList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        int questionId = mQuestionIdList.get(position);

        QuestionFragment questionFragment = QuestionFragment.getInstance(position, questionId);

        return questionFragment;

    }

    @Override
    public int getCount() {
        return mQuestionIdList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;

    }

    public void updateQuestionResponse(int position, String updatedSelectedOption) {
        notifyDataSetChanged();
    }

}
