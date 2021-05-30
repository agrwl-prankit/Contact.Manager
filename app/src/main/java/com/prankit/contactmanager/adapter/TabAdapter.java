package com.prankit.contactmanager.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prankit.contactmanager.fragment.CallLogFragment;
import com.prankit.contactmanager.fragment.MyContactFragment;
import com.prankit.contactmanager.fragment.PhoneContactFragment;

public class TabAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;

    public TabAdapter(Context c, FragmentManager fm, int totalTabs){
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new MyContactFragment();
        if (position == 1) return new PhoneContactFragment();
        return new CallLogFragment();
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
