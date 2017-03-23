package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Implementations of this interface determine how your fragment's are going to be shown/hidden.
 * It could be by using {@link FragmentTransaction#show(Fragment)}/{@link FragmentTransaction#hide(Fragment)}
 * or {@link FragmentTransaction#attach(Fragment)}/{@link FragmentTransaction#detach(Fragment)}
 */
public interface ShowHideHandler {
    /**
     * Show the given fragment within the given transaction the way you want.
     * @param transaction The transaction that will be committed in order to show your fragment.
     * @param fragment The fragment you want to show.
     * @return The transaction, that when committed should show your fragment.
     */
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment);

    /**
     * Hide the given fragment within the given transaction the way you want.
     * @param transaction The transaction that will be committed in order to hide your fragment.
     * @param fragment The fragment you want to hide.
     * @return The transaction, that when committed should hide your fragment.
     */
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment);

    /**
     *
     * @param saveControllerState
     * @param fragment
     */
    public void save(Bundle saveControllerState, Fragment fragment);

    /**
     *
     * @param savedControllerState
     * @param transaction
     * @param fragment
     */
    public void restore(@Nullable Bundle savedControllerState, FragmentTransaction transaction, Fragment fragment);

    /**
     *
     * @param fragment
     * @return
     */
    public boolean isVisible(Fragment fragment);
}
