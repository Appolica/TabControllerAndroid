package com.appolica.tabcontroller.listener;


import androidx.fragment.app.Fragment;

import com.appolica.tabcontroller.FragmentProvider;

/**
 * Pass this listener to
 * {@link com.appolica.tabcontroller.TabController#setChangeListener(OnFragmentChangeListener)}
 * to be notified for one of it's events.
 */
public interface OnFragmentChangeListener {

    /**
     * Called when one of your fragments has been shown.
     *
     * @param provider Your shown fragment's provider.
     * @param shownFragment The fragment that has been shown.
     */
    void onFragmentShown(FragmentProvider provider, Fragment shownFragment);

    /**
     * Called on attempt to show an already visible fragment.
     *
     * @param provider Your visible fragment's provider.
     * @param visibleFragment The fragment that is already visible.
     */
    void onFragmentAlreadyVisible(FragmentProvider provider, Fragment visibleFragment);

    /**
     * Called when the fragment you want to show has been created by the
     * {@link com.appolica.tabcontroller.TabController}.
     *
     * @param provider Your created fragment's provider.
     * @param createdFragment Your created fragment.
     */
    void onFragmentCreated(FragmentProvider provider, Fragment createdFragment);
}
