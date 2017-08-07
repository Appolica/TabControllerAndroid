package com.appolica.fragmentcontrollersample.tabs.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appolica.fragmentcontrollersample.Tabs;
import com.appolica.fragmentcontrollersample.tabs.NestedFragment;


public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Tabs.values()[position].name();
    }

    @Override
    public Fragment getItem(int position) {
        final NestedFragment fragment = NestedFragment.getInstance(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return PagerTabs.values().length;
    }

    public static enum PagerTabs {
        TAB_1(1),
        TAB_2(2);

        private int index;

        PagerTabs(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
}
