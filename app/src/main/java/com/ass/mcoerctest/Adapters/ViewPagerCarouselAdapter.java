package com.ass.mcoerctest.Adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ass.mcoerctest.ViewPagerCarouselFragment;
import com.ass.mcoerctest.models.Notification;

import java.util.List;

public class ViewPagerCarouselAdapter extends FragmentStatePagerAdapter {
    private List<Notification>notifications;

    public ViewPagerCarouselAdapter(FragmentManager fm, List<Notification> notifications) {
        super(fm);
        this.notifications = notifications;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ViewPagerCarouselFragment.NOTIFICATION_KEY, notifications.get(position));
        ViewPagerCarouselFragment frag = new ViewPagerCarouselFragment();
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return (notifications == null) ? 0 : notifications.size();
    }

}
