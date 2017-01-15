package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public interface ShowHideHandler {
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment);
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment);
    public void save(Bundle saveControllerState, Fragment fragment);
    public void restore(@Nullable Bundle savedControllerState, FragmentTransaction transaction, Fragment fragment);
}
