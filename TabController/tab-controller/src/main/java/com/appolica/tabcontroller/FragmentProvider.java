package com.appolica.tabcontroller;

import android.support.v4.app.Fragment;

public interface FragmentProvider {

    public String getTag();

    public Fragment getInstance();
}
