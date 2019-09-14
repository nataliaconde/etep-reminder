package com.etep.reminder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapterTasks extends FragmentStatePagerAdapter {
    int counttab;

    public PageAdapterTasks(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TodayTab todayTab = new TodayTab();
                return todayTab;
            case 1:
                TomorrowTab tomorrowTab = new TomorrowTab();
                return tomorrowTab;
            case 2:
                NextDaysTab nextDaysTab = new NextDaysTab();
                return nextDaysTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
