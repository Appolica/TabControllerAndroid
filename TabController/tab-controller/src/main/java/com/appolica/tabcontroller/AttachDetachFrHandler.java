package com.appolica.tabcontroller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class AttachDetachFrHandler extends SimpleShowHideHandler {

    @Override
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment) {
        return transaction.attach(fragment);
    }

    @Override
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment) {
        return transaction.detach(fragment);
    }
}
