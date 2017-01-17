package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class AttachDetachHandler implements ShowHideHandler {
    private static final String TAG = "AttachDetachHandler";

    @Override
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment) {
        Log.d(TAG, "attach: " + fragment);
        return transaction.attach(fragment);
    }

    @Override
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment) {
        Log.d(TAG, "detach: " + fragment);
        return transaction.detach(fragment);
    }

    @Override
    public void save(@Nullable Bundle saveControllerState, Fragment fragment) {
        // should be empty
    }

    @Override
    public void restore(@Nullable Bundle savedControllerState, FragmentTransaction transaction, Fragment fragment) {
        // should be empty
    }

    @Override
    public boolean isVisible(Fragment fragment) {
        return fragment.isVisible();
    }
}
