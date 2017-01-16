package com.appolica.tabcontroller;

import android.support.v4.app.Fragment;

public interface OnFragmentChangeListener {

    void onFragmentShown(FragmentProvider fragmentType, Fragment shownFragment);

    void onFragmentAlreadyVisible(FragmentProvider fragmentType, Fragment visibleFragment);

    void onFragmentCreated(FragmentProvider fragmentType, Fragment addedFragment);
}
