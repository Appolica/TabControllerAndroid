package com.appolica.fragmentcontrollersample.tabs;

import android.support.v4.app.Fragment;

import com.appolica.fragmentcontroller.fragment.ControllerFragmentType;

public enum FragmentsType implements ControllerFragmentType {
    NESTED(NestedFragment.class, "NestedFragment");

    private Class<? extends Fragment> fragmentClass;
    private String tag;
    private int index;

    FragmentsType(Class<? extends Fragment> fragmentClass, String tag) {
        this.fragmentClass = fragmentClass;
        this.tag = tag;
        index = 0;
    }

    @Override
    public Fragment getInstance() {
        if (fragmentClass.equals(NestedFragment.class)) {
            return NestedFragment.getInstance(index);
        }

        return null;
    }

    public FragmentsType withIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public String getTag() {
        return tag;
    }
}

