package com.appolica.sample;

import android.support.v4.app.Fragment;

import com.appolica.sample.tabs.FlatFragment;
import com.appolica.sample.tabs.pager.ViewPagerFragment;
import com.appolica.sample.tabs.stack.StackFragment;
import com.appolica.tabcontroller.FragmentProvider;

public enum Tabs implements FragmentProvider {
    TAB_1(StackFragment.class),
    TAB_2(ViewPagerFragment.class),
    TAB_3(FlatFragment.class)
    ;

    private Class<? extends Fragment> fragmentClass;

    Tabs(Class<? extends Fragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    @Override
    public String getTag() {
        return name();
    }

    @Override
    public Fragment getInstance() {
        try {
            return fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
