package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class SimpleShowHideHandler implements ShowHideHandler {
    @Override
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment) {
        return null;
    }

    @Override
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment) {
        return null;
    }

    @Override
    public void save(@Nullable Bundle saveControllerState, Fragment fragment) {

    }

    @Override
    public void restore(@Nullable Bundle savedControllerState, FragmentTransaction transaction, Fragment fragment) {

    }
}
