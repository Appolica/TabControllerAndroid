package com.appolica.tabcontroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.appolica.tabcontroller.listener.OnFragmentChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TabController {

    private static final String TAG = "TabController";

    public static final String BUNDLE_KEY = "bundle-key-tab-controller";

    private final FragmentManager fragmentManager;
    private final int containerId;

    private ShowHideHandler showHideHandler;

    private OnFragmentChangeListener changeListener;


    public TabController(FragmentManager fragmentManager, int containerId) {
        this(fragmentManager, containerId, new ShowHideFrHandler());
    }

    public TabController(FragmentManager fragmentManager, int containerId, ShowHideHandler showHideHandler) {
        this.fragmentManager = fragmentManager;
        FragmentManager.enableDebugLogging(false);
        this.containerId = containerId;
        this.showHideHandler = showHideHandler;
    }

    public void switchTo(FragmentProvider fragmentType) {

        List<Notifier> notifiers = new ArrayList<>();

        inTransaction(transaction -> {

            final Fragment fragmentToShow = fragmentManager.findFragmentByTag(fragmentType.getTag());

            if (fragmentToShow != null) {
                if (!showHideHandler.isVisible(fragmentToShow)) {
                    //Fragment is active but not visible
                    hideVisibleFragment(transaction);
                    showFragment(fragmentToShow, transaction);

                    notifiers.add(listener -> listener.onFragmentShown(fragmentType, fragmentToShow));
                } else {
                    //Fragment is already visible on the screen
                    notifiers.add(listener -> listener.onFragmentAlreadyVisible(fragmentType, fragmentToShow));
                }
            } else {
                //Fragment does not exist
                hideVisibleFragment(transaction);

                Fragment addedFragment = addToFragmentManager(fragmentType, transaction);

                notifiers.add(listener -> listener.onFragmentCreated(fragmentType, addedFragment));
                notifiers.add(listener -> listener.onFragmentShown(fragmentType, addedFragment));
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

    @Nullable
    public Fragment getFragment(@NonNull FragmentProvider fragmentProvider) {
        return fragmentManager.findFragmentByTag(fragmentProvider.getTag());
    }

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
        @NonNull
        public void commitTransaction(FragmentTransaction fragmentTransaction);
    }

    private static interface Notifier {
        public void notifyListener(@NonNull OnFragmentChangeListener onFragmentChangeListener);
    }
}
