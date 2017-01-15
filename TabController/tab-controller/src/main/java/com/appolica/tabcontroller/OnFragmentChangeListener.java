package com.appolica.tabcontroller;

import android.support.v4.app.Fragment;

public interface OnFragmentChangeListener {


    void onFragmentShown(FragmentProvider fragmentType, Fragment fragmentToShow);

    void onFragmentAlreadyVisible(FragmentProvider fragmentType, Fragment fragmentToShow);

    void onFragmentCreated(FragmentProvider fragmentType, Fragment addedFragment);
}
