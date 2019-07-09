package com.appolica.tabcontroller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Implementations of this interface determine how your fragments are going to be shown/hidden.
 * It could be by using {@link FragmentTransaction#show(Fragment)}/{@link FragmentTransaction#hide(Fragment)}
 * or {@link FragmentTransaction#attach(Fragment)}/{@link FragmentTransaction#detach(Fragment)}
 */
public interface ShowHideHandler {
    /**
     * Show the given fragment within the given transaction the way you want.
     *
     * @param transaction The transaction that will be committed in order to show your fragment.
     * @param fragment The fragment you want to show.
     * @return The transaction, that when committed should show your fragment.
     */
    public FragmentTransaction show(FragmentTransaction transaction, Fragment fragment);

    /**
     * Hide the given fragment within the given transaction the way you want.
     *
     * @param transaction The transaction that will be committed in order to hide your fragment.
     * @param fragment The fragment you want to hide.
     * @return The transaction, that when committed should hide your fragment.
     */
    public FragmentTransaction hide(FragmentTransaction transaction, Fragment fragment);

    /**
     * Called when {@link TabController} is saving its state. In some cases (like when you
     * show/hide your fragment by using FragmentTransaction::show/hide) you may want to save
     * the visibility of your fragment. This is where you should do that.
     * <br>
     * This method is called for each fragment, returned from {@link FragmentManager#getFragments()}.
     *
     * @param saveControllerState The output bundle for saving your visibility.
     * @param fragment The fragment which visibility you are saving.
     */
    public void save(Bundle saveControllerState, Fragment fragment);

    /**
     * Called when {@link TabController} is restoring its state. In some cases (like when you
     * show/hide your fragment by using FragmentTransaction::show/hide) in order to restore the
     * visibility of your fragments you should implement this method. Your fragment will become
     * visible or hidden depending on what action you add to the given {@link FragmentTransaction}.
     * <br>
     * This method is called for each fragment returned from {@link FragmentManager#getFragments()}.
     *
     * @param savedControllerState The bundle you restore your visibility state from.
     * @param transaction The transaction your fragment will be shown/hidden again.
     * @param fragment The fragment which's visibility you are restoring.
     */
    public void restore(@Nullable Bundle savedControllerState, FragmentTransaction transaction, Fragment fragment);

    /**
     * Since {@link Fragment#isVisible()} and {@link Fragment#isHidden()} work different,
     * {@link TabController} is using this abstract method, relying that the implementation will
     * work properly.
     *
     * @param fragment The fragment which's visibility {@link TabController} is querying.
     * @return true if your fragment is visible, false otherwise.
     */
    public boolean isVisible(Fragment fragment);
}
