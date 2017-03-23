package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 *
 */
public class ShowHideFrHandler implements ShowHideHandler {
    @Override
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment) {

        return transaction.show(fragment);
    }

    @Override
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment) {

        return transaction.hide(fragment);
    }

    @Override
    public void save(Bundle saveControllerState, Fragment fragment) {
        boolean visible = !fragment.isHidden();
        saveControllerState.putBoolean(fragment.getTag(), visible);
    }

    @Override
    public void restore(@Nullable Bundle savedControllerState, FragmentTransaction transaction, Fragment fragment) {
        if (savedControllerState != null) {
            boolean visible = savedControllerState.getBoolean(fragment.getTag());
            if (visible) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
        }
    }

    @Override
    public boolean isVisible(Fragment fragment) {
        return !fragment.isHidden();
    }
}
