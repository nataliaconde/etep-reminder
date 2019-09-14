package com.etep.reminder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapterLogin extends FragmentStatePagerAdapter {
    int counttab;

    public PageAdapterLogin(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginTab loginTab = new LoginTab();
                return loginTab;
            case 1:
                SignUpTab signUpTab = new SignUpTab();
                return signUpTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
