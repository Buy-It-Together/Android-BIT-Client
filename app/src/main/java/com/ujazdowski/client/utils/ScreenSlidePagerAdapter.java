package com.ujazdowski.client.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.ujazdowski.client.ui.fragment.Title;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Fragment> fragments;

    public ScreenSlidePagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    public void remove(Fragment fragment) {
        this.fragments.remove(fragment);
    }

    public void add(Fragment fragment) {
        if (!fragments.contains(fragment)) {
            fragments.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment instanceof Title) {
            return context.getResources().getString(((Title) fragment).getTitleResource());
        }
        return super.getPageTitle(position);
    }
}