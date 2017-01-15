package com.appolica.sample;

import android.support.v4.app.Fragment;

import com.appolica.tabcontroller.FragmentProvider;

public enum Tabs implements FragmentProvider {
    TAB_1(1),
    TAB_2(2),
    TAB_3(3),
    TAB_4(4)
    ;

    private int tabNum;

    Tabs(int tabNum) {
        this.tabNum = tabNum;
    }

    @Override
    public String getTag() {
        return name();
    }

    @Override
    public Fragment getInstance() {
        return SampleTab.getInstance(tabNum);
    }
}
