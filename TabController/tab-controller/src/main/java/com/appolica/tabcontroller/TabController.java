package com.appolica.tabcontroller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.appolica.tabcontroller.listener.OnFragmentChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The core of the library. This class controls the switching between fragments.
 */
public class TabController {

    private static final String TAG = "TabController";

    private static final String BUNDLE_KEY = "bundle-key-tab-controller";

    private final FragmentManager fragmentManager;
    private final int containerId;

    private ShowHideHandler showHideHandler;

    private OnFragmentChangeListener changeListener;

    /**
     * Create a new instance of {@link TabController} with the default {@link ShowHideHandler}.
     *
     * @param fragmentManager The support fragment manager, used to switch between fragments.
     * @param containerId The view id of the container for your fragments.
     *
     * @see ShowHideHandler
     */
    public TabController(FragmentManager fragmentManager, int containerId) {
        this(fragmentManager, containerId, new ShowHideFrHandler());
    }

    /**
     * Create a new instance of {@link TabController}.
     *
     * @param fragmentManager The support fragment manager, used to switch between fragments.
     * @param containerId The view id of the container for your fragments.
     * @param showHideHandler The {@link ShowHideHandler} which determines how your fragments
     *                        are going to be shown/hidden.
     *
     * @see ShowHideHandler
     */
    public TabController(FragmentManager fragmentManager, int containerId, ShowHideHandler showHideHandler) {
        this.fragmentManager = fragmentManager;
        FragmentManager.enableDebugLogging(false);
        this.containerId = containerId;
        this.showHideHandler = showHideHandler;
    }

    /**
     * Show the given fragment in the container, provided to the constructor. If
     * FragmentManager::findFragmentByTag returns null for the tag, given by the provider,
     * your fragment's instance will be obtained by calling FragmentProvider::getInstance.
     * Otherwise it will be reused.
     *
     * If there is already a visible fragment, it will be hidden. How fragments are shown/hidden
     * depends on {@link ShowHideHandler}.
     *
     * @param provider The {@link FragmentProvider} for the fragment you want to show.
     *
     * @see FragmentProvider
     */
    public void switchTo(FragmentProvider provider) {

        List<Notifier> notifiers = new ArrayList<>();

        inTransaction(transaction -> {

            final Fragment fragmentToShow = fragmentManager.findFragmentByTag(provider.getTag());

            if (fragmentToShow != null) {
                if (!showHideHandler.isVisible(fragmentToShow)) {
                    //Fragment is active but not visible
                    hideVisibleFragment(transaction);
                    showFragment(fragmentToShow, transaction);

                    notifiers.add(listener -> listener.onFragmentShown(provider, fragmentToShow));
                } else {
                    //Fragment is already visible on the screen
                    notifiers.add(listener -> listener.onFragmentAlreadyVisible(provider, fragmentToShow));
                }
            } else {
                //Fragment does not exist
                hideVisibleFragment(transaction);

                Fragment addedFragment = addToFragmentManager(provider, transaction);

                notifiers.add(listener -> listener.onFragmentCreated(provider, addedFragment));
                notifiers.add(listener -> listener.onFragmentShown(provider, addedFragment));
            }

            return notifiers;
        });
    }

    private void hide(FragmentProvider currentFragment) {
        final Fragment visibleFragment = fragmentManager.findFragmentByTag(currentFragment.getTag());

        if (visibleFragment != null) {

            inTransaction(transaction -> {
                showHideHandler.hide(transaction, visibleFragment);
                return new ArrayList<>();
            });
        }
    }

    private void showFragment(Fragment fragment, FragmentTransaction transaction) {
        showHideHandler.show(transaction, fragment);
    }

    private Fragment addToFragmentManager(FragmentProvider fragmentType, FragmentTransaction transaction) {
        Fragment fragment = fragmentType.getInstance();
        transaction.add(containerId, fragment, fragmentType.getTag());

        return fragment;
    }

    private void hideVisibleFragment(FragmentTransaction fragmentTransaction) {
        final Fragment visibleFragment = getVisibleFragment();

        if (visibleFragment != null) {
            showHideHandler.hide(fragmentTransaction, visibleFragment);
        }
    }

    /**
     * Iterates through the fragments, returned by FragmentManager::getFragments.
     *
     * @return The first visible fragment found in the list or null.
     */
    @Nullable
    public Fragment getVisibleFragment() {
        final List<Fragment> fragments = getFMFragments();
        for (Fragment fragment : fragments) {
            if (showHideHandler.isVisible(fragment)) {
                return fragment;
            }
        }

        return null;
    }

    /**
     * Find a fragment by it's {@link FragmentProvider}. Same as calling
     * fragmentManager.findFragmentByTag(fragmentProvider.getTag());
     *
     * @param fragmentProvider The fragment provider of the fragment you want to obtain.
     * @return The fragment which the given {@link FragmentProvider} is for.
     */
    @Nullable
    public Fragment getFragment(@NonNull FragmentProvider fragmentProvider) {
        return fragmentManager.findFragmentByTag(fragmentProvider.getTag());
    }

    /**
     * Restore the state of the {@link TabController}. This will show the last visible fragment
     * before saving the state.
     * <br>
     * Make sure you call {@link #save(Bundle)} when saving your instance
     * state.
     *
     * @param savedInstanceState The saved instance state Bundle where the TabController state
     *                           wil be obtained from.
     */
    public void restore(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            final Bundle controllerState = savedInstanceState.getBundle(BUNDLE_KEY);

            if (controllerState == null) {
                throw new IllegalStateException("TabController's bundle not found in savedInstanceState. Did you call TabController::save in onSaveInstanceState(outState)?");
            }

            final List<Fragment> fragments = getFMFragments();

            inTransaction(transaction -> {

                for (Fragment fragment : fragments) {
                    showHideHandler.restore(controllerState, transaction, fragment);
                }

                return new ArrayList<>();
            });
        }
    }

    /**
     * Save the state of the {@link TabController} in order to be able to restore your last visible
     * fragment when your app restores.
     * <br>
     * Make sure you call {@link #restore(Bundle)} when restoring your instance state.
     * @param savedInstanceState The output Bundle where the state will be saved to.
     */
    public void save(Bundle savedInstanceState) {

        final Bundle controllerState = new Bundle();

        final List<Fragment> fragments = getFMFragments();

        for (Fragment fragment : fragments) {

            showHideHandler.save(controllerState, fragment);
        }

        savedInstanceState.putBundle(BUNDLE_KEY, controllerState);
    }

    private void inTransaction(@NonNull TransactionBody body) {
        inTransaction(body, FragmentTransaction::commitNow);
    }

    private void inAsyncTransaction(@NonNull TransactionBody body) {
        inTransaction(body, FragmentTransaction::commit);
    }

    private void inTransaction(@NonNull TransactionBody body, @NonNull TransactionCommitter committer) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setAllowOptimization(false);

        List<Notifier> notifiers = body.runInTransaction(transaction);

        committer.commitTransaction(transaction);

        if (changeListener != null)
            for (Notifier notifier : notifiers) {
                notifier.notifyListener(changeListener);
            }
    }

    /**
     * Set listener to be notified on one of the {@link TabController}'s events.
     *
     * @param changeListener Your implementation of the listener.
     *
     * @see OnFragmentChangeListener
     */
    public void setChangeListener(OnFragmentChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @NonNull
    private List<Fragment> getFMFragments() {
        final List<Fragment> fmList = fragmentManager.getFragments();

        final List<Fragment> resultList = new ArrayList<>();

        if (fmList != null) {

            resultList.addAll(fmList);

            final Iterator<Fragment> iterator = resultList.iterator();
            while (iterator.hasNext()) {

                if (iterator.next() == null) {
                    iterator.remove();
                }
            }
        }

        return resultList;
    }

    private static interface TransactionBody {
        @NonNull
        public List<Notifier> runInTransaction(FragmentTransaction fragmentTransaction);
    }

    private static interface TransactionCommitter {

        public void commitTransaction(FragmentTransaction fragmentTransaction);
    }

    private static interface Notifier {
        public void notifyListener(@NonNull OnFragmentChangeListener onFragmentChangeListener);
    }
}
