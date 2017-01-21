package com.appolica.tabcontroller.listener;

import android.support.v4.app.Fragment;

import com.appolica.tabcontroller.FragmentProvider;

public interface OnFragmentChangeListener {

    void onFragmentShown(FragmentProvider fragmentType, Fragment shownFragment);

    void onFragmentAlreadyVisible(FragmentProvider fragmentType, Fragment visibleFragment);

    void onFragmentCreated(FragmentProvider fragmentType, Fragment addedFragment);
}
